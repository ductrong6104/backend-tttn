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
    private String idAndFullName;
    private String startDate;
    private String electricitySupplyAddress;
    private String electricTypeName;
    private String reasonForRejection;

}
