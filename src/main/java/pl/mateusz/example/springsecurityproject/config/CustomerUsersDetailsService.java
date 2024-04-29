package pl.mateusz.example.springsecurityproject.config;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.mateusz.example.springsecurityproject.user.dto.UserRegisterDto;
import pl.mateusz.example.springsecurityproject.user.UserService;

import java.util.Optional;

@Service
class CustomerUsersDetailsService implements UserDetailsService {

    private final UserService userService;

    public CustomerUsersDetailsService(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserRegisterDto> userByUserName = userService.getUserByUserName(username);
        return userByUserName.map(this::createUserDetails)
                .orElseThrow(() -> new UsernameNotFoundException("Nie znaleziono użytkownika o nazwie: "
                        + username));

    }

    private UserDetails createUserDetails(UserRegisterDto userRegisterDto) {
        return User.builder().username(userRegisterDto.getUserName())
                .password(userRegisterDto.getPassword())
                .roles(userRegisterDto.getRoles().toArray(String[]::new)).build();
    }
}
