package org.example.dienluc.service.dto.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentCreateDto {
    private Integer powerMeterId;
    private Integer employeeId;
}
