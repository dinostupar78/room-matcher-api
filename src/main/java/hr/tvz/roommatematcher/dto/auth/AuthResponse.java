package hr.tvz.roommatematcher.dto.auth;

public record AuthResponse(
        String accessToken,
        String refreshToken,
        String tokenType
) { }
