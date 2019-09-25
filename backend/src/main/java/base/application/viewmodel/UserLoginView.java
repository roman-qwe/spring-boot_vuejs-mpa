package base.application.viewmodel;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

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
                password == null ? "null" : password.substring(0, 5));
    }
}