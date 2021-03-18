package ba.unsa.etf.nwt.taskservice.exception.base;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
