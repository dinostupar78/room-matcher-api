package hr.tvz.roommatematcher.service;
import hr.tvz.roommatematcher.dto.auth.RegisterRequest;
import hr.tvz.roommatematcher.dto.user.AppUserResponse;
import hr.tvz.roommatematcher.enums.UserRole;
import hr.tvz.roommatematcher.mapper.AppUserMapper;
import hr.tvz.roommatematcher.model.AppUser;
import hr.tvz.roommatematcher.model.Authority;
import hr.tvz.roommatematcher.repository.AppUserJpaRepository;
import hr.tvz.roommatematcher.repository.AuthorityJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class AppUserServiceImpl implements AppUserService {

    private final AppUserJpaRepository appUserRepository;
    private final AuthorityJpaRepository authorityRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AppUserResponse registerUser(RegisterRequest registerRequest) {
        if (appUserRepository.existsByUsername(registerRequest.username())) {
            throw new RuntimeException("Username already exists");
        }

        if (appUserRepository.existsByEmail(registerRequest.email())) {
            throw new RuntimeException("Email already exists");
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
