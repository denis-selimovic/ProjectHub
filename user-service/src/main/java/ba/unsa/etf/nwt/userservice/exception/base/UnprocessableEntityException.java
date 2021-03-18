package ba.unsa.etf.nwt.userservice.exception.base;

import org.springframework.http.HttpStatus;

public class UnprocessableEntityException extends BaseException {
    public UnprocessableEntityException(String message, HttpStatus httpStatus) {
        super(message, HttpStatus.UNPROCESSABLE_ENTITY);
    }
}
