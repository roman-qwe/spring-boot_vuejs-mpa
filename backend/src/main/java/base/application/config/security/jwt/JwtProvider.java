package base.application.config.security.jwt;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import base.application.util.auth.PasswordUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtProvider {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return PasswordUtil.B_CRYPT_PASSWORD_ENCODER;
    }

    public JwtCookie getJwtCookie(HttpServletRequest req) {
        Cookie cookie = getTokenCookie(req, JwtConfig.AUTH_NAME);
        if (cookie == null)
            return null;
        String token = cookie.getValue();
        if (token == null || token.isBlank())
            return null;
        String cToken = refinement(cookie.getValue(), JwtConfig.PREFIX);
        if (!isValidToken(cToken))
            return null;
        JwtDetail detail = JwtDetail.from(cToken);
        if (!JwtDetail.isValid(detail))
            return null;
        log.info("IN getJwtCookie detail: {}", detail);
        return new JwtCookie(detail, token);
    }

    public JwtCookie createJwtCookie(JwtDetail detail) {
        if (!JwtDetail.isValid(detail))
            return null;
        String token = detail.createToken();
        if (token == null || token.isBlank())
            return null;
        log.info("IN createJwtCookie token: {}", token);
        return new JwtCookie(detail, token);
    }

    public JwtCookie createAndSetAuth(JwtDetail detail) {
        log.info("IN createAndSetAuth JwtDetail: {}", detail);
        JwtCookie cookie = createJwtCookie(detail);
        if (!JwtCookie.isValid(cookie))
            return null;
        log.info("IN createAndSetAuth jwtCookie: {}", cookie);
        Authentication auth = getAuth(cookie);
        if (auth == null)
            return null;

        SecurityContextHolder.getContext().setAuthentication(auth);
        return cookie;
    }

    private Authentication getAuth(JwtCookie cookie) {
        if (!JwtCookie.isValid(cookie))
            return null;
        Collection<? extends GrantedAuthority> authorities = Collections
                .singleton(new SimpleGrantedAuthority(cookie.getDetail().getRole()));
        return new UsernamePasswordAuthenticationToken(cookie.getDetail().getUsername(), null, authorities);
    }

    private Cookie getTokenCookie(HttpServletRequest req, String cookieName) {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        Cookie[] cookies = httpReq.getCookies();
        if (cookies == null || cookies.length == 0)
            return null;
        Optional<Cookie> oCookie = Arrays.stream(cookies).filter(a -> a.getName().equals(cookieName)).findFirst();
        if (oCookie == null || oCookie.isEmpty())
            return null;
        return oCookie.get();
    }

    private static String refinement(String token, String jwtPrefix) {
        if (token == null || token.isBlank() || !token.startsWith(jwtPrefix))
            return null;
        return token.substring(jwtPrefix.length(), token.length());
    }

    private boolean isValidToken(String token) {
        if (token == null || token.isBlank())
            return false;
        Jws<Claims> claims = null;
        try {
            log.info("before claims token: {}", token);
            claims = Jwts.parser().setSigningKey(JwtConfig.ENCODED_SECRET).parseClaimsJws(token);
            log.info("after claims");
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | UnsupportedJwtException
                | IllegalArgumentException e) {
            log.info(e.toString());
            return false;
        }
        log.info("claims received without exception");

        if (claims == null || claims.getBody().getExpiration().before(new Date()))
            return false;

        return true;
    }
}