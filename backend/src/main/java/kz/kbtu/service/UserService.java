package kz.kbtu.service;
import jakarta.persistence.EntityNotFoundException;
import kz.kbtu.dto.auth.RegisterRequest;
import kz.kbtu.dto.auth.RegisterResponse;
import kz.kbtu.dto.user.UserResponse;
import kz.kbtu.entity.User;
import kz.kbtu.entity.Role;
import kz.kbtu.mapper.UserMapper;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userMapper = userMapper;
    }

    public RegisterResponse registerUser(RegisterRequest request) {

        if (request.getName() == null || request.getName().trim().isEmpty() ||
                request.getEmail() == null || request.getEmail().trim().isEmpty() ||
                request.getPassword() == null || request.getPassword().trim().isEmpty() ||
                request.getRole() == null || request.getRole().trim().isEmpty()) {
            throw new RuntimeException("All fields are required");
        }

        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        if (!request.getEmail().matches("^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$")) {
            throw new RuntimeException("Invalid email format");
        }

        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodedPassword);
        user.setRole(role);

        userRepository.save(user);

        return new RegisterResponse("User registered successfully");
    }
    public List<UserResponse> getAllUsersExcluding(String adminEmail){
        return userRepository.findAll().stream()
                .filter(user -> !user.getEmail().equals(adminEmail))
                .map(userMapper::toResponse)
                .collect(Collectors.toList());
    }
    public void deleteUserById(UUID id) {
        if (!userRepository.existsById(id)){
            throw new EntityNotFoundException("User not found");
        }
        userRepository.deleteById(id);
    }
}