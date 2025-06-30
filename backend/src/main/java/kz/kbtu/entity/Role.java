package kz.kbtu.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "role")
@Data
public class Role {

    @Id
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

    public Role(Integer id, String name) {
        this.id = id;
        this.name = name;
    }
    public Role() {}
    public String getName() {
        return name;
    }
    public Role(String name) {
        this.name = name;
    }
    public Integer getId() {
        return id;
    }
    public void setId(Integer id){
        this.id = id;
    }
    public void setName(String name){
        this.name = name;
    }
}