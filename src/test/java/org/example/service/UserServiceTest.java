package org.example.service;

import org.example.exception.UserAlreadyExitsException;
import org.example.exception.UserNotFoundException;
import org.example.model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashSet;

import static org.assertj.core.api.Assertions.*;

class UserServiceTest {

    private static final String DEFAULT_USER_NAME = "Antonio";
    private static final String DEFAULT_USER_SURNAME = "Sanchez";
    private static final String DEFAULT_USER_DOCUMENT_NUMBER = "12Q";

    private UserService userService;

    @BeforeEach
    void setup() {
        userService = new UserService(new HashSet<User>());
    }

    @Test
    void should_createUser() {
        final var user = getDefaultUser();

        final var result = userService.createUser(user);

        final var createdUser = userService.getUser(user.documentNumber());
        assertThat(result).isNotNull().isEqualTo(createdUser.get());
    }

    @Test
    void should_throwException_when_createUserAndUserExists() {
        final var user = getDefaultUser();
        userService.createUser(user);
        final var expectedThrow = catchThrowable(() -> userService.createUser(user));
        assertThat(expectedThrow).isInstanceOf(UserAlreadyExitsException.class)
                .hasMessage(UserAlreadyExitsException.DEFAULT_USER_EXISTS_MESSAGE);
    }

    @Test
    void should_beOk_when_updateUser() {
        final var user = getDefaultUser();
        userService.createUser(user);
        final var userModified = getDefaultUser("Pepe", "Perez");

        final var result = userService.updateUser(userModified);

        final var updatedUser = userService.getUser(user.documentNumber());
        assertThat(result).isNotNull().isEqualTo(updatedUser.get());
    }

    @Test
    void should_throwException_when_updateUserAndUserDoesNotExist() {
        final var expectedThrow = catchThrowable(() -> userService.updateUser(getDefaultUser()));

        assertThat(expectedThrow).isInstanceOf(UserNotFoundException.class)
                .hasMessage(UserNotFoundException.DEFAULT_USER_DOES_NOT_EXIST_MESSAGE);
    }

    @Test
    void should_beOk_when_deleteUser() {
        final var user = getDefaultUser();
        userService.createUser(user);

        assertThatCode(() -> userService.deleteUser(user.documentNumber())).doesNotThrowAnyException();

    }

    @Test
    void should_throwException_when_deleteUserAndUserDoesNotExist() {
        final var expectedThrow = catchThrowable(() -> userService.deleteUser(getDefaultUser().documentNumber()));

        assertThat(expectedThrow).isInstanceOf(UserNotFoundException.class)
                .hasMessage(UserNotFoundException.DEFAULT_USER_DOES_NOT_EXIST_MESSAGE);
    }

    @Test
    void should_beOk_when_getUser() {
        final var user = getDefaultUser();
        userService.createUser(user);

        final var result = userService.getUser(user.documentNumber());

        assertThat(result).hasValue(user);
    }

    @Test
    void should_returnEmptyOptional_when_getUserAndUserDoesNotExists() {
        final var result = userService.getUser(getDefaultUser().documentNumber());

        assertThat(result).isEmpty();
    }

    private User getDefaultUser() {
        return new User(DEFAULT_USER_NAME, DEFAULT_USER_SURNAME, DEFAULT_USER_DOCUMENT_NUMBER);
    }

    private User getDefaultUser(String name, String surname) {
        return new User(name, surname, DEFAULT_USER_DOCUMENT_NUMBER);
    }

}