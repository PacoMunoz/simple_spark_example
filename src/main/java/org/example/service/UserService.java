package org.example.service;

import org.example.exception.UserAlreadyExitsException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;

import java.util.Optional;
import java.util.Set;

public record UserService(Set<User> users) {

    public User createUser(User user) {
        if (users.add(user)) {
            return user;
        }
        throw new UserAlreadyExitsException();
    }

    public User updateUser(User user) {
        final var oldUser = users.stream()
                .filter(user1 -> user1.documentNumber().equals(user.documentNumber()))
                .findFirst()
                .orElseThrow(UserNotFoundException::new);
        users.remove(oldUser);
        users.add(user);
        return user;
    }

    public void deleteUser(String documentNumber) {
        final var result = users.stream().filter(user -> user.documentNumber().equals(documentNumber)).findFirst();
        final var user = result.orElseThrow(UserNotFoundException::new);
        users.remove(user);
    }

    public Optional<User> getUser(String documentNumber) {
        return users.stream().filter(user -> user.documentNumber().equals(documentNumber)).findFirst();
    }
}
