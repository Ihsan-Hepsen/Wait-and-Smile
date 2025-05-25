package com.server.service;

import com.server.domain.Project;

import java.util.List;
import java.util.Optional;

public interface ProjectService {

    void newProject(Project project);
    void deleteProject(Long projectId);
    Optional<Project> getProjectById(Long projectId);
    void updateProject(Project project);
    List<Project> getAllProjects();

}
