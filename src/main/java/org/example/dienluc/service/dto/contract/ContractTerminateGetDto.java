package org.example.dienluc.service.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ContractTerminateGetDto {
    private Integer employeeId;
    private Integer powerMeterId;
}