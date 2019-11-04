package base.application.api.guest.rest;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import base.application.config.security.jwt.JwtCookie;
import base.application.config.security.jwt.JwtDetail;
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
    @PostMapping(value = "login", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> login(@RequestBody UserLoginView userLoginView, HttpServletRequest req,
            HttpServletResponse res) {
        log.info("IN GuestRest login - userLoginView with {}", userLoginView);

        if (!UserLoginView.isValid(userLoginView))
            return ResponseEntity.ok("error: Username or password invalid");

        // try {
        String username = userLoginView.getUsername();
        User user = userRepository.findByName(username);
        log.info("IN login user: {}", user);

        if (user == null
                || !PasswordUtil.B_CRYPT_PASSWORD_ENCODER.matches(userLoginView.getPassword(), user.getPassword()))
            return ResponseEntity.ok("error: User with username: " + username + " not found");

        JwtDetail jwtDetail = JwtDetail.builder().username(user.getName()).role(user.getRole().getName()).build();
        JwtCookie jwtCookie = jwtProvider.createAndSetAuth(jwtDetail);
        if (!JwtCookie.isValid(jwtCookie))
            return ResponseEntity.ok("error: Username or password invalid");

        res.addCookie(jwtCookie);
        return ResponseEntity.ok().body("success");

        // String token = jwtProvider.create(user);
        // log.info("IN login token: {}", token);

        // HttpCookie cookie = ResponseCookie.from(JwtConfig.AUTH_NAME, token).path("/").build();
        // return ResponseEntity.ok().header(HttpHeaders.SET_COOKIE, cookie.toString()).body("success");

        // } catch (AuthenticationException e) {
        // throw new BadCredentialsException("Invalid username or password");
        // }
    }

}