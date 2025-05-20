package com.server.service;

import com.server.domain.WaitListEntry;

import java.util.List;
import java.util.Optional;

public interface WaitListService {

    boolean joinWaitList(WaitListRequest waitListRequest);
    Optional<List<WaitListEntry>> getProjectEmailList(String projectId);

}
