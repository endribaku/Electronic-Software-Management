package Exceptions;

public class InvalidCredentialsException extends Exception{
    String message = "Invalid username or password.";

    public InvalidCredentialsException() {
        System.out.println(this.getMessage());
    }

    public InvalidCredentialsException(String message) {
        this.message = message;
        System.out.println(this.getMessage());
    }

    @Override
    public String getMessage() {
        return this.message;
    }
}
