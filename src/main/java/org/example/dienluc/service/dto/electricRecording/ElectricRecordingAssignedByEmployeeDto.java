package org.example.dienluc.service.dto.electricRecording;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingAssignedByEmployeeDto {
    private Integer powerMeterId;
    private String installationLocation;
    private Double oldIndex;
}
