package kz.kbtu.dto.user;

import kz.kbtu.entity.Role;

import java.util.UUID;

public class UserResponse {
    private UUID id;
    private String name;
    private String email;
    private String roleName;
    public UserResponse() {
    }
    public UserResponse(UUID id, String name, String email, String roleName) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.roleName = roleName;
    }
    public UUID getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getEmail() {
        return email;
    }


}
