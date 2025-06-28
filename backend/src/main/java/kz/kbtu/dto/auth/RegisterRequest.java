package kz.kbtu.dto.auth;

import kz.kbtu.entity.Role;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private Role role;

    public String getEmail() {
        return email;
    }


    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String user) {
        this.name = user;
    }

    public void setEmail(String mail) {
        this.email = mail;
    }

    public void setPassword(String test123) {
        this.password = test123;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
}
