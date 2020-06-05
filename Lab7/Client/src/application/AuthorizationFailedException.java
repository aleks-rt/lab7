package application;

public class AuthorizationFailedException extends Exception {
    public AuthorizationFailedException() {
        super();
    }

    public AuthorizationFailedException(String message) {
        super(message);
    }
}
