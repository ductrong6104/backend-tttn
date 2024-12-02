package org.example.dienluc.service;

import org.example.dienluc.entity.AssignmentModel;
import org.example.dienluc.mapper.Assignment;
import org.example.dienluc.service.dto.assignment.AssignmentCreateDto;
import org.example.dienluc.service.dto.assignment.AssignmentGetDto;
import org.example.dienluc.service.dto.assignment.AssignmentUpdateDto;

import java.util.List;

public interface AssignmentService {
    public List<AssignmentGetDto> getAssignments();

    public AssignmentModel createAssignment(AssignmentCreateDto assignmentCreateDto);

    public AssignmentModel updateAssignment(Integer assignmentId, AssignmentUpdateDto assignmentUpdateDto);
}
