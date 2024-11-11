package org.example.dienluc.service.dto.powerMeter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PowerMeterInforByIds {
    private Integer id;
    private String installationDate;
    private String installationLocation;
    private Double longitude;
    private Double latitude;
}
