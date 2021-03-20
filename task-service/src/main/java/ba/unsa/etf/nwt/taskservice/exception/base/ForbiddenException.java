package ba.unsa.etf.nwt.taskservice.exception.base;

import org.springframework.http.HttpStatus;

public class ForbiddenException extends BaseException{
    public ForbiddenException(final String message) {
        super(message, HttpStatus.FORBIDDEN);
    }
}
