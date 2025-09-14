package com.example.CouponsManagement.Entity;

import com.example.CouponsManagement.Enums.UserRole;
import jakarta.persistence.*;

@Entity
@Table(name = "user_details")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    @Column(nullable = false, length = 10, unique = true)
    private String mobile;
    private Integer bXgYLimit;
    private UserRole role;
}
