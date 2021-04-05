package ba.unsa.etf.nwt.projectservice.projectservice.client.exception;

import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.BadRequestException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.ForbiddenException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.projectservice.projectservice.exception.base.UnprocessableEntityException;
import feign.Response;
import feign.codec.ErrorDecoder;

public class CustomErrorDecoder implements ErrorDecoder {
    @Override
    public Exception decode(final String s, final Response response) {
        return switch (response.status()) {
            case 400 -> new BadRequestException("Bad request");
            case 404 -> new NotFoundException("Not found");
            case 422 -> new UnprocessableEntityException("Unprocessable entity");
            case 403 -> new ForbiddenException("You don't have permission for this activity");
            default -> new Exception("Error");
        };
    }
}
