package pl.mateusz.example.springsecurityproject.exceptions;

public class UserWithEmptyFirstNameException extends RuntimeException {
    public UserWithEmptyFirstNameException(String message) {
        super(message);
    }
}
