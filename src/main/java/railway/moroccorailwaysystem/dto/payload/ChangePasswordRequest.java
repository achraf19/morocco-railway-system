package railway.moroccorailwaysystem.dto.payload;

public record ChangePasswordRequest(
        String email,
        String newPassword
){
}
