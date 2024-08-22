package org.example.dienluc.service.dto.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientGetInforDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private String phone;
    private String identityCard;
    private String birthday;
    private String address;
    private String email;
}
