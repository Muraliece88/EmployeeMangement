
package com.abnamro.nl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
public class Role {
    @Id
    private Long Id;
    private String name;
    @OneToMany(mappedBy = "role", cascade = CascadeType.PERSIST,orphanRemoval = true)
    private Set<Employee> employee= new HashSet<>();
}

