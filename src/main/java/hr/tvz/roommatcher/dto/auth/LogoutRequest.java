package hr.tvz.roommatcher.dto.auth;

public record LogoutRequest(
        String refreshToken
) {
}
