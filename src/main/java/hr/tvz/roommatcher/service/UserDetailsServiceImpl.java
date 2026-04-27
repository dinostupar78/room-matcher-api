package hr.tvz.roommatcher.service;

import hr.tvz.roommatcher.model.AppUser;
import hr.tvz.roommatcher.repository.AppUserJpaRepository;
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
        System.out.println("LOGIN USERNAME: " + username);

        AppUser appUser = appUserRepository.findByUsername(username)
               .orElseThrow(() -> new UsernameNotFoundException("User with username " + username + " not found"));


        System.out.println("FOUND USER: " + appUser.getUsername());
        System.out.println("PASSWORD FROM DB: " + appUser.getPassword());

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
