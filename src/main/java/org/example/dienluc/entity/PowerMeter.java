package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Nationalized;

import java.util.Date;

@Entity
@Table(name = "DONGHODIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class PowerMeter {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NGAYLAPDAT")
    private String installationDate;
    @Nationalized
    @Column(name = "VITRILAPDAT")
    private String installationLocation;
    @Column(name = "TRANGTHAI")
    private Boolean status;
    @OneToOne(mappedBy = "powerMeter")
    private Contract contract;
    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = false; // Giá trị mặc định cho trường status
        }
    }
//    sử dụng Jackson để serialize đối tượng PowerMeter thành JSON,
//    nó có thể tự động gọi phương thức getter của các trường.
//    Trong trường hợp này, getIdAndInstallationLocation() sẽ được gọi để đưa giá trị vào trong kết quả JSON.
    // đánh dấu JsonIgnore để nó không xuất hien trong JSON trả về từ object
    @JsonIgnore
    public String getIdAndInstallationLocation(){
        return id + "-" + installationLocation;
    }

}
