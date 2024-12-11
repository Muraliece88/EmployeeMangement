
package com.abnamro.nl.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(indexes = @Index(columnList = "employee_id"))
public class Project {
    @jakarta.persistence.Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;
}

