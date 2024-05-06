package railway.moroccorailwaysystem.dto.payload;

import org.springframework.http.HttpStatus;

public record ApiResponse(
        String email,
        String message,
        HttpStatus httpStatus
) {

}
