package org.example.dienluc.service.dto.contract;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegistrationFormGetDto {
    private Integer id;
    private String idAndFullName;
    private String startDate;
    private String electricitySupplyAddress;
    private String electricTypeName;
    private String reasonForRejection;
    private String nameStatus;
}
