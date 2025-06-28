package kz.kbtu.service;

import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.dto.auth.LoginResponse;
import kz.kbtu.service.AuthService;
import kz.kbtu.entity.User;
import kz.kbtu.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class AuthServiceTest {
    private final UserRepository userRepository = Mockito.mock(UserRepository.class);
    private final PasswordEncoder passwordEncoder = Mockito.mock(PasswordEncoder.class);
    private final JwtService jwtService = Mockito.mock(JwtService.class);

    private final AuthService authService = new AuthService(userRepository, passwordEncoder, jwtService);

    @Test
    void testLogin_Success(){
        LoginRequest request = new LoginRequest("test@example.com", "password");

        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashed-password");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("password", "hashed-password")).thenReturn(true);
        Mockito.when(jwtService.generateToken(user)).thenReturn("mock-token");

        LoginResponse response = authService.login(request);

        assertNotNull(response);
        assertEquals("mock-token", response.getToken());
    }

    @Test
    void testLogin_UserNotFound(){
        LoginRequest request = new LoginRequest("missing@example.com", "password");
        Mockito.when(userRepository.findByEmail("missing@example.com")).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> authService.login(request));
    }

    @Test
    void testLogin_InvalidPassword(){
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("hashed-password");

        LoginRequest request = new LoginRequest("test@example.com", "wrongpassword");

        Mockito.when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        Mockito.when(passwordEncoder.matches("wrongpassword", "hashed-password")).thenReturn(false);

        assertThrows(RuntimeException.class, () -> authService.login(request));

    }
}
