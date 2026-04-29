package hr.tvz.roommatcher.dto.user;
import hr.tvz.roommatcher.enums.GenderType;
import java.time.Instant;
import java.time.LocalDate;

public record AppUserResponse(
        Long id,
        String username,
        String name,
        String email,
        GenderType gender,
        String bio,
        LocalDate dateOfBirth,
        String profileImageUrl,
        Instant registrationDate

) {}