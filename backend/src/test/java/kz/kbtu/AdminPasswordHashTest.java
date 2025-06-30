//package kz.kbtu;
//
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@SpringBootTest
//public class AdminPasswordHashTest {
//
//    @Autowired
//    private PasswordEncoder passwordEncoder;
//
//    @Test
//    void generatePasswordHash() {
//        String rawPassword = "admin123";
//        String hash = passwordEncoder.encode(rawPassword);
//        System.out.println("Password '" + rawPassword + "' hash:");
//        System.out.println(hash);
//    }
//}