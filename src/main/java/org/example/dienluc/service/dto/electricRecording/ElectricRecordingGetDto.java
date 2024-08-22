package org.example.dienluc.service.dto.electricRecording;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingGetDto {
    private Integer id;
    private Integer powerMeterId;
    private String recordingDate;
    private Double oldIndex;
    private Double newIndex;
    private String employeeIdAndFullName;
    private Boolean invoiced;
}
