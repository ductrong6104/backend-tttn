package org.example.dienluc.service.dto.statistical.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ConsumptionFromDateToDateGetDto {
    private String dayMonth;
    private Double consumption;
}
