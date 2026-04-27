package hr.tvz.roommatcher.dto.user;

import hr.tvz.roommatcher.enums.GenderType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AppUserRequest {
    @NotBlank(message = "Name is required")
    @Size(min = 2, max = 150, message = "Name must be between 2 and 150 characters")
    private String name;

    @Email(message = "Email must be valid")
    @Size(max = 254, message = "Email must be at most 254 characters")
    private String email;

    @Size(max = 254, message = "Username must be at most 254 characters")
    private String username;

    String currentPassword;

    @Size(min = 8, max = 100, message = "Password must be between 8 and 100 characters")
    String newPassword;

    @NotNull(message = "Gender is required")
    private GenderType gender;

    @Size(max = 1000, message = "Bio must be at most 1000 characters")
    private String bio;

    @Size(max = 500, message = "Profile image URL must be at most 500 characters")
    private String profileImageUrl;

    @NotNull(message = "Date of birth is required")
    private LocalDate dateOfBirth;

}
