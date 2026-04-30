package hr.tvz.roommatematcher.repository;

import hr.tvz.roommatematcher.enums.UserRole;
import hr.tvz.roommatematcher.model.Authority;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthorityJpaRepository extends JpaRepository<Authority, Long> {
    Optional<Authority> findByRole(UserRole role);
}
