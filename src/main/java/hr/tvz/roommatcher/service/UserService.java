package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.dto.user.AppUserDTO;
import hr.tvz.roommatcher.dto.user.AppUserRequest;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.dto.user.UpdateUserRequest;
import org.springframework.web.multipart.MultipartFile;

public interface UserService {
    AppUserResponse getMe();
    AppUserResponse updateMe(UpdateUserRequest request);
    AppUserResponse uploadAvatar(MultipartFile file);
    AppUserDTO  getPublicUser(Long id);
    void deleteMe();
}

