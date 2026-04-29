package hr.tvz.roommatcher.dto.auth;
import jakarta.validation.constraints.NotBlank;

public record LogoutRequest(
        @NotBlank(message = "refreshToken must not be blank")
        String refreshToken
) {}
