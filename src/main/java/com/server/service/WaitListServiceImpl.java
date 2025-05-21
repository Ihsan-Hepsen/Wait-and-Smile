package com.server.service;

import com.server.domain.Project;
import com.server.domain.WaitListEntry;
import com.server.repository.ProjectRepository;
import com.server.repository.WaitListEntryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WaitListServiceImpl implements WaitListService {

    private static final Logger log = LoggerFactory.getLogger(WaitListServiceImpl.class);
    private final WaitListEntryRepository waitListEntryRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public WaitListServiceImpl(WaitListEntryRepository waitListEntryRepository, ProjectRepository projectRepository) {
        this.waitListEntryRepository = waitListEntryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    @Transactional
    public WaitListResponse joinWaitList(WaitListRequest waitListRequest) {
        String projectName = waitListRequest.projectName();
        Optional<Project> projectOpt = projectRepository.findByProjectName(projectName);

        if (projectOpt.isEmpty()) {
            log.warn("Project not found: {}", projectName);
            return WaitListResponseFactory.error("Project not found: " + projectName);
        }

        Project project = projectOpt.get();
        String email = waitListRequest.email();
        boolean alreadyExists = project.getEmailList().stream()
                .anyMatch(entry -> entry.getEmail().equalsIgnoreCase(email));

        if (alreadyExists) {
            return WaitListResponseFactory.alreadyExists();
        }

        WaitListEntry entry = new WaitListEntry(email, project);
        waitListEntryRepository.save(entry);
        return WaitListResponseFactory.success();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<WaitListEntry>> getProjectEmailList(String projectId) {
        if (projectId == null) {
            return Optional.empty();
        }

        var project = projectRepository.findByProjectName(projectId);
        return project.map(Project::getEmailList)
                .map(ArrayList::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}