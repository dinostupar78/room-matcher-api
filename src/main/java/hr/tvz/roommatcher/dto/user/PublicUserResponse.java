package hr.tvz.roommatcher.dto.user;
import hr.tvz.roommatcher.enums.GenderType;
import java.time.Instant;
import java.time.LocalDate;

public record PublicUserResponse(
        String name,
        String email,
        String username,
        GenderType gender,
        String bio,
        String profileImageUrl,
        LocalDate dateOfBirth,
        Instant registrationDate
) {}
