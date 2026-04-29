package hr.tvz.roommatcher.controller;
import hr.tvz.roommatcher.dto.user.PublicUserResponse;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.dto.user.UpdateUserRequest;
import hr.tvz.roommatcher.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<PublicUserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/me")
    public ResponseEntity<AppUserResponse> getMe() {
        return ResponseEntity.ok(userService.getMe());
    }

    @GetMapping("/{id}")
    public ResponseEntity<PublicUserResponse> getPublicUser(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getPublicUser(id));
    }

    @PostMapping("/me/avatar")
    public ResponseEntity<AppUserResponse> uploadAvatar(@RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(userService.uploadAvatar(file));
    }

    @PutMapping("/me")
    public ResponseEntity<AppUserResponse> updateMe(@Valid @RequestBody UpdateUserRequest request) {
        return ResponseEntity.ok(userService.updateMe(request));
    }

    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMe() {
        userService.deleteMe();
        return ResponseEntity.noContent().build();
    }

}
