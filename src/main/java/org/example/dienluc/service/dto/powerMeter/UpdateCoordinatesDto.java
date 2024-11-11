package org.example.dienluc.service.dto.powerMeter;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCoordinatesDto {
    private Integer idPowerMeter;
    private Double longitude;
    private Double latitude;
    private String installationLocation;
}
