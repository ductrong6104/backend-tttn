package org.example.dienluc.service.dto.account;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AccountNewDto {
    private Integer accountId;
    private Integer clientId;
    private String username;
    private Boolean disabled;
    private String roleName;
}
