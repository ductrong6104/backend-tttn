package org.example.dienluc.service.dto.powerMeter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricRecordingHistoryByPowerMeterDto {
    private Integer id;
    private Integer powerMeterId;
    private String recordingDate;
    private Double oldIndex;
    private Double newIndex;
}
