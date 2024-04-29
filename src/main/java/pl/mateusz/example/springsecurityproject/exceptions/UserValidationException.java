package pl.mateusz.example.springsecurityproject.exceptions;

public class UserValidationException extends RuntimeException {
    public UserValidationException(String message) {
        super(message);
    }
}
