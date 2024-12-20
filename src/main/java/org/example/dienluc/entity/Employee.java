package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.dienluc.config.Constants;
import org.hibernate.annotations.Nationalized;

@Entity
@Table(name = "NHANVIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Employee {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Nationalized
    @Column(name = "HO", length = 50)
    private String firstName;
    @Nationalized
    @Column(name = "TEN", length = 50)
    private String lastName;
    @Column(name = "GIOITINH")
    private boolean gender;
    @Column(name = "NGAYSINH")
    private String birthday;
    @Nationalized
    @Column(name = "DIACHI")
    private String address;
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
    @Column(name = "SDT", length = 10, unique = true)
    private String phone;
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
    @Column(name = "EMAIL", unique = true)
    private String email;
    @Pattern(regexp = Constants.CCCD_REGEX, message = "Invalid citizen identification number")
    @Column(name = "CCCD", length = 12, unique = true)
    private String identityCard;
    @Column(name = "NGHILAM")
    private boolean resignation;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "IDTAIKHOAN")
    private Account account;
    @Column(name = "KINHDO")
    private Double longitude;
    @Column(name = "VIDO")
    private Double latitude;
    @JsonIgnore
    public String getIdAndFullName(){
        return id + "-" + firstName + " " + lastName;
    }

}
