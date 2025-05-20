package com.server.controller;

import com.server.service.WaitListRequest;
import com.server.service.WaitListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/waitlist")
public class WaitListController {

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
        boolean joinSuccessful = waitListService.joinWaitList(waitListRequest);
        return joinSuccessful ?
                new ResponseEntity<>(HttpStatus.CREATED)
                :
                ResponseEntity.badRequest().body("Project not found");
    }

    @GetMapping("/{pId}")
    public ResponseEntity<?> getProjectEmailList(@PathVariable("pId") String projectId) {
        return waitListService.getProjectEmailList(projectId)
                .<ResponseEntity<?>>map(ResponseEntity::ok)
                .orElse(ResponseEntity.status(HttpStatus.NOT_FOUND).body("Project not found with ID " + projectId));
    }
}
