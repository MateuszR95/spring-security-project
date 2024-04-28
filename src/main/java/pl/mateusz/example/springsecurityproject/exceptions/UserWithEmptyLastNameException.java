package pl.mateusz.example.springsecurityproject.exceptions;

public class UserWithEmptyLastNameException extends RuntimeException {
    public UserWithEmptyLastNameException(String message) {
        super(message);
    }
}
