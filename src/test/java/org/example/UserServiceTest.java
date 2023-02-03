package org.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.internal.verification.NoInteractions;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    @Mock
    UserRepository userRepository;
    @InjectMocks
    UserService userService;

    @Test
    void whenRepositoryReturnsNullThenSomethingHappenedThenListOfLoginsMustBeEmpty() {
        when(userRepository.getAllUsers()).thenReturn(null);
        assertThat(userService.getAllLogins()).isEqualTo(List.of());
    }

    @Test
    void whenRepositoryIsEmptyThenListOfLoginsMustAlsoBeEmpty() {
        when(userRepository.getAllUsers()).thenReturn(List.of());
        assertThat(userService.getAllLogins()).isEqualTo(List.of());
    }

    @Test
    void whenNetworkExceptionIsRaisedThenServiceReturnsEmptyList() {
        when(userRepository.getAllUsers()).thenThrow(new RuntimeException());
        assertThat(userService.getAllLogins()).isEqualTo(List.of());
    }

    @Test
    void whenInvalidUserIsPassedThenServiceThrowsException() {
        assertThatThrownBy(() -> userService.createNewUser(" ", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Некорректный формат login или password");
        verify(userRepository, new NoInteractions()).getAllUsers();
        verify(userRepository, new NoInteractions()).addUser(any());
    }

    @Test
    void whenExistingUserIsPassedThenServiceThrowsException() {
        when(userRepository.getAllUsers()).thenReturn(List.of(new User("test", "test")));
        assertThatThrownBy(() -> userService.createNewUser("test", "test"))
                .isInstanceOf(UserNonUniqueException.class)
                .hasMessage("Данный пользователь уже существует");
    }
}