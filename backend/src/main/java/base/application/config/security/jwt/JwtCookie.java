package base.application.config.security.jwt;

import javax.servlet.http.Cookie;

import lombok.Getter;

@Getter
public class JwtCookie extends Cookie {
    private static final long serialVersionUID = 5349051539818218766L;

    private JwtDetail detail;
    private String token;

    public JwtCookie(JwtDetail detail, String token) {
        super(JwtConfig.AUTH_NAME, token);

        this.detail = detail;
        this.token = token;
        setPath("/");
    }

    public static boolean isValid(JwtCookie cookie) {
        if (cookie == null)
            return false;
        return cookie != null && JwtDetail.isValid(cookie.getDetail()) && cookie.getToken() != null
                && !cookie.getToken().isBlank();
    }

    @Override
    public String toString() {
        return String.format("detail : { %s }, token: %s", detail, token);
    }
}