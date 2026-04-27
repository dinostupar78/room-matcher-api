package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.user.AppUserDTO;
import hr.tvz.roommatcher.dto.user.AppUserRequest;
import hr.tvz.roommatcher.dto.user.AppUserResponse;

import hr.tvz.roommatcher.dto.user.UpdateUserRequest;
import hr.tvz.roommatcher.mapper.AppUserMapper;
import hr.tvz.roommatcher.model.AppUser;
import hr.tvz.roommatcher.repository.AppUserJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;


@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final AppUserJpaRepository appUserRepository;
    private final AppUserMapper appUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AppUserResponse getMe() {
        AppUser user = getCurrentUser();
        return appUserMapper.toAppUserResponse(user);
    }

    @Override
    public AppUserResponse updateMe(UpdateUserRequest request) {
        AppUser user = getCurrentUser();

        if (request.getUsername() != null && !request.getUsername().equals(user.getUsername())) {
            if (appUserRepository.existsByUsername(request.getUsername())) {
                throw new RuntimeException("Username already exists");
            }
            user.setUsername(request.getUsername());
        }

        if (request.getEmail() != null && !request.getEmail().equals(user.getEmail())) {
            if (appUserRepository.existsByEmail(request.getEmail())) {
                throw new RuntimeException("Email already exists");
            }
            user.setEmail(request.getEmail());
        }

        if (request.getNewPassword() != null) {
            if (request.getCurrentPassword() == null) {
                throw new RuntimeException("Current password is required");
            }

            if (!passwordEncoder.matches(request.getCurrentPassword(), user.getPassword())) {
                throw new RuntimeException("Current password is incorrect");
            }

            user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        }

        appUserMapper.updateAppUserFromRequest(request, user);

        AppUser saved = appUserRepository.save(user);
        return appUserMapper.toAppUserResponse(saved);
    }

    @Override
    public AppUserResponse uploadAvatar(MultipartFile file) {
        AppUser user = getCurrentUser();

        if (file.isEmpty()) {
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
            throw new RuntimeException("Could not upload avatar");
        }
    }

    @Override
    public AppUserDTO getPublicUser(Long id) {
        AppUser user = appUserRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("User not found"));
        return appUserMapper.toDto(user);
    }

    @Override
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