package org.example.exception;

public class UserAlreadyExitsException extends RuntimeException {

    public static final String DEFAULT_USER_EXISTS_MESSAGE = "User already exist";

    public UserAlreadyExitsException() {
        super(DEFAULT_USER_EXISTS_MESSAGE);
    }
}
