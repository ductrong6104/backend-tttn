package org.example.dienluc.service.dto.level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LevelGetByElectricRecordingDto {
    private Integer oldIndex;
    private Integer newIndex;
    private BigDecimal price;
}
