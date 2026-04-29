package hr.tvz.roommatcher.service;
import hr.tvz.roommatcher.dto.user.PublicUserResponse;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.dto.user.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {
    List<PublicUserResponse> getAllUsers();
    AppUserResponse getMe();
    AppUserResponse updateMe(UpdateUserRequest request);
    AppUserResponse uploadAvatar(MultipartFile file);
    PublicUserResponse getPublicUser(Long id);
    void deleteMe();
}

