package base.application.api.guest.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.application.config.security.jwt.JwtProvider;
import base.application.viewmodel.UserLoginView;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(value = "/api/guest/")
public class GuestRest {

    @Autowired
    private AuthenticationManager authManager;
    @Autowired
    private JwtProvider jwtProvider;
    @Autowired
    private UserRepository userRepository;

    @PostMapping("login")
    public ResponseEntity login(HttpServletRequest req, HttpServletResponse res,     @RequestBody UserLoginView userLoginView) {
    log.info("IN GuestRest login - userLoginView with {}", userLoginView);

    if (userLoginView == null || !userLoginView.isValid())
    throw new BadCredentialsException("Invalid username or password");

    try {
    String username = userLoginView.getUsername();
    Authentication auth = authManager
    .authenticate(new UsernamePasswordAuthenticationToken(username,
    userLoginView.getPassword()));

    log.info("IN GuestRest login - auth: {}", auth);

    if (auth != null)
    log.info("auth = {}", auth.isAuthenticated());

    User user = userRepository.findByName(username);

    if (user == null) {
    throw new UsernameNotFoundException("User with username: " + username + " not
    found");
    }

    String token = jwtProvider.create(user);

    Map<Object, Object> response = new HashMap<>();
    response.put("username", username);
    response.put("token", token);

    return ResponseEntity.ok(response);
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