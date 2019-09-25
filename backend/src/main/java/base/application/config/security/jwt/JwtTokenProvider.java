package base.application.config.security.jwt;

import java.util.Base64;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import base.application.data.db.base.model.user.role.Role;
import base.application.util.auth.PasswordUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtTokenProvider {

    private String encodedSecret;

    @Autowired
    private UserDetailsService userDetailsService;

    @PostConstruct
    protected void init() {
        encodedSecret = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return PasswordUtil.B_CRYPT_PASSWORD_ENCODER;
    }

    public String createToken(String username, Role role) {

        Claims claims = Jwts.claims().setSubject(username);
        claims.put("roles", getRoleNames(role));

        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDATE_MILLISECONDS);

        return TOKEN_PREFIX + Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, encodedSecret)//
                .compact();
    }

    public Authentication getAuthentication(String token) {
        log.info("IN getAuthentication - token: {}", removeTokenPrefix(token));
        UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(removeTokenPrefix(token)));
        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    public String getUsername(String token) {
        return Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(token).getBody().getSubject();
    }

    public boolean validateToken(String token) {
        String toValid = removeTokenPrefix(token);
        Jws<Claims> claims = Jwts.parser().setSigningKey(encodedSecret).parseClaimsJws(toValid);

        if (claims.getBody().getExpiration().before(new Date()))
            return false;

        return true;
    }

    private List<String> getRoleNames(Role role) {
        log.info("IN getRoleNames role: {} - ", role.getName());
        return Collections.singletonList(role.getName());
    }

    private String removeTokenPrefix(String token) {
        if (!token.startsWith(TOKEN_PREFIX))
            return null;
        return token.substring(TOKEN_PREFIX.length(), token.length());
    }
}