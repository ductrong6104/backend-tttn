package org.example.dienluc.controller;

import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.AssignmentService;
import org.example.dienluc.service.dto.assignment.AssignmentCreateDto;
import org.example.dienluc.service.dto.assignment.AssignmentUpdateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/assignments")
public class AssignmentController {
    private final AssignmentService assignmentService;

    public AssignmentController(AssignmentService assignmentService) {
        this.assignmentService = assignmentService;
    }

    @GetMapping("/assigned")
    public ResponseEntity<?> getAssignments() {
        ResponseData responseData = ResponseData.builder()
                .data(assignmentService.getAssignments())
                .message("get all assigned electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("")
    public ResponseEntity<?> createAssignment(@RequestBody AssignmentCreateDto assignmentCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(assignmentService.createAssignment(assignmentCreateDto))
                .message("create new assignment")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{assignmentId}")
    public ResponseEntity<?> updateAssignment(@PathVariable Integer assignmentId, @RequestBody AssignmentUpdateDto assignmentUpdateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(assignmentService.updateAssignment(assignmentId, assignmentUpdateDto))
                .message("update assignment")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

}
