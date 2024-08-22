package org.example.dienluc.service.dto.client;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dienluc.config.Constants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientUpdateReasonRejectDto {
    private String fullName;
    private String birthday;
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
    private String phone;
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
    private String email;
    @Pattern(regexp = Constants.CCCD_REGEX, message = "Invalid citizen identification number")
    private String identityCard;
}
