package pl.mateusz.example.springsecurityproject.user;

import pl.mateusz.example.springsecurityproject.role.UserRole;

import java.util.Set;
import java.util.stream.Collectors;

class UserDtoMapper {

    public static UserRegisterDto mapToRegisterDto(User user) {
        UserRegisterDto userRegisterDto = new UserRegisterDto();
        userRegisterDto.setUserName(user.getUserName());
        userRegisterDto.setEmail(user.getEmail());
        userRegisterDto.setFirstName(user.getFirstName());
        userRegisterDto.setLastName(user.getLastName());
        userRegisterDto.setPassword(user.getPassword());
        Set<String> roleNames = user.getRoles().stream()
                .map(userRole -> userRole.getRole().name()).collect(Collectors.toSet());
        userRegisterDto.setRoles(roleNames);
        return userRegisterDto;
    }

    public static UserEditDto mapToEditDto(User user) {
        UserEditDto userEditDto = new UserEditDto();
        userEditDto.setUserName(user.getUserName());
        userEditDto.setFirstName(user.getFirstName());
        userEditDto.setLastName(user.getLastName());
        userEditDto.setPassword(user.getPassword());
        return userEditDto;
    }

    public static UserDisplayDto mapToDisplayDto(User user) {
        UserDisplayDto userDisplayDto = new UserDisplayDto();
        userDisplayDto.setUserName(user.getUserName());
        userDisplayDto.setFirstName(user.getFirstName());
        userDisplayDto.setLastName(user.getLastName());
        userDisplayDto.setEmail(user.getEmail());
        Set<UserRole> roles = user.getRoles();
        Set<String> roleNames = roles.stream().map(userRole -> userRole.getRole()
                .name()).collect(Collectors.toSet());
        userDisplayDto.setRoles(roleNames);
        return userDisplayDto;
    }

}
