package base.application.viewmodel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import base.application.util.auth.PasswordUtil;
import base.application.util.auth.UsernameUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
@Setter
@AllArgsConstructor
@SuperBuilder
public class UserLoginView implements Serializable {

    private static final long serialVersionUID = 8741465753487639061L;

    @JsonProperty("username")
    protected String username;

    @JsonProperty("password")
    protected String password;

    @Override
    public String toString() {
        return String.format("username: %s, password: %s", username,
                password == null ? "null" : password.substring(0, 8));
    }

    public boolean isValid() {
        log.info("-- IN UserLoginView isValid Username: {}", UsernameUtil.checkError(username));
        log.info("-- IN UserLoginView isValid Password: {}", PasswordUtil.checkError(password));

        return !UsernameUtil.checkError(username) && !PasswordUtil.checkError(password);
    }
}