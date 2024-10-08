package org.example.dienluc.service.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountGetDto {
    private Integer id;
    private String username;
    private String password;
    private Boolean disabled;
    private String employeeIdAndName;
}
