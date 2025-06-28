package kz.kbtu.dto.auth;

import kz.kbtu.entity.Role;

public class RegisterRequest {
    private String name;
    private String email;
    private String password;
    private String role;

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

    public void setRole(String role) {
        this.role = role == null ? null : role.trim().toLowerCase();
    }

    public String getRole() {
        return this.role;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
