package ba.unsa.etf.nwt.userservice.exception.base;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.http.HttpStatus;

@EqualsAndHashCode(callSuper = false)
@Data
public abstract class BaseException extends RuntimeException {
    private final HttpStatus httpStatus;

    public BaseException(final String message, final HttpStatus httpStatus) {
        super(message);
        this.httpStatus = httpStatus;
    }
}
