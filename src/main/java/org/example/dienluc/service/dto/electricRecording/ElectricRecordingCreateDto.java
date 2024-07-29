package org.example.dienluc.service.dto.electricRecording;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingCreateDto {
    private Integer employeeId;
    private Integer powerMeterId;
    private String recordingDate;
    private Double oldIndex;
    private Double newIndex;
}
