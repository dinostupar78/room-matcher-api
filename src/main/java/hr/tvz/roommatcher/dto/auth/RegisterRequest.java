package hr.tvz.roommatcher.dto.auth;

import hr.tvz.roommatcher.enums.GenderType;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record RegisterRequest(
        String username,
        String email,
        String password,
        String name,
        String bio,
        String profileImageUrl,
        GenderType gender,
        LocalDate dateOfBirth
) {
}
