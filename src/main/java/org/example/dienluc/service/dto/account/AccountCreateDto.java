package org.example.dienluc.service.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountCreateDto {
    private String username;
    private String password;
    private Boolean disabled;
    private Integer roleId;
}
