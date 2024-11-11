package org.example.dienluc.service.dto.electricRecording;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingAssignedDto {
    private Integer id;
    private Integer powerMeterId;
    private String installationLocation;
    private String recordingDate;
    private String employeeNameAndId;
    private Double longitude;
    private Double latitude;
}
