package kz.kbtu.service;
import kz.kbtu.dto.auth.RegisterRequest;
import kz.kbtu.dto.auth.RegisterResponse;
import kz.kbtu.entity.User;
import kz.kbtu.entity.Role;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository,
                       RoleRepository roleRepository,
                       PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
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
}