package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.AssignmentModel;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.repository.AssignmentRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.service.AssignmentService;
import org.example.dienluc.service.dto.assignment.AssignmentCreateDto;
import org.example.dienluc.service.dto.assignment.AssignmentGetDto;
import org.example.dienluc.service.dto.assignment.AssignmentUpdateDto;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AssignmentServiceImpl implements AssignmentService {
    private final AssignmentRepository assignmentRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.assignmentRepository = assignmentRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public List<AssignmentGetDto> getAssignments() {
        List<Object[]> results = assignmentRepository.getAssignments();
        String[] fields = {"id","powerMeterId","installationLocation", "employeeIdAndFullName" };
        return MapperUtil.mapResults(results, AssignmentGetDto.class, fields);
    }

    @Override
    public AssignmentModel createAssignment(AssignmentCreateDto assignmentCreateDto) {
        AssignmentModel assignment = modelMapper.map(assignmentCreateDto, AssignmentModel.class);
        return assignmentRepository.save(assignment);
    }

    @Override
    public AssignmentModel updateAssignment(Integer assignmentId, AssignmentUpdateDto assignmentUpdateDto) {
        AssignmentModel assignmentModel = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new EntityNotFoundException("Assignment not find with id " + assignmentId));
        Employee employee = employeeRepository.findById(assignmentUpdateDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not find with id " + assignmentUpdateDto.getEmployeeId()));
        assignmentModel.setEmployee(employee);
        return assignmentRepository.save(assignmentModel);

    }
}
