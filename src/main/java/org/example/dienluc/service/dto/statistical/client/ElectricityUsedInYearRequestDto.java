package org.example.dienluc.service.dto.statistical.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ElectricityUsedInYearRequestDto {
    private Integer clientId;
    private String year;
}
