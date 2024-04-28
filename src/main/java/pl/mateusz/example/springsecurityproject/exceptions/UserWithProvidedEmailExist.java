package pl.mateusz.example.springsecurityproject.exceptions;

public class UserWithProvidedEmailExist extends RuntimeException {
    public UserWithProvidedEmailExist(String message) {
        super(message);
    }
}
