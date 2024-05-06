package railway.moroccorailwaysystem.dto.payload;

import railway.moroccorailwaysystem.model.UserType;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        int age,
        String username,
        String password,
        UserType userType
) {
}
