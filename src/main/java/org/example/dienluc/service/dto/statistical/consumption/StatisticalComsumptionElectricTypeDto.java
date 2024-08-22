package org.example.dienluc.service.dto.statistical.consumption;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatisticalComsumptionElectricTypeDto {
    private String electricTypeName;
    private Double consumption;
}
