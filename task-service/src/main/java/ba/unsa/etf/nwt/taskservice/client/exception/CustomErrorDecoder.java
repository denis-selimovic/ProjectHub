package ba.unsa.etf.nwt.taskservice.client.exception;

import ba.unsa.etf.nwt.taskservice.exception.base.BadRequestException;
import ba.unsa.etf.nwt.taskservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.taskservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.taskservice.exception.base.UnpocessableEntityException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(final String s, final Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException("Bad request");
            case 404 -> new NotFoundException("Not fount");
            case 422 -> new UnpocessableEntityException("Unprocessable entity");
            case 403 -> new ForbiddenException("Forbidden");
            default -> new Exception("Error");
        };
    }
}
