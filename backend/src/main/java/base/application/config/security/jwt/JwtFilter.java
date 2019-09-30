package base.application.config.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JwtProvider jwtProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest req, HttpServletResponse res, FilterChain filterChain)
            throws ServletException, IOException {
        HttpServletRequest httpReq = (HttpServletRequest) req;

        Cookie[] cookies = httpReq.getCookies();
        if (cookies == null) {
            filterChain.doFilter(req, res);
            return;
        }

        List<Cookie> tokenCookies = Arrays.stream(cookies).filter(a -> a.getName().equals(JwtProvider.HEADER))
                .collect(Collectors.toList());

        if (tokenCookies == null || tokenCookies.size() == 0) {
            filterChain.doFilter(req, res);
            return;
        }

        String token = tokenCookies.get(0).getValue();
        log.info("IN JwtFilter doFilterInternal token: {}", token);
        if (token == null) {
            filterChain.doFilter(req, res);
            return;
        }

        if (!jwtProvider.validate(token)) {
            filterChain.doFilter(req, res);
            return;
        }

        Authentication auth = jwtProvider.getAuthentication(token);
        if (auth == null) {
            filterChain.doFilter(req, res);
            return;
        }

        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(req, res);
    }

}