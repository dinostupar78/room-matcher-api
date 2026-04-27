package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.auth.RegisterRequest;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.enums.UserRole;
import hr.tvz.roommatcher.mapper.AppUserMapper;
import hr.tvz.roommatcher.model.AppUser;
import hr.tvz.roommatcher.model.Authority;
import hr.tvz.roommatcher.repository.AppUserJpaRepository;
import hr.tvz.roommatcher.repository.AuthorityJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Set;

@Service
@AllArgsConstructor
public class AppUserImpl implements AppUserService {

    private final AppUserJpaRepository appUserRepository;
    private final AuthorityJpaRepository authorityRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AppUserResponse registerUser(RegisterRequest registerRequest) {
        if (appUserRepository.existsByUsername(registerRequest.username())) {
            throw new RuntimeException("Username already exists");
        }

        Authority userAuthority = authorityRepository.findByRole(UserRole.ROLE_USER)
                .orElseThrow(() -> new RuntimeException("Role User not found"));

        AppUser user = AppUser.builder()
                .name(registerRequest.name())
                .email(registerRequest.email())
                .username(registerRequest.username())
                .password(passwordEncoder.encode(registerRequest.password()))
                .gender(registerRequest.gender())
                .bio(registerRequest.bio())
                .profileImageUrl(registerRequest.profileImageUrl())
                .dateOfBirth(registerRequest.dateOfBirth())
                .registrationDate(Instant.now())
                .authorities(Set.of(userAuthority))
                .build();

        AppUser saved = appUserRepository.save(user);

        return appUserMapper.toAppUserResponse(saved);

    }
}
