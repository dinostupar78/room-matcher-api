package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.model.User;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class MockUserRepository implements UserRepository {

    private static final List<User> users = new ArrayList<>();

    static {
        User firstUser = User.builder()
                .id(1L)
                .firstName("John")
                .lastName("Doe")
                .email("john.doe@example.com")
                .password("password123")
                .dateOfBirth(LocalDate.of(1990, 1, 1))
                .registrationDate(LocalDateTime.now().minusDays(30))
                .build();

        User secondUser = User.builder()
                .id(2L)
                .firstName("Jane")
                .lastName("Smith")
                .email("jane.smith@example.com")
                .password("secret")
                .dateOfBirth(LocalDate.of(1992, 6, 15))
                .registrationDate(LocalDateTime.now().minusDays(10))
                .build();

        users.add(firstUser);
        users.add(secondUser);
    }

    @Override
    public List<User> findAll() {
        return users;
    }

    @Override
    public Optional<User> findById(Long id) {
        return users.stream()
                .filter(users -> users.getId().equals(id))
                .findFirst();
    }

    @Override
    public Optional<User> findMe() {
        // For the mock, return the first user as the "current" user
        return users.stream().findFirst();
    }
}
