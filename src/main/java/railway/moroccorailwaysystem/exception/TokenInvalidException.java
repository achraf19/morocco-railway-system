package railway.moroccorailwaysystem.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NO_CONTENT)
public class TokenInvalidException extends RuntimeException {

    public TokenInvalidException(String message) {
        super(message);
    }
}
