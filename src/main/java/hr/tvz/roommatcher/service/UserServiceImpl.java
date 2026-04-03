package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.UserDTO;
import hr.tvz.roommatcher.model.entities.User;
import hr.tvz.roommatcher.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public Optional<UserDTO> findMe() {
        return userRepository.findMe().map(this::mapToDTO);
    }

    @Override
    public Optional<UserDTO> findById(Long id) {
        return userRepository.findById(id).map(this::mapToDTO);
    }

    private UserDTO mapToDTO(User user){
        return UserDTO.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .email(user.getEmail())
                .password(user.getPassword())
                .dateOfBirth(user.getDateOfBirth())
                .registrationDate(user.getRegistrationDate())
                .build();
    }
}