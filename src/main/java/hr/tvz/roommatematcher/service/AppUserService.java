package hr.tvz.roommatematcher.service;
import hr.tvz.roommatematcher.dto.auth.RegisterRequest;
import hr.tvz.roommatematcher.dto.user.AppUserResponse;

public interface AppUserService {
    AppUserResponse registerUser(RegisterRequest registerRequest);
}
