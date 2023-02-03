package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

class UserRepositoryTest {
    private final User user1 = new User("john@domain.com", "John176-3");
    private final User user2 = new User("susan@domain.com", "Susan-6513");
    UserRepository userRepository;
    @BeforeEach
    public void setUp() {
        userRepository = new UserRepository();
        userRepository.addUser(user1);
        userRepository.addUser(user2);
    }

    @Test
    void whenTheResultingListOfUsersIsEmpty() {
        Collection<User> expected = userRepository.getAllUsers();
        Collection<User> actual = new ArrayList<>();
        Assertions.assertNotEquals(actual, expected);
    }

    @Test
    void whenTheResultingListOfUsersIsNotEmpty() {
        Collection<User> expected = userRepository.getAllUsers();
        Collection<User> actual = new ArrayList<>();
        actual.add(user1);
        actual.add(user2);
        Assertions.assertEquals(actual, expected);
    }

    @Test
    void searchUserByLoginIfExists() {
        Optional<User> user = userRepository.searchUserByLogin(user1.getLogin());
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void searchUserByLoginWhenThereIsNone() {
        Optional<User> user = userRepository.searchUserByLogin("tom@domain.com");
        Assertions.assertFalse(user.isPresent());
    }

    @Test
    void searchUserByLoginAndPasswordIfExists() {
        Optional<User> user = userRepository.searchUserByLoginPassword(user1.getLogin(), user1.getPassword());
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void searchUserByLoginAndPasswordWhenThereIsNone() {
        Optional<User> user = userRepository.searchUserByLoginPassword("tom@domain.com", "Tom14-4Nom");
        Assertions.assertFalse(user.isPresent());
    }

    @Test
    void searchUserByLoginAndPasswordWhenLoginMatchesButPasswordDoesnt() {
        Optional<User> user = userRepository.searchUserByLoginPassword(user2.getLogin(), "Tom14-4Nom");
        Assertions.assertFalse(user.isPresent());
    }

    @Test
    void searchUserByLoginAndPasswordWhenPasswordMatchesButLoginDoesnt() {
        Optional<User> user = userRepository.searchUserByLoginPassword("tom@domain.com", user2.getPassword());
        Assertions.assertFalse(user.isPresent());
    }
}