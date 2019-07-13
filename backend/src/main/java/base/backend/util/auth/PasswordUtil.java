package base.backend.util.auth;

import org.springframework.stereotype.Component;
import java.util.regex.Pattern;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Component
public class PasswordUtil {

    public static final int PASSWORD_STRENGTH = 8;
    public static final int PASSWORD_LENGTH_MIN = 6;
    public static final int PASSWORD_LENGTH_MAX = 50;
    public static final Pattern PASSWORD_REGEX = Pattern.compile("[a-zA-Z0-9!@#$%^&\\\\\\-_]+");
    public static final BCryptPasswordEncoder B_CRYPT_PASSWORD_ENCODER = new BCryptPasswordEncoder(PASSWORD_STRENGTH);

    public static boolean checkError(String password) {
        return password == null || password.length() < PASSWORD_LENGTH_MIN || password.length() > PASSWORD_LENGTH_MAX
                || !PASSWORD_REGEX.matcher(password).matches();
    }
}