package org.example;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<String> getAllLogins() {
        try {
            Collection<User> users = this.userRepository.getAllUsers();
            if (users == null || users.isEmpty()) {
                return List.of();
            }
            return users
                    .stream()
                    .map(User::getLogin)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return List.of();
        }
    }

    public void createNewUser(String login, String password) {
        User user = new User(login, password);
        if (login == null || login.isBlank() && password == null || password.isBlank()) {
            throw new IllegalArgumentException("Некорректный формат login или password");
        }
        boolean userExist = this.userRepository
                .getAllUsers()
                .stream()
                .anyMatch(u -> u.equals(user));
        if (userExist) {
            throw new UserNonUniqueException("Данный пользователь уже существует");
        }
        this.userRepository.addUser(user);
    }
}
