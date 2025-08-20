package kz.kbtu.service;

import jakarta.persistence.EntityNotFoundException;
import kz.kbtu.dto.auth.RegisterRequest;
import kz.kbtu.dto.user.UserResponse;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;
import kz.kbtu.mapper.UserMapper;
import kz.kbtu.repository.RoleRepository;
import kz.kbtu.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UserServiceTest {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserService userService;
    private PasswordEncoder passwordEncoder;
    private UserMapper userMapper;

    @BeforeEach
    void setUp() {
        userRepository = Mockito.mock(UserRepository.class);
        roleRepository = Mockito.mock(RoleRepository.class);
        passwordEncoder = Mockito.mock(PasswordEncoder.class);
        userMapper = Mockito.mock(UserMapper.class);
        userService = new UserService(userRepository, roleRepository, passwordEncoder, userMapper);

    }
    @Test
    void testRegister_Success(){
        RegisterRequest request = new RegisterRequest();
        request.setName("User");
        request.setEmail("test@example.com");
        request.setPassword("test123");
        request.setRole("analyst");

        when(userRepository.existsByEmail("test@example.com")).thenReturn(false);
        when(roleRepository.findByName("analyst")).thenReturn(Optional.of(new Role(1, "analyst")));
        when(passwordEncoder.encode("test123")).thenReturn("encoded-password");

        userService.registerUser(request);
        verify(userRepository, times(1)).save(Mockito.any(User.class));
    }
    @Test
    void testRegister_EmailExists(){
        RegisterRequest request = new RegisterRequest();
        request.setName("User_1");
        request.setEmail("test1@example.com");
        request.setPassword("test1234");
        request.setRole("analyst");

        when(userRepository.existsByEmail("test1@example.com")).thenReturn(true);
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });
        assertEquals("Email already in use", exception.getMessage());
        verify(userRepository, never()).save(Mockito.any(User.class));
    }

    @Test
    void testRegister_InvalidEmail(){
        RegisterRequest request = new RegisterRequest();
        request.setName("User_2");
        request.setEmail("invalid-email");
        request.setPassword("test1234");
        request.setRole("analyst");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);});
        assertEquals("Invalid email format", exception.getMessage());
        verify(userRepository, never()).save(any(User.class));

    }
    @Test
    void testRegister_EmptyFields(){
        RegisterRequest request = new RegisterRequest();
        request.setName("");
        request.setEmail("");
        request.setPassword("");
        request.setRole("");

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.registerUser(request);
        });

        assertEquals("All fields are required", exception.getMessage());
        verify(userRepository, never()).save(Mockito.any(User.class));
    }

    @Test
    void testRegister_InvalidRole() {
        RegisterRequest request = new RegisterRequest();
        request.setName("User_3");
        request.setEmail("test3@example.com");
        request.setPassword("test1234");
        request.setRole("manager");

        when(userRepository.existsByEmail("test3@example.com")).thenReturn(false);
        when(roleRepository.findByName("manager")).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                userService.registerUser(request));

        assertEquals("Role not found", exception.getMessage());

        verify(userRepository, never()).save(any(User.class));
    }

    @Test
    void testUsers_ReturnUsersExcludingAdmin(){
        Role role1 = new Role(1, "admin");
        User admin = new User();
        admin.setId(UUID.randomUUID());
        admin.setName("admin");
        admin.setEmail("admin@example.com");
        admin.setPassword("admin123");
        admin.setRole(role1);
        Role role2 = new Role(2, "analyst");
        User analyst = new User();
        analyst.setId(UUID.randomUUID());
        analyst.setName("analyst");
        analyst.setEmail("analyst@example.com");
        analyst.setPassword("analyst123");
        analyst.setRole(role2);

        when(userRepository.findAll()).thenReturn(List.of(admin, analyst));
        when(userMapper.toResponse(analyst)).thenReturn(
                new UserResponse(
                        analyst.getId(),
                        analyst.getName(),
                        analyst.getEmail(),
                        analyst.getRole().getName()
                )
        );
        List<UserResponse> result = userService.getAllUsersExcluding("admin@example.com");
        assertEquals(1, result.size());
        assertTrue(result.stream().noneMatch(u -> u.getEmail().equals("admin@example.com")));
    }

    @Test
    void testUsers_DeleteUserById(){
        UUID id = UUID.randomUUID();

        when(userRepository.existsById(id)).thenReturn(true);
        userService.deleteUserById(id);
        verify(userRepository, times(1)).deleteById(id);
    }
    @Test
    void testUsers_UserNotFound(){
        UUID id = UUID.randomUUID();
        when(userRepository.existsById(id)).thenReturn(false);
        assertThrows(EntityNotFoundException.class, () -> userService.deleteUserById(id));
    }
}