package org.example.dienluc.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Assignment {
    private String employeeId;
    private List<AssignmentDetail> powerMeters;
}
