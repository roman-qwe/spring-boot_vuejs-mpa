package base.application.api.guest.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import base.application.config.security.jwt.JwtConfig;
import base.application.config.security.jwt.JwtProvider;
import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.repository.user.UserRepository;
import base.application.util.auth.PasswordUtil;
import base.application.viewmodel.UserLoginView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/guest/")
public class GuestRest {

    // private AuthenticationManager authManager;
    private JwtProvider jwtProvider;
    private UserRepository userRepository;

    @Autowired
    public GuestRest(JwtProvider jwtProvider, UserRepository userRepository) {
        this.jwtProvider = jwtProvider;
        this.userRepository = userRepository;
    }


    @ResponseStatus(value = HttpStatus.OK)
    // @ResponseBody
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLoginView userLoginView, HttpServletRequest req,
            HttpServletResponse res) {
        log.info("IN GuestRest login - userLoginView with {}", userLoginView);

        if (userLoginView == null || !userLoginView.isValid())
            throw new BadCredentialsException("Invalid username or password");

        try {
            String username = userLoginView.getUsername();
            // Authentication auth = authManager
            // .authenticate(new UsernamePasswordAuthenticationToken(username,
            // userLoginView.getPassword()));

            // log.info("IN GuestRest login - auth: {}", auth);

            // if (auth != null)
            // log.info("auth = {}", auth.isAuthenticated());

            User user = userRepository.findByName(username);
            log.info("IN login user: ", user);

            if (user == null || !PasswordUtil.B_CRYPT_PASSWORD_ENCODER.matches(userLoginView.getPassword(),
                    user.getPassword())) {
                return ResponseEntity.ok("error: User with username: " + username + " not found");
            }

            String token = jwtProvider.create(user);
            log.info("IN login token: {}", token);
            // Map<Object, Object> response = new HashMap<>();
            // response.put("Authorization", token);

            HttpCookie cookie = ResponseCookie.from(JwtConfig.AUTH_NAME, token).path("/").build();
            return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("success");

            // Cookie cookie = new Cookie("my_token1", token);
            // cookie.setHttpOnly(true);
            // cookie.setDomain("my_domain");
            // res.addCookie(cookie);
            // log.info("IN login - cookie.toString: {} {}", cookie.getName(),
            // cookie.getValue());
            // HttpHeaders headers = new HttpHeaders();
            // headers.add("Set-Cookie", "my_token2=" + token);
            // return ResponseEntity.ok(response);

            // log.info("IN login - headers: ", headers);
            // ResponseEntity.status(HttpStatus.OK).headers(headers).build()
            // return Collections.singletonMap("response", "Hello World");
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    // @PostMapping("login")
    // public void login(@RequestBody UserLoginView userLoginView,
    // HttpServletRequest req, HttpServletResponse res) {
    // log.info("IN GuestRest login - userLoginView with {}", userLoginView);

    // if (userLoginView == null || !userLoginView.isValid())
    // throw new BadCredentialsException("Invalid username or password");

    // try {
    // String username = userLoginView.getUsername();
    // Authentication auth = authManager
    // .authenticate(new UsernamePasswordAuthenticationToken(username,
    // userLoginView.getPassword()));

    // log.info("IN GuestRest login - auth: {}", auth);

    // if (auth != null)
    // log.info("auth = {}", auth.isAuthenticated());

    // User user = userRepository.findByName(username);

    // if (user == null) {
    // throw new UsernameNotFoundException("User with username: " + username + " not
    // found");
    // }

    // String token = jwtProvider.create(user);
    // Cookie cookie = jwtProvider.getTokenCookie(req);
    // if (cookie == null) {
    // cookie = new Cookie(JwtConfig.HEADER, token);
    // } else {
    // cookie.setValue(token);
    // }

    // log.info("IN login - cookie.token: {}", cookie.getValue());
    // res.addCookie(cookie);
    // } catch (AuthenticationException e) {
    // throw new BadCredentialsException("Invalid username or password");
    // }
    // }
}