package org.example.dienluc.service.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dienluc.config.Constants;
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientGetDto {
    private String firstName;
    private String lastName;
    private String phone;
    private String identityCard;
    private String birthday;
    private String address;
    private String email;
}
