package com.server.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "project_name", unique = true)
    private String projectName;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User owner;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<WaitListEntry> emailList = new ArrayList<>();

    public Project() {
        this.emailList = new ArrayList<>();
    }

    public Project(String projectName, List<WaitListEntry> emailList) {
        this.projectName = projectName;
        this.emailList = emailList;
    }

    public Long getId() {
        return id;
    }

    public List<WaitListEntry> getEmailList() {
        return emailList;
    }

    public String getProjectName() {
        return projectName;
    }

    public User getOwner() {
        return owner;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public void setEmailList(List<WaitListEntry> emailList) {
        this.emailList = emailList;
    }

    public void newEmailEntry(String email) {
        boolean exists = emailList.stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
        if (!exists) {
            WaitListEntry entry = new WaitListEntry(email, this);
            emailList.add(entry);
        }
    }

    public void addWaitListEntry(WaitListEntry entry) {
        emailList.add(entry);
        entry.setProject(this);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                ", owner=" + owner.getId() +
                ", emailList=" + emailList +
                '}';
    }
}
