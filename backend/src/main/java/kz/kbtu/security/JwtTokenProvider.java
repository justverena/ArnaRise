package kz.kbtu.security;

import kz.kbtu.entity.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface JwtTokenProvider {
    String generateToken(UserDetails userDetails);
    boolean validateToken(String token);
    String getUsernameFromToken(String token);
}
