package hr.tvz.roommatcher.service;
import hr.tvz.roommatcher.dto.user.PublicUserResponse;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.dto.user.UpdateUserRequest;
import hr.tvz.roommatcher.mapper.AppUserMapper;
import hr.tvz.roommatcher.model.AppUser;
import hr.tvz.roommatcher.repository.AppUserJpaRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserJpaRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<PublicUserResponse> getAllUsers() {
        List<AppUser> users = appUserRepository.findAll();
        return users.stream()
                .map(appUserMapper::toDto)
                .toList();

    }

    @Override
    public AppUserResponse getMe() {
        AppUser user = getCurrentUser();
        return appUserMapper.toAppUserResponse(user);
    }

    @Override
    @Transactional
    public AppUserResponse updateMe(UpdateUserRequest request) {
        AppUser user = getCurrentUser();

        if (request.username() != null && !request.username().equals(user.getUsername())) {
            if (appUserRepository.existsByUsername(request.username())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(request.username());
        }

        if (request.email() != null && !request.email().equals(user.getEmail())) {
            if (appUserRepository.existsByEmail(request.email())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.email());
        }

        if (request.newPassword() != null) {
            if (request.currentPassword() == null) {
                throw new RuntimeException("Current password is required");
            }

            if (!passwordEncoder.matches(request.currentPassword(), user.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }

            user.setPassword(passwordEncoder.encode(request.newPassword()));
        }

        appUserMapper.updateAppUserFromRequest(request, user);

        AppUser saved = appUserRepository.save(user);
        return appUserMapper.toAppUserResponse(saved);
    }

    @Override
    @Transactional
    public AppUserResponse uploadAvatar(MultipartFile file) {
        AppUser user = getCurrentUser();

        if (file == null || file.isEmpty()) {
            throw new RuntimeException("File is empty");
        }

        try{
            String uploadDir = "uploads/avatars/";
            String fileName = user.getId() + "_" + System.currentTimeMillis() + "_" + file.getOriginalFilename();

            Path uploadPath = Paths.get(uploadDir);
            Files.createDirectories(uploadPath);

            Path filePath = uploadPath.resolve(fileName);
            Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

            user.setProfileImageUrl("/uploads/avatars/" + fileName);

            AppUser saved = appUserRepository.save(user);
            return appUserMapper.toAppUserResponse(saved);

        } catch (IOException e) {
            throw new RuntimeException("Could not upload avatar", e);
        }
    }

    @Override
    public PublicUserResponse getPublicUser(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appUserMapper.toDto(user);
    }

    @Override
    @Transactional
    public void deleteMe() {
        AppUser user = getCurrentUser();
        appUserRepository.delete(user);

    }

    private AppUser getCurrentUser() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        return appUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}