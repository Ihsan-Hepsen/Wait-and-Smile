package com.server.service;

import com.server.domain.Project;
import com.server.domain.WaitListEntry;
import com.server.repository.ProjectRepository;
import com.server.repository.WaitListEntryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class WaitListServiceImpl implements WaitListService {

    private final WaitListEntryRepository waitListEntryRepository;
    private final ProjectRepository projectRepository;

    @Autowired
    public WaitListServiceImpl(WaitListEntryRepository waitListEntryRepository, ProjectRepository projectRepository) {
        this.waitListEntryRepository = waitListEntryRepository;
        this.projectRepository = projectRepository;
    }

    @Override
    public boolean joinWaitList(WaitListRequest waitListRequest) {
        String projectId = waitListRequest.projectId();
        Optional<Project> projectOpt = projectRepository.findByProjectId(projectId);

        if (projectOpt.isEmpty()) {
            return false;
        }

        Project project = projectOpt.get();
        String email = waitListRequest.email();
        boolean alreadyExists = project.getEmailList().stream()
                .anyMatch(entry -> entry.getEmail().equalsIgnoreCase(email));

        if (alreadyExists) {
            return false;
        }

        WaitListEntry entry = new WaitListEntry(email, project);
        waitListEntryRepository.save(entry);
        return true;
    }


    @Override
    public Optional<List<WaitListEntry>> getProjectEmailList(String projectId) {
        if (projectId == null) {
            return Optional.empty();
        }
        var project = projectRepository.findByProjectId(projectId);
        return project.map(Project::getEmailList)
                .map(ArrayList::new);
    }
}
