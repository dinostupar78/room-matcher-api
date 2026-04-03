package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.UserDTO;

import java.util.Optional;

public interface UserService {
    Optional<UserDTO> findMe();
    Optional<UserDTO> findById(Long id);
}

