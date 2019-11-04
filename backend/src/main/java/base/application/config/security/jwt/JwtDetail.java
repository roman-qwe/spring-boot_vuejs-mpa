package base.application.config.security.jwt;

import java.util.Date;

import base.application.util.auth.UsernameUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Builder
public class JwtDetail {
    private String username;
    private String role;

    public static JwtDetail from(String token) {
        if (token == null || token.isBlank())
            return null;

        Claims parsedBody = null;
        try {
            Jws<Claims> parsedToken = Jwts.parser().setSigningKey(JwtConfig.ENCODED_SECRET).parseClaimsJws(token);
            parsedBody = parsedToken.getBody();
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException
                | IllegalArgumentException e) {
            return null;
        }
        if (parsedBody == null)
            return null;
        String username = parsedBody.getSubject();
        String role = (String) parsedBody.get("role");
        return JwtDetail.builder().username(username).role(role).build();
    }

    public String createToken() {
        if (!isValid(this))
            return null;

        log.info("IN createToken username: {}", username);
        log.info("IN createToken role: {}", role);
        Claims claims = Jwts.claims().setSubject(username);
        claims.put("role", role);

        Date now = new Date();
        Date validity = new Date(now.getTime() + JwtConfig.VALIDATE_MILLISECONDS);

        return JwtConfig.PREFIX + Jwts.builder().setClaims(claims).setIssuedAt(now).setExpiration(validity)
                .signWith(SignatureAlgorithm.HS256, JwtConfig.ENCODED_SECRET).compact();
    }

    public static boolean isValid(JwtDetail detail) {
        return detail != null && UsernameUtil.isValid(detail.getUsername()) && detail.getRole() != null
                && !detail.getRole().isBlank();
    }

    @Override
    public String toString() {
        return String.format("username: %s, role: %s", username, role);
    }
}