package com.valeflores.vale_das_flores.services;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import com.valeflores.vale_das_flores.entities.User;
import com.valeflores.vale_das_flores.repositories.UserRepository;
import com.valeflores.vale_das_flores.services.exceptions.RegisterException;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @BeforeEach
    public void setup() {
    }

    @Test
    public void testCreateUser_ShouldCreateUserWhenEmailIsNotUsed() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(false);
        
        User user = new User();
        user.setEmail(email);
        user.setName("Test User");
        user.setPassword("password");

        userService.insert(user);

        verify(userRepository, times(1)).existsByEmail(email);
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testCreateUser_ShouldThrowExceptionWhenEmailIsUsed() {
        String email = "test@example.com";
        when(userRepository.existsByEmail(email)).thenReturn(true);

        User user = new User();
        user.setEmail(email);
        user.setName("Test User");
        user.setPassword("password");

        assertThrows(RegisterException.class, () -> {
            userService.insert(user);
        });
    }
}
