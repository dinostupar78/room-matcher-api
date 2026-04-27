package hr.tvz.roommatcher.controller;
import hr.tvz.roommatcher.dto.user.AppUserDTO;
import hr.tvz.roommatcher.dto.user.AppUserRequest;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.dto.user.UpdateUserRequest;
import hr.tvz.roommatcher.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping("/me")
    public ResponseEntity<AppUserResponse> getMe() {
        AppUserResponse response = userService.getMe();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/me/avatar")
    public ResponseEntity<AppUserResponse> uploadAvatar(@RequestParam("file") MultipartFile file) {
        AppUserResponse response = userService.uploadAvatar(file);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/me")
    public ResponseEntity<AppUserResponse> updateMe(@Valid @RequestBody UpdateUserRequest request) {
        AppUserResponse response = userService.updateMe(request);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppUserDTO> getPublicUser(@PathVariable Long id) {
        AppUserDTO response = userService.getPublicUser(id);
        return ResponseEntity.ok(response);

    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        userService.deleteMe();
        return ResponseEntity.noContent().build();
    }

}
