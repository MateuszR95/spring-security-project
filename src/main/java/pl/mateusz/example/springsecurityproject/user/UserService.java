package pl.mateusz.example.springsecurityproject.user;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.mateusz.example.springsecurityproject.exceptions.UserNotFoundException;
import pl.mateusz.example.springsecurityproject.exceptions.UserRoleNotFoundException;
import pl.mateusz.example.springsecurityproject.exceptions.UserValidationException;
import pl.mateusz.example.springsecurityproject.role.Role;
import pl.mateusz.example.springsecurityproject.role.UserRole;
import pl.mateusz.example.springsecurityproject.role.UserRoleRepository;
import pl.mateusz.example.springsecurityproject.user.dto.UserDisplayDto;
import pl.mateusz.example.springsecurityproject.user.dto.UserEditDto;
import pl.mateusz.example.springsecurityproject.user.dto.UserRegisterDto;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, UserRoleRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    public Optional<UserRegisterDto> getUserByUserName(String userName) {
        Optional<User> userByUserName = userRepository.getUserByUserName(userName);
        return userByUserName.map(UserDtoMapper::mapToRegisterDto);
    }

    public Optional<UserEditDto> getUserEditDtoByUsername(String userName) {
        Optional<User> userByUserName = userRepository.getUserByUserName(userName);
        return userByUserName.map(UserDtoMapper::mapToEditDto);
    }

    public Optional<UserDisplayDto> getUserByUsername(String username) {
        Optional<User> userByUserName = userRepository.getUserByUserName(username);
        return userByUserName.map(UserDtoMapper::mapToDisplayDto);
    }

    public List<UserDisplayDto> getUsersList() {
        return userRepository.findAllByOrderByUserNameDesc()
                .stream()
                .map(UserDtoMapper::mapToDisplayDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void register(UserRegisterDto dto) {
        User user = new User();
        Optional<User> userByUserName = userRepository.getUserByUserName(dto.getUserName());
        if (userByUserName.isPresent()) {
            throw new UserValidationException("Użytkownik o tej nazwie już istnieje!");
        }
        Optional<User> userByEmail = userRepository.getUserByEmail(dto.getEmail());
        if (userByEmail.isPresent()) {
            throw new UserValidationException("Użytkownik z podanym adresem mailowym już istnieje!");
        }
        user.setUserName(dto.getUserName());
        user.setEmail(dto.getEmail());
        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        String encryptedPassword = passwordEncoder.encode(dto.getPassword());
        user.setPassword(encryptedPassword);
        UserRole role = userRoleRepository.getUserRoleByRole(Role.USER)
                .orElseThrow(() -> new UserRoleNotFoundException("Nie znaleziono takiej roli użytkownika"));
        user.getRoles().add(role);
        userRepository.save(user);

    }

    @Transactional
    public void editAccount(String userName, UserEditDto userEditDto) {
        User user = userRepository.getUserByUserName(userName).orElseThrow(() -> new UserNotFoundException("Nie znaleziono takiego użytkownika"));
        if (userEditDto.getFirstName().isEmpty()) {
            throw new UserValidationException("Imię nie może być puste");
        }
        if (userEditDto.getLastName().isEmpty()) {
            throw new UserValidationException("Nazwisko nie może być puste");
        }
        user.setFirstName(userEditDto.getFirstName());
        user.setLastName(userEditDto.getLastName());
        String passwordEncoded = passwordEncoder.encode(userEditDto.getPassword());
        user.setPassword(passwordEncoded);
        userRepository.save(user);
    }

    @Transactional
    public void promoteUser(String userName) {
        User user = userRepository.getUserByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Nie znaleziono takiego użytkownika"));
        UserRole role = userRoleRepository.getUserRoleByRole(Role.ADMIN)
                .orElseThrow(() -> new UserRoleNotFoundException("Nie znaleziono takiej roli użytkownika"));
        user.getRoles().add(role);
    }

    @Transactional
    public void dismissUser(String userName) {
        User user = userRepository.getUserByUserName(userName)
                .orElseThrow(() -> new UserNotFoundException("Nie znaleziono takiego użytkownika"));
        UserRole role = userRoleRepository.getUserRoleByRole(Role.ADMIN)
                .orElseThrow(() -> new UserRoleNotFoundException("Nie znaleziono takiej roli użytkownika"));
        user.getRoles().remove(role);
    }

}
