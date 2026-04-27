package hr.tvz.roommatcher.controller;

import hr.tvz.roommatcher.dto.auth.*;
import hr.tvz.roommatcher.dto.user.AppUserResponse;
import hr.tvz.roommatcher.dto.auth.RegisterRequest;
import hr.tvz.roommatcher.model.AppUser;
import hr.tvz.roommatcher.model.RefreshToken;
import hr.tvz.roommatcher.repository.AppUserJpaRepository;
import hr.tvz.roommatcher.security.JwtTokenProvider;
import hr.tvz.roommatcher.service.AppUserService;
import hr.tvz.roommatcher.service.RefreshTokenService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserService appUserService;
    private final RefreshTokenService refreshTokenService;
    private final AppUserJpaRepository appUserRepository;

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        String accessToken = jwtTokenProvider.generateToken(authentication.getName());

        AppUser user = appUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @PostMapping("/register")
    public ResponseEntity<AppUserResponse> register(@Valid @RequestBody RegisterRequest request) {
        AppUserResponse response = appUserService.registerUser(request);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/refresh")
    public AuthResponse refresh(@RequestBody RefreshTokenRequest request){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.refreshToken());

        String accessToken = jwtTokenProvider.generateToken(
                refreshToken.getAppUser().getUsername()
        );

        return new AuthResponse(accessToken, refreshToken.getToken());
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody RefreshTokenRequest request) {
        refreshTokenService.deleteRefreshToken(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

}
