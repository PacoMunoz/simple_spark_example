package org.example.model;

import org.example.exception.UserDomainException;

public record User(String name, String surname, String documentNumber) {
    public User {
        if (name == null || name.isBlank()) {
            throw new UserDomainException("Name can not be null or empty");
        }
        if (surname == null || surname.isBlank()) {
            throw new UserDomainException("Surname can not be null or empty");
        }
        if (documentNumber == null || documentNumber.isBlank()) {
            throw new UserDomainException("Document number can not be null or empty");
        }
    }
}
