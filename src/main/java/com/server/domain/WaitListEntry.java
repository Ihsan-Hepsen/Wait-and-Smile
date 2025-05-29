package com.server.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "waitlist_entries")
public class WaitListEntry {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String email;

    @ManyToOne
    @JoinColumn(name = "project_id")
    @JsonBackReference
    private Project project;

    @Column(nullable = false)
    private final LocalDateTime joinedAt;

    public WaitListEntry() {
        this.joinedAt = LocalDateTime.now();
    }

    public WaitListEntry(String email, Project project) {
        this.email = email;
        this.joinedAt = LocalDateTime.now();

        if (project != null) {
            project.addWaitListEntry(this);
        }
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

    // NOTE: only to be called by Project.addWaitListEntry()
    void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "WaitListEntry{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", project=" + (project != null ? project.getId() : "null") +
                ", joinedAt=" + joinedAt +
                '}';
    }
}
