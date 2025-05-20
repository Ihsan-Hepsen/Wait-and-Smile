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

    @Column(unique = true)
    private String projectId;

    @OneToMany(mappedBy = "project", orphanRemoval = true)
    @JsonManagedReference
    private List<WaitListEntry> emailList = new ArrayList<>();


    public Project() {
        this.emailList = new ArrayList<>();
    }

    public Project(String projectId, List<WaitListEntry> emailList) {
        this.projectId = projectId;
        this.emailList = emailList;
    }

    public Long getId() {
        return id;
    }

    public List<WaitListEntry> getEmailList() {
        return emailList;
    }

    public String getProjectId() {
        return projectId;
    }

    public void setProjectId(String projectId) {
        this.projectId = projectId;
    }

    public void setEmailList(List<WaitListEntry> emailList) {
        this.emailList = emailList;
    }

    public void addEmail(String email) {
        boolean exists = emailList.stream()
                .anyMatch(e -> e.getEmail().equalsIgnoreCase(email));
        if (!exists) {
            emailList.add(new WaitListEntry(email, this));
        }
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", projectId='" + projectId + '\'' +
                ", emailList=" + emailList +
                '}';
    }
}
