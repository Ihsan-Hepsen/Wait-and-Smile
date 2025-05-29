package com.server.service.impl;

import com.server.domain.Project;
import com.server.domain.WaitListEntry;
import com.server.repository.ProjectRepository;
import com.server.repository.WaitListEntryRepository;
import com.server.service.AutoReplyEmailService;
import com.server.service.WaitListService;
import com.server.utils.WaitListRequest;
import com.server.utils.WaitListResponse;
import com.server.utils.WaitListResponseFactory;
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
    private final AutoReplyEmailService emailService;

    @Autowired
    public WaitListServiceImpl(WaitListEntryRepository waitListEntryRepository, ProjectRepository projectRepository, AutoReplyEmailService emailService) {
        this.waitListEntryRepository = waitListEntryRepository;
        this.projectRepository = projectRepository;
        this.emailService = emailService;
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

        boolean wasAdded = project.addWaitListEntry(email);
        if (!wasAdded) {
            // Email already exists
            return WaitListResponseFactory.alreadyExists();
        }

        projectRepository.save(project);

        int waitlistSize = project.getWaitlistSize();
        emailService.sendWelcomeEmail(project.getProjectName(), waitlistSize, email);

        return WaitListResponseFactory.success();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<List<WaitListEntry>> getProjectEmailList(String projectId) {
        if (projectId == null) {
            return Optional.empty();
        }

        var project = projectRepository.findByProjectName(projectId);
        return project.map(Project::getEmailList).map(ArrayList::new);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Project> getAllProjects() {
        return projectRepository.findAll();
    }
}