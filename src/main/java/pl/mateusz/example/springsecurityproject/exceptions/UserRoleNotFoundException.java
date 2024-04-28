package pl.mateusz.example.springsecurityproject.exceptions;

public class UserRoleNotFoundException extends RuntimeException {
    public UserRoleNotFoundException(String message) {
        super(message);
    }
}
