package com.server.domain;

import jakarta.persistence.*;

@Entity
@Table(name = "users")
public class User {

    @Id
    private String id;

    private String name;
    private String email;

    @OneToOne(mappedBy = "owner", cascade = CascadeType.ALL)
    private Project project;

    public User() {

    }

    public User(String name, String email) {
        this.name = name;
        this.email = email;
        this.project = null;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public Project getProject() {
        return project;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", projectId=" + (project != null ? project.getId() : "NO ASSOCIATED PROJECT") +
                '}';
    }
}
