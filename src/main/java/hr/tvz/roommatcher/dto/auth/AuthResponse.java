package hr.tvz.roommatcher.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken
) {
}
