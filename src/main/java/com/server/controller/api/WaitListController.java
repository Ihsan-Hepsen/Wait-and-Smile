package com.server.controller.api;

import com.server.utils.WaitListRequest;
import com.server.service.WaitListService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


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
    @ResponseBody
    public ResponseEntity<Map<String, String>> joinWaitList(@RequestBody WaitListRequest request) {
        String email = request.email();
        String projectName = request.projectName();

        Map<String, String> response = new HashMap<>();
        if (email.isBlank() || projectName.isBlank()) {
            response.put("status", "error");
            response.put("message", "Both email and project name are required");
            return ResponseEntity.badRequest().body(response);
        }
        log.info("Email: {}", email);

        WaitListRequest waitListRequest = new WaitListRequest(email, projectName);
        var serviceResponse = waitListService.joinWaitList(waitListRequest);

        if (!serviceResponse.success()) {
            response.put("status", "error");
            response.put("message", serviceResponse.message());
            return ResponseEntity.ok(response);
        }

        if (serviceResponse.message() == null) {
            response.put("status", "new");
            response.put("message", "Welcome to the waitlist!");
        } else {
            if (serviceResponse.message().toLowerCase().contains("already") ||
                    serviceResponse.message().toLowerCase().contains("exists")) {
                response.put("status", "exists");
            } else {
                response.put("status", "new");
            }
            response.put("message", serviceResponse.message());
        }

        return ResponseEntity.ok(response);
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
