package hr.tvz.roommatematcher.dto.user;
import hr.tvz.roommatematcher.enums.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UpdateUserRequest(

        @Size(min = 2, max = 200, message = "Name must be between 2 and 200 characters")
        String name,

        @Size(max = 254, message = "Email must be at most 254 characters")
        @Email(message = "Email should be valid")
        String email,

        @Size(min = 2, max = 150, message = "Username must be between 2 and 150 characters")
        String username,

        String currentPassword,

        @Size(min = 8, max = 100, message = "New password must be between 8 and 100 characters")
        String newPassword,

        GenderType gender,

        @Size(max = 1000, message = "Bio must be at most 1000 characters")
        String bio,

        @Past(message = "Date of birth must be in the past")
        LocalDate dateOfBirth,

        @Size(max = 1000, message = "Profile image URL must be less than 1000 characters")
        String profileImageUrl

) {}


