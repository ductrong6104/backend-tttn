package org.example.dienluc.service.dto.assignment;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentGetDto {
    private Integer id;
    private Integer powerMeterId;
    private String installationLocation;
    private String employeeIdAndFullName;
//    @JsonFormat(pattern = "yyyy-MM-dd") // Định dạng ngày
//    private String assignmentDate;
}
