package org.example.dienluc.service.dto.statistical.consumption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumptionByYearGetDto {
    private Integer month;
    private Double consumption;
}
