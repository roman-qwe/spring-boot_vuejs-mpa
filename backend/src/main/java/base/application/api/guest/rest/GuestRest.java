package base.application.api.guest.rest;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import base.application.config.security.jwt.JwtProvider;
import base.application.data.db.base.model.user.entity.User;
import base.application.data.db.base.repository.user.UserRepository;
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
    public ResponseEntity login(@RequestBody UserLoginView userLoginView) {
        log.info("IN login - userLoginView with {}", userLoginView);

        log.info("++++ IN GuestRest login: {}", userLoginView == null || !userLoginView.isValid());

        if (userLoginView == null || !userLoginView.isValid())
            throw new BadCredentialsException("Invalid username or password");
        log.info("userLoginView: {}", userLoginView.isValid());

        try {
            String username = userLoginView.getUsername();
            Authentication auth = authManager
                    .authenticate(new UsernamePasswordAuthenticationToken(username, userLoginView.getPassword()));
            log.info("auth = {}", auth.isAuthenticated());

            User user = userRepository.findByName(username);

            if (user == null) {
                throw new UsernameNotFoundException("User with username: " + username + " not found");
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
}