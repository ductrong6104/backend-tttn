package org.example.dienluc.service.dto.electricityPrice;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ElectricityPriceDeleteDto {
    @NotNull
    private Integer electricTypeId;
    @NotNull
    private Integer levelId;
}
