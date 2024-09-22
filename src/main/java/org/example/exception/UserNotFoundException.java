package org.example.exception;

public class UserNotFoundException extends RuntimeException {

    public static final String DEFAULT_USER_DOES_NOT_EXIST_MESSAGE = "User does not exist";

    public UserNotFoundException() {
        super(DEFAULT_USER_DOES_NOT_EXIST_MESSAGE);
    }
}
