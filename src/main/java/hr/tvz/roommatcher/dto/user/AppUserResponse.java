package hr.tvz.roommatcher.dto.user;

import hr.tvz.roommatcher.enums.GenderType;
import lombok.Builder;

import java.time.Instant;
import java.time.LocalDate;

@Builder
public record AppUserResponse(
        Long id,
        String name,
        String email,
        GenderType gender,
        String bio,
        LocalDate dateOfBirth,
        String profileImageUrl,
        Instant registrationDate

) {}