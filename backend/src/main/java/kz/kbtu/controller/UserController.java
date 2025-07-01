package kz.kbtu.controller;

import kz.kbtu.dto.auth.RegisterRequest;
import kz.kbtu.dto.auth.RegisterResponse;
import kz.kbtu.dto.user.UserResponse;
import kz.kbtu.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.bind.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/admin")
public class UserController {

    private final UserService userService;
    public UserController(UserService userService) {
        this.userService = userService;
    }
    @PreAuthorize("hasAuthority('admin')")
    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> registerUser(@RequestBody RegisterRequest request) {
        RegisterResponse response = userService.registerUser(request);
        return ResponseEntity.ok(response);
    }
    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/users")
    public List<UserResponse> getAllUsersExcludingAdmin(@AuthenticationPrincipal UserDetails admin) {
        return userService.getAllUsersExcluding(admin.getUsername());
    }
    @PreAuthorize("hasAuthority('admin')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable("id") UUID id, @AuthenticationPrincipal UserDetails user) {
        System.out.println("Delete requested by: " + (user != null ? user.getUsername(): "anonymous"));
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
