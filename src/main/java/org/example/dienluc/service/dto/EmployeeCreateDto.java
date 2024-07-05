package org.example.dienluc.service.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeCreateDto {
    private Integer id;
    private String firstName;
    private String lastName;
    private boolean gender;
    private String birthday;
    private String address;
    private String phone;
    private String email;
    private String identityCard;
}
