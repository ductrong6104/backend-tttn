package org.example.dienluc.service.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractGetDto {
    private Integer contractId;
    private String firstName;
    private String lastName;
    private String startDate;
    private String endDate;
    private String electricitySupplyAddress;
    private String electricTypeName;
    private String processingEmployeeIdAndName;
}
