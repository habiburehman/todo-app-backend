package com.example.backend.Models;

import jakarta.persistence.*;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.constraints.NotNull;

@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(nullable = false)
    private String description;

    @Column (name = "userId")
    private Long userId;


    public Task() {
    }

    public Task(Long id, String description) {
        this.id = id;
        this.description = description;
    }

    public Task(String description) {
        this.description = description;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUserId(Long userId) { this.userId = userId; }

    public Long getUserId () {return this.userId; }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
