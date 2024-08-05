package org.example.dienluc.service.dto.electricRecording;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingUpdateDto {
    private Integer powerMeterId;
    private Integer employeeId;
}
