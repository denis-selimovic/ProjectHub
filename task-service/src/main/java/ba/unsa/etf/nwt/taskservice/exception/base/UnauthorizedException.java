package ba.unsa.etf.nwt.taskservice.exception.base;

import org.springframework.http.HttpStatus;

public class UnauthorizedException extends BaseException {
    public UnauthorizedException(final String message) {
        super(message, HttpStatus.UNAUTHORIZED);
    }
}
