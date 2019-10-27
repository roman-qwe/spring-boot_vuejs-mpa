package base.application.config.security.jwt;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthException extends AuthenticationException {
    private static final long serialVersionUID = 5462806736135731416L;

    public JwtAuthException(String msg, Throwable t) {
        super(msg, t);
    }

    public JwtAuthException(String msg) {
        super(msg);
    }
}