package base.application.config.security.jwt;

import java.util.Base64;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
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
    private final String SECRET = "super_secret_jwt_string";
    private final String ENCODED_SECRET;

    private final long VALIDATE_MILLISECONDS = 1000 * 60 * 60;

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer_";

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return PasswordUtil.B_CRYPT_PASSWORD_ENCODER;
    }

    public JwtProvider() {
        ENCODED_SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

    public String create(GUser user) {
        Claims claims = Jwts.claims().setSubject(user.getName());
        claims.put("role", user.getRole().getName());

        Date now = new Date();
        Date validity = new Date(now.getTime() + VALIDATE_MILLISECONDS);

        return PREFIX + Jwts.builder()//
                .setClaims(claims)//
                .setIssuedAt(now)//
                .setExpiration(validity)//
                .signWith(SignatureAlgorithm.HS256, ENCODED_SECRET)//
                .compact();
    }

    public boolean validate(String uToken) {
        String token = refinement(uToken);
        if (token == null)
            return false;
        log.info("IN JwtProvider - validate token: {}", token);
        Jws<Claims> claims = null;

        try {
            claims = Jwts.parser().setSigningKey(ENCODED_SECRET).parseClaimsJws(token);
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
        UserDetails userDetails = userDetailsService.loadUserByUsername(getUsername(token));
        if (userDetails == null)
            return null;

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }

    private String refinement(String token) {
        if (!token.startsWith(PREFIX))
            return null;
        return token.substring(PREFIX.length(), token.length());
    }

    private String getUsername(String token) {
        return Jwts.parser().setSigningKey(ENCODED_SECRET).parseClaimsJws(token).getBody().getSubject();
    }
}