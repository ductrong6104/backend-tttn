package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import org.hibernate.annotations.Nationalized;

import java.util.Date;

@Entity
@Table(name = "HOPDONG")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NGAYBDHD")
    private String startDate;
    @Column(name = "NGAYKTHD")
    private String endDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDLOAIDIEN")
    private ElectricType electricType;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDKHACHHANG")
    private Client client;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDDONGHODIEN")
    private PowerMeter powerMeter;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDNHANVIENXULY")
    private Employee processingEmployee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDTRANGTHAI")
    private ContractStatus contractStatus;
    @Column(name = "LYDOTUCHOI")
    private String reasonForRejection;
    @PrePersist
    protected void onCreate() {
        if (contractStatus == null){
            contractStatus = ContractStatus.builder()
                    .id(1)
                    .build();
        }
    }
}
