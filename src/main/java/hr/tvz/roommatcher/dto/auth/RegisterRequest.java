package hr.tvz.roommatcher.dto.auth;
import hr.tvz.roommatcher.enums.GenderType;
import jakarta.validation.constraints.*;
import java.time.LocalDate;

public record RegisterRequest(
        @NotBlank(message = "Name is required")
        @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
        String name,

        @NotBlank(message = "Email is required")
        @Email(message = "Email should be valid")
        String email,

        @NotBlank(message = "Username is required")
        @Size(min = 2, max = 150, message = "Username must be between 2 and 150 characters")
        String username,

        @NotBlank(message = "Password is required")
        @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
        String password,

        @NotNull(message = "Gender is required")
        GenderType gender,

        @Size(max = 1000, message = "Bio must be less than 1000 characters")
        String bio,

        @NotNull(message = "Date of birth is required")
        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @Size(max = 1000, message = "Profile image URL must be less than 1000 characters")
        String profileImageUrl

) {}
