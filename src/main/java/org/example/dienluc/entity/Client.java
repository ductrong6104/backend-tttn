package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import org.example.dienluc.config.Constants;
import org.hibernate.annotations.Nationalized;

import java.util.List;

@Entity
@Table(name = "KHACHHANG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
@ToString(exclude = {"contracts", "account"})
public class Client {
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
    private Boolean gender;
    @Column(name = "NGAYSINH")
    private String birthday;
    @Email(regexp = Constants.EMAIL_REGEX, message = "Invalid email address")
    @Column(name = "EMAIL")
    private String email;
    @Pattern(regexp = Constants.PHONE_REGEX, message = "Invalid phone number")
    @Column(name = "SDT", length = 10)
    private String phone;
    @Nationalized
    @Column(name = "DIACHI")
    private String address;
    @Pattern(regexp = Constants.CCCD_REGEX, message = "Invalid citizen identification number")
    @Column(name = "CCCD", length = 12)
    private String identityCard;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTAIKHOAN")
    private Account account;
    @JsonIgnore
    public String getFullName(){
        return firstName + " " + lastName;
    }
    @JsonIgnore
    @OneToMany(mappedBy = "client", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<Contract> contracts;

    @JsonIgnore
    public void setFirstNameLastName(String fullName){
        if (fullName != null && !fullName.trim().isEmpty()) {
            String[] nameParts = fullName.trim().split("\\s+");
            if (nameParts.length > 0) {
                this.firstName = nameParts[0];
            }
            if (nameParts.length > 1) {
                this.lastName = nameParts[nameParts.length - 1];
            }
            // Optional: Handle middle names or other name parts if needed
        }
    }
    @JsonIgnore
    public String getIdAndFullName(){
        return id + "-" + firstName + " " + lastName;
    }
}
