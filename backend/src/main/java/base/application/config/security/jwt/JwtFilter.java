package base.application.config.security.jwt;

import java.io.IOException;
import java.util.Arrays;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import base.application.config.security.WebSecurityConfig;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    private JwtProvider jwtProvider;
    private AntPathMatcher pathMatcher = new AntPathMatcher();

    @Autowired
    public JwtFilter(JwtProvider jwtProvider) {
        this.jwtProvider = jwtProvider;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        log.info("IN doFilterInternal do first");
        log.info("IN doFilterInternal uri: {}", req.getRequestURI());
        JwtCookie cookie = jwtProvider.getJwtCookie(req);
        if (!JwtCookie.isValid(cookie)) {
            filterChain.doFilter(req, res);
            return;
        }
        log.info("IN doFilterInternal jwtCookie(current): {}", cookie);

        cookie = jwtProvider.createAndSetAuth(cookie.getDetail());
        if (!JwtCookie.isValid(cookie)) {
            filterChain.doFilter(req, res);
            return;
        }
        log.info("IN doFilterInternal jwtCookie(new): {}", cookie);

        res.addCookie(cookie);
        filterChain.doFilter(req, res);
    }

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) throws ServletException {
        return Arrays.stream(WebSecurityConfig.EXCLUDE_FROM_JWT_FILTER).anyMatch(p -> pathMatcher.match(p, request.getServletPath()));
    }

}