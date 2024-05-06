package railway.moroccorailwaysystem.dto.payload;

public record AuthRequest(
        String email,
        String password
) {
}
