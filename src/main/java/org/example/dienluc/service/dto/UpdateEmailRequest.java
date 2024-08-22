package org.example.dienluc.service.dto;

import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dienluc.config.Constants;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateEmailRequest {
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
    private String email;
}
