package hr.tvz.roommatematcher.service;
import hr.tvz.roommatematcher.model.AppUser;
import hr.tvz.roommatematcher.repository.AppUserJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final AppUserJpaRepository appUserRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        AppUser appUser = appUserRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));

       return User.builder()
                .username(appUser.getUsername())
                .password(appUser.getPassword())
                .authorities(
                        appUser.getAuthorities()
                                .stream()
                                .map(authority -> authority.getRole().name())
                                .toArray(String[]::new)
                )
                .build();
    }
}
