package com.server.service.impl;

import com.server.domain.Project;
import com.server.repository.ProjectRepository;
import com.server.service.ProjectService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final ProjectRepository projectRepository;

    @Autowired
    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }


    @Override
    public void newProject(Project project) {
        log.info("New project: {} - {}", project.getId(), project.getProjectName());
        projectRepository.save(project);
    }

    @Override
    public void deleteProject(Long projectId) {
        var project = projectRepository.findById(projectId);
        if (project.isPresent()) {
            projectRepository.delete(project.get());
            log.info("Deleted project: {}", projectId);
        } else {
            log.info("Project not deleted: Project NOT FOUND with ID: {}", projectId);
        }
    }

    @Override
    public Optional<Project> getProjectById(Long projectId) {
        return projectRepository.findById(projectId);
    }

    @Override
    public void updateProject(Project project) {
        var exists = projectRepository.findById(project.getId());
        if (exists.isPresent()) {
            projectRepository.save(project);
        } else {
            log.error("Cannot update project: NOT FOUND");
        }
    }

    @Override
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}
