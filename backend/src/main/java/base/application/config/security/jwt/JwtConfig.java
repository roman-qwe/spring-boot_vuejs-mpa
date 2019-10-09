package base.application.config.security.jwt;

import java.util.Base64;

public class JwtConfig {
    private static final String SECRET = "super_secret_jwt_string";

    public static final String ENCODED_SECRET = Base64.getEncoder().encodeToString(SECRET.getBytes());
    public static final long VALIDATE_MILLISECONDS = 1000 * 60 * 60;
    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer_";

}