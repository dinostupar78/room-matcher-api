package hr.tvz.roommatematcher.dto.user;
import hr.tvz.roommatematcher.enums.GenderType;
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
