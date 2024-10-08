package org.example.dienluc.service.dto.employee;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
