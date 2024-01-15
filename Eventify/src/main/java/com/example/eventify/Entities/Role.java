package com.example.eventify.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "Roles")
public class Role extends BaseEntity{
    private String Name;

    @ManyToMany(mappedBy = "Roles")
    private List<User> Users;

    public Role(){

    }

    public Role(String name) {
        Name = name;
    }
}
