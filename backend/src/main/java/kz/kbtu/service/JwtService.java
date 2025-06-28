package kz.kbtu.service;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import kz.kbtu.entity.User;
import kz.kbtu.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
    public class JwtService implements JwtTokenProvider {

        @Value("${jwt.secret}")
        private String secretKey;

        @Override
        public String generateToken(User user) {
            Key key = Keys.hmacShaKeyFor(secretKey.getBytes());
            long now = System.currentTimeMillis();
            long expiration = now + 3600000;

            return Jwts.builder()
                    .setSubject(user.getEmail())
                    .setIssuedAt(new Date(now))
                    .setExpiration(new Date(expiration))
                    .signWith(key, SignatureAlgorithm.HS256)
                    .compact();
        }
    }
