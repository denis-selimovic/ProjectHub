package ba.unsa.etf.nwt.userservice.client.exception;

import ba.unsa.etf.nwt.userservice.exception.base.BadRequestException;
import ba.unsa.etf.nwt.userservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.userservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.userservice.exception.base.UnprocessableEntityException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(String s, Response response) {
        return switch (response.status()) {
            case 404 -> new NotFoundException("Not found");
            case 422 -> new UnprocessableEntityException("Unprocessable entity");
            case 403 -> new ForbiddenException("Forbidden");
            default ->  new BadRequestException("Bad request");
        };
    }
}
