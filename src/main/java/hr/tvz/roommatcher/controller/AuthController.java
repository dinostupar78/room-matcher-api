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
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final AppUserService appUserService;
    private final RefreshTokenService refreshTokenService;
    private final AppUserJpaRepository appUserRepository;

    @PostMapping("/register")
    public ResponseEntity<AppUserResponse> register(@Valid @RequestBody RegisterRequest request) {
        AppUserResponse response = appUserService.registerUser(request);

        return ResponseEntity
                .created(URI.create("/api/users/" + response.id()))
                .body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody AuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        AppUser user = appUserRepository.findByUsername(authentication.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String accessToken = jwtTokenProvider.generateToken(authentication.getName());
        RefreshToken refreshToken = refreshTokenService.createRefreshToken(user);

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken(), "Bearer"));
    }

    @PostMapping("/refresh")
    public ResponseEntity<AuthResponse> refresh(@Valid @RequestBody RefreshTokenRequest request){
        RefreshToken refreshToken = refreshTokenService.verifyRefreshToken(request.refreshToken());

        String accessToken = jwtTokenProvider.generateToken(
                refreshToken.getAppUser().getUsername()
        );

        return ResponseEntity.ok(new AuthResponse(accessToken, refreshToken.getToken(), "Bearer"));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody LogoutRequest request) {
        refreshTokenService.deleteRefreshToken(request.refreshToken());
        return ResponseEntity.noContent().build();
    }

}
