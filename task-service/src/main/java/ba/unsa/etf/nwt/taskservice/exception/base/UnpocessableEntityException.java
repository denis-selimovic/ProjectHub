package ba.unsa.etf.nwt.taskservice.exception.base;

import org.springframework.http.HttpStatus;

public class UnpocessableEntityException extends BaseException {
    public UnpocessableEntityException(final String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
