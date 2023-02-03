package org.example;

import java.util.*;

public class UserRepository {
    private final List<User> users = new ArrayList<>();
    public Collection<User> getAllUsers() {
        return Collections.unmodifiableList(users);
    }

    public Optional<User> searchUserByLogin(String login) {
        for (User user : users) {
            if (user.getLogin().equals(login)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public Optional<User> searchUserByLoginPassword(String login, String password) {
        for (User user : users) {
            if (user.getLogin().equals(login) && user.getPassword().equals(password)) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    public void addUser(User user) {
        this.users.add(user);
    }
}
