package kz.kbtu.security;

import kz.kbtu.entity.User;

public interface JwtTokenProvider {
    String generateToken(User user);
}
