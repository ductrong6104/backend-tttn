package org.example.dienluc.service.dto;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dienluc.config.Constants;
import org.example.dienluc.domain.Account;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private Boolean gender;
    private String birthday;
    private String address;
    private String phone;
    private String email;
    private String identityCard;
    private Boolean resignation;
}
