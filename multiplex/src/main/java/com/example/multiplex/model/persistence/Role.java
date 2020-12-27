package com.example.multiplex.model.persistence;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.Set;

@Entity
@Table(name = "Roles")
public class Role implements GrantedAuthority {

    @Id
    private String name;

    // ----------- many to many -----------
    @ManyToMany(mappedBy = "roles", fetch = FetchType.EAGER)
    private Set<User> users;


    public Role(String name) {
        this.name = name;
    }

    public Role() {}

    @Override
    public String getAuthority() {
        return name;
    }
}
