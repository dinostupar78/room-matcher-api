package hr.tvz.roommatcher.dto.user;
import hr.tvz.roommatcher.enums.GenderType;
import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record AppUserDTO (
        String name,
        String email,
        String username,
        String password,
        GenderType gender,
        String bio,
        String profileImageUrl,
        LocalDate dateOfBirth,
        Instant registrationDate
) {}
