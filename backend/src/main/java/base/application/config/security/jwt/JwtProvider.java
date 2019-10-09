package base.application.config.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.Cookie;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import base.application.data.db.base.model.user.general.GUser;
import base.application.util.auth.PasswordUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return PasswordUtil.B_CRYPT_PASSWORD_ENCODER;
    }

    public String create(GUser user) {
        if (user == null || user.getName() == null || user.getRole() == null)
            return null;
        return generateToken(user.getName(), user.getRole().getName());
    }

    public String create(Authentication auth) {
        log.info("IN create Authentication auth.getAuthorities().stream().findFirst().get().getAuthority(): ",
                auth.getAuthorities().stream().findFirst().get().getAuthority());
        String username = auth.getName();
        if (username == null)
            return null;
        Optional<? extends GrantedAuthority> oRole = auth.getAuthorities().stream().findFirst();
        if (oRole == null || oRole.isEmpty())
            return null;
        String role = oRole.get().getAuthority();
        if (role == null)
            return null;
        return generateToken(username, role);
    }

    private String generateToken(String username, String role) {
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + JwtConfig.VALIDATE_MILLISECONDS);

        return JwtConfig.PREFIX + Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, JwtConfig.ENCODED_SECRET)//
                .compact();
    }

    public boolean validate(String uToken) {
        String token = refinement(uToken);
        if (token == null)
            return false;
        log.info("IN JwtProvider - validate token: {}", token);
        Jws<Claims> claims = null;

        try {
            claims = Jwts.parser().setSigningKey(JwtConfig.ENCODED_SECRET).parseClaimsJws(token);
        } catch (JwtException | IllegalArgumentException e) {
            return false;
        }

        log.info("IN JwtProvider - validate claims: {}", claims);

        if (claims != null && claims.getBody().getExpiration().before(new Date()))
            return false;

        return true;
    }

    public Authentication getAuthentication(String uToken) {
        String token = refinement(uToken);
        if (token == null)
            return null;

        log.info("IN getAuthentication - token: {}", token);

        Jws<Claims> parsedToken = Jwts.parser().setSigningKey(JwtConfig.ENCODED_SECRET).parseClaimsJws(token);
        String username = getUsername(token);
        Collection<? extends GrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority((String) parsedToken.getBody().get("role")));

        log.info("IN getAuthentication - authorities.size(): {}", authorities.size());
        if (username != null) {
            return new UsernamePasswordAuthenticationToken(username, null, authorities);
        }
        return null;
    }

    public Cookie getTokenCookie(HttpServletRequest req) {
        HttpServletRequest httpReq = (HttpServletRequest) req;

        Cookie[] cookies = httpReq.getCookies();
        if (cookies == null) {
            return null;
        }

        Optional<Cookie> oCookie = Arrays.stream(cookies).filter(a -> a.getName().equals(JwtConfig.HEADER)).findFirst();
        if (oCookie == null || oCookie.isEmpty()) {
            return null;
        }

        return oCookie.get();
    }

    private String refinement(String token) {
        if (!token.startsWith(JwtConfig.PREFIX))
            return null;
        return token.substring(JwtConfig.PREFIX.length(), token.length());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(JwtConfig.ENCODED_SECRET).parseClaimsJws(token).getBody().getSubject();
    }
}