package org.example.dienluc.service.dto.electricityPrice;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricityPriceGetDto {
    private Integer electricTypeId;
    private Integer levelId;
    private String electricTypeName;
    private String firstLevel;
    private String secondLevel;
    private BigDecimal price;
}
