package org.example.dienluc.service.dto.statistical.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AmountEveryMonthInEveryYearGetDto {
    private Integer year;
    private Integer month;
    private BigDecimal amount;
}
