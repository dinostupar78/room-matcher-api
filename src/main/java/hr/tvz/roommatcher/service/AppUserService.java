package hr.tvz.roommatcher.service;
import hr.tvz.roommatcher.dto.auth.RegisterRequest;
import hr.tvz.roommatcher.dto.user.AppUserResponse;

public interface AppUserService {
    AppUserResponse registerUser(RegisterRequest registerRequest);
}
