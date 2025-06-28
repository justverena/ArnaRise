package kz.kbtu.service;

import kz.kbtu.dto.auth.RegisterRequest;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;
import kz.kbtu.repository.RoleRepository;
import kz.kbtu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userService = new UserService(userRepository, roleRepository, passwordEncoder);

    }
    @Test
    void testRegister_Success(){
        RegisterRequest request = new RegisterRequest();
        Role analystRole = new Role();
        analystRole.setId(1);
        analystRole.setName("analyst");
        request.setName("User");
        request.setEmail("test@example.com");
        request.setPassword("test123");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(roleRepository.findByName(request.getRole())).thenReturn(Optional.of(analystRole));
        when(passwordEncoder.encode("test123")).thenReturn("encoded-password");

        userService.registerUser(request);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }
    @Test
    void testRegister_EmailExists(){
        RegisterRequest request = new RegisterRequest();
        Role analystRole = new Role();
        analystRole.setId(1);
        analystRole.setName("analyst");
        request.setName("User_1");
        request.setEmail("test1@example.com");
        request.setPassword("test1234");

        when(userRepository.existsByEmail("test1@example.com")).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });
        assertEquals("Email already in use", exception.getMessage());
        verify(userRepository, never()).save(Mockito.any(User.class));
    }
}
