package ba.unsa.etf.nwt.projectservice.projectservice.exception.base;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {
    public BadRequestException(final String message) {
        super(message, HttpStatus.BAD_REQUEST);
    }
}
