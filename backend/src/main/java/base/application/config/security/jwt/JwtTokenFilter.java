package base.application.config.security.jwt;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.GenericFilterBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class JwtTokenFilter extends GenericFilterBean {

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        HttpServletRequest httpReq = (HttpServletRequest) req;
        // Arrays.stream(httpReq.getCookies())
        // .forEach(a -> System.out.println("name: " + a.getName() + ", value: " +
        // a.getValue() + ", domain:"
        // + a.getDomain() + ", comment: " + a.getComment() + ", maxAge: " +
        // a.getMaxAge() + ", path: "
        // + a.getPath() + ", version: " + a.getVersion() + ", secure: " + a.getSecure()
        // + ", class: "
        // + a.getClass().toString()));

        Arrays.stream(httpReq.getCookies())
                .forEach(a -> System.out.println(new Date() + ", name: " + a.getName() + ", value: " + a.getValue()));

        // String token = jwtTokenProvider.resolveToken();
        // Stream<Cookie> stream = Arrays.stream(httpReq.getCookies()).filter(a ->
        // a.getName().equals("Authorization"));
        List<Cookie> tokenCookies = Arrays.stream(httpReq.getCookies()).filter(a -> a.getName().equals("Authorization"))
                .collect(Collectors.toList());

        if (tokenCookies != null && tokenCookies.size() > 0) {
            System.out.println("stream != null && count > 0");
            String token = tokenCookies.get(0).getValue();
            log.info("IN doFilter - token: {}", token);
            if (token != null && jwtTokenProvider.validateToken(token)) {
                System.out.println("token != null");
                Authentication auth = jwtTokenProvider.getAuthentication(token);

                if (auth != null) {
                    SecurityContextHolder.getContext().setAuthentication(auth);
                }
            }
        }
        filterChain.doFilter(req, res);
    }

}