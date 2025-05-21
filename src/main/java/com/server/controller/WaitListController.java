package com.server.controller;

import com.server.service.WaitListRequest;
import com.server.service.WaitListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/waitlist")
public class WaitListController {

    private static final Logger log = LoggerFactory.getLogger(WaitListController.class);
    private final WaitListService waitListService;

    @Autowired
    public WaitListController(WaitListService waitListService) {
        this.waitListService = waitListService;
    }

    @PostMapping
    public ResponseEntity<?> joinWaitList(@RequestBody WaitListRequest waitListRequest) {
        if (waitListRequest == null) {
            return ResponseEntity.badRequest().body("Missing body");
        }

        log.info("Email: {}, Project Name: {}", waitListRequest.email(), waitListRequest.projectName());

        var response = waitListService.joinWaitList(waitListRequest);

        if (!response.success()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response.message());
        }

        if (response.message() == null) {
            return ResponseEntity.status(HttpStatus.CREATED).body("Welcome to the waitlist!");
        }

        return ResponseEntity.ok(response.message());
    }


    @GetMapping("/{pId}")
    public ResponseEntity<?> getProjectEmailList(@PathVariable("pId") String projectId) {
        return waitListService.getProjectEmailList(projectId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found with ID " + projectId));
    }

    @GetMapping("/projects")
    public ResponseEntity<?> getAllProjects() {
        return ResponseEntity.ok(waitListService.getAllProjects());
    }
}
