package ba.unsa.etf.nwt.userservice.controller;

import ba.unsa.etf.nwt.userservice.exception.base.BadRequestException;
import ba.unsa.etf.nwt.userservice.exception.base.NotFoundException;
import ba.unsa.etf.nwt.userservice.exception.base.UnauthorizedException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/exceptions")
public class ExceptionsController {
    @GetMapping("/{exception}")
    public void throwError(@PathVariable String exception) {
        switch (exception) {
            case "400" -> throw new BadRequestException("Bad request thrown");
            case "401" -> throw new UnauthorizedException("Unauthorized thrown");
            case "404" -> throw new NotFoundException("Not found thrown");
        }
    }
}
