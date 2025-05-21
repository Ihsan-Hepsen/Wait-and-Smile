package com.server.service;

import com.server.domain.Project;
import com.server.domain.WaitListEntry;
import com.server.utils.WaitListRequest;
import com.server.utils.WaitListResponse;

import java.util.List;
import java.util.Optional;

public interface WaitListService {

    WaitListResponse joinWaitList(WaitListRequest waitListRequest);
    Optional<List<WaitListEntry>> getProjectEmailList(String projectId);
    List<Project> getAllProjects();
}
