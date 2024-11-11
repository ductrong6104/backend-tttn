package org.example.dienluc.service.dto.employee;

import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String username;
    private String password;
    private Integer roleId;
    private Double longitude;
    private Double latitude;
}
