package base.application.config.security.jwt;

@Component
public class Token {
    // JWT token defaults
    private final String SECRET = "super_secret_jwt_string";
    private final long VALIDATE_MILLISECONDS = 3_600_000;

    public static final String HEADER = "Authorization";
    public static final String PREFIX = "Bearer_";

    public static final String encodedSecret;

    @PostConstruct
    protected void init() {
        encodedSecret = Base64.getEncoder().encodeToString(SECRET.getBytes());
    }

}