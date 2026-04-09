package hr.tvz.roommatcher.model;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
public class User {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

    private String password;

    private LocalDate dateOfBirth;

    private LocalDateTime registrationDate;
}
