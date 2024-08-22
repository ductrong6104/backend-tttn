package org.example.dienluc.service.dto.statistical.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectricityUsedByClientDto {
    private Integer month;
    private Double electricUsed;
}
