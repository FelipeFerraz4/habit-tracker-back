package space.algoritmos.habit_tracker_back.exception;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class InvalidJwtAutenticationException extends AuthenticationException {
    public InvalidJwtAutenticationException(String message) {
        super(message);
    }
}
