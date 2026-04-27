package hr.tvz.roommatcher.dto.user;

import hr.tvz.roommatcher.enums.GenderType;
import lombok.Data;

import java.time.LocalDate;

@Data
public class UpdateUserRequest {
    private String username;
    private String email;
    private String name;
    private String bio;
    private String profileImageUrl;
    private GenderType gender;
    private LocalDate dateOfBirth;
    private String currentPassword;
    private String newPassword;
}
