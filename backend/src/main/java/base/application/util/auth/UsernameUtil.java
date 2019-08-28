package base.application.util.auth;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;

@Component
public class UsernameUtil {

    public static final int USERNAME_LENGTH_MIN = 6;
    public static final int USERNAME_LENGTH_MAX = 50;
    public static final Pattern USERNAME_REGEX = Pattern.compile("[a-zA-Z0-9-_]+");

    public static boolean checkError(String username) {
        return username == null || username.length() < USERNAME_LENGTH_MIN || username.length() > USERNAME_LENGTH_MAX
                || !USERNAME_REGEX.matcher(username).matches();
    }
}