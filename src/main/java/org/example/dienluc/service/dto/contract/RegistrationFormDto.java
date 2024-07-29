package org.example.dienluc.service.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationFormDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String startDate;
    private String electricitySupplyAddress;
    private Integer electricTypeId;

}
