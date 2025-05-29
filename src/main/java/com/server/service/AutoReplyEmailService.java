package com.server.service;

import com.server.domain.Project;

public interface AutoReplyEmailService {

    void sendWelcomeEmail(String project, int count, String receiverEmail);

}
