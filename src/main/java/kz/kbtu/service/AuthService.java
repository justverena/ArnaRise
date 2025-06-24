package kz.kbtu.service;



import kz.kbtu.dto.auth.LoginRequest;
import kz.kbtu.dto.auth.LoginResponse;
import kz.kbtu.entity.User;
import kz.kbtu.repository.UserRepository;
import kz.kbtu.security.JwtTokenProvider;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        String token = jwtService.generateToken(user);
        System.out.println("Trying login for: " + request.getEmail());
        return new LoginResponse(token);

    }

}
