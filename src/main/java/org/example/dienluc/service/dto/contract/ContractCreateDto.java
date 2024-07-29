package org.example.dienluc.service.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractCreateDto {
    private Integer clientId;
    private String startDate;
    private String electricitySupplyAddress;
    private Integer electricTypeId;
}
