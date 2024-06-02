package com.meraphorce.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(nullable = false, length = 255)
    private String name;

    @Column(nullable = false, length = 255)
    private String email;

    @Column(nullable = true, length = 255)
    private String password;
    
    @Column(nullable = true, length = 255)
    private String roles;
    
}
