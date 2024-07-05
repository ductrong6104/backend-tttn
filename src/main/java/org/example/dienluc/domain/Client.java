package org.example.dienluc.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dienluc.config.Constants;

@Entity
@Table(name = "KHACHHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "HO", length = 50)
    private String firstName;
    @Column(name = "TEN", length = 50)
    private String lastName;
    @Column(name = "GIOITINH")
    private Boolean gender;
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
    @Column(name = "EMAIL")
    private String email;
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
    @Column(name = "SDT", length = 10)
    private String phone;
    @Column(name = "DIACHI", length = 100)
    private String address;
    @Pattern(regexp = Constants.CCCD_REGEX, message = "Invalid citizen identification number")
    @Column(name = "CCCD", length = 12)
    private String identityCard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTAIKHOAN")
    private Account account;
}
