package org.example.dienluc.service.dto.electricRecording;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingUpdateByEmployeeDto {
    private Integer powerMeterId;
    private Integer employeeId;
    private String recordingDate;
    private Double newIndex;
}
