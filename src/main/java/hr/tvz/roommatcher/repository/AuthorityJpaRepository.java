package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.enums.UserRole;
import hr.tvz.roommatcher.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityJpaRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRole(UserRole role);
}
