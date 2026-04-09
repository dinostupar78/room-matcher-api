package hr.tvz.roommatcher.repository;

import hr.tvz.roommatcher.model.User;

import java.util.List;
import java.util.Optional;

public interface UserRepository {
    List<User> findAll();
    Optional<User> findById(Long id);
    Optional<User> findMe();
}
