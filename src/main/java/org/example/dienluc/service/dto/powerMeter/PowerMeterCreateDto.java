package org.example.dienluc.service.dto.powerMeter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PowerMeterCreateDto {
    private String installationDate;
    private String installationLocation;
}
