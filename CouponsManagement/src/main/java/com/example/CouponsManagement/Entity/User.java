package com.example.CouponsManagement.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "user_details")
public class User {
    private Long id;
    private String name;
    private String mobile;
    private Integer bXgYLimit;
}
