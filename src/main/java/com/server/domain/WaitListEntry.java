package com.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class WaitListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String email;

    @ManyToOne
    @JsonBackReference
    private Project project;

    @Column(nullable = false)
    private final LocalDateTime joinedAt;


    public WaitListEntry() {
        this.joinedAt = LocalDateTime.now();
    }

    public WaitListEntry(String email, Project project) {
        this.email = email;
        this.project = project;
        this.joinedAt = LocalDateTime.now();
    }


    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public String getEmail() {
        return email;
    }

    public LocalDateTime getJoinedAt() {
        return joinedAt;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "WaitListEntry{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", project=" + project.getId() +
                ", joinedAt=" + joinedAt +
                '}';
    }
}
