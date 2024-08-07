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
public class ElectricPriceGetPriceDto {
    private BigDecimal price;
}
