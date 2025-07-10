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
    private final JwtTokenProvider jwtTokenProvider;

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
        .orElseThrow(() -> new RuntimeException("User not found"));

        if(!passwordEncoder.matches(request.getPassword(), user.getPassword())){
            throw new RuntimeException("Wrong password");
        }
        String token = jwtTokenProvider.generateToken(user);
        System.out.println("Trying login for: " + request.getEmail());
        return new LoginResponse(token);

    }

}
