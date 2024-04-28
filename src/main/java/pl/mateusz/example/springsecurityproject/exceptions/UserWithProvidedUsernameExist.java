package pl.mateusz.example.springsecurityproject.exceptions;

public class UserWithProvidedUsernameExist extends RuntimeException {
    public UserWithProvidedUsernameExist(String message) {
        super(message);
    }
}
