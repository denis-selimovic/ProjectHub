package ba.unsa.etf.nwt.projectservice.projectservice.exception.base;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BaseException {
    public UnprocessableEntityException(String message) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}