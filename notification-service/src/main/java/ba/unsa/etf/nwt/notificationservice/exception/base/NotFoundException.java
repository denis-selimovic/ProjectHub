package ba.unsa.etf.nwt.notificationservice.exception.base;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {
    public NotFoundException(final String message) {
        super(message, HttpStatus.NOT_FOUND);
    }
}
