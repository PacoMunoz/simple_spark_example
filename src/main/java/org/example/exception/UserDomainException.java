package org.example.exception;

public class UserDomainException extends RuntimeException{
    public UserDomainException(String message) {
        super(message);
    }
}
