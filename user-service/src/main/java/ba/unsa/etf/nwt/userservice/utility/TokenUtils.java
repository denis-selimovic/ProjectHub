package ba.unsa.etf.nwt.userservice.utility;

import lombok.Data;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Data
public class TokenUtils {

    @Value("${token.duration.token.activation}")
    private Integer activationTokenDuration;
    @Value("${token.duration.token.reset}")
    private Integer resetPasswordTokenDuration;
    @Value("${token.length}")
    private Integer tokenLength;

    public String generateHashString(){
        return RandomStringUtils.random(tokenLength, true, true);
    }
}
