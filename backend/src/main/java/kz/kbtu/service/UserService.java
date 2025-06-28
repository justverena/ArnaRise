package kz.kbtu.service;

import kz.kbtu.dto.auth.RegisterRequest;
import kz.kbtu.dto.auth.RegisterResponse;
import kz.kbtu.entity.Role;
import kz.kbtu.entity.User;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.repository.RoleRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import sun.security.x509.DNSName;

public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, RoleRepository roleRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public RegisterResponse registerUser(RegisterRequest request) {

        if (userRepository.existsByEmail(request.getEmail())){
            throw new RuntimeException("Email already in use");
        }
        Role role = roleRepository.findByName(request.getRole())
                .orElseThrow(() -> new RuntimeException("Role not found"));

        String encodePassword = passwordEncoder.encode(request.getPassword());

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(encodePassword);
        user.setRole(role);

        userRepository.save(user);

        return new RegisterResponse("User registered successfully");
    }
}
