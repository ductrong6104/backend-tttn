package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "HOADON")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Bill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @Column(name = "NGAYLAPHD")
    private String invoiceDate;
    @Column(name = "NGAYHANTT")
    private String paymentDueDate;
    @Column(name = "NGAYDONGTIEN")
    private String paymentDate;
    @Column(name = "TONGTIEN")
    private BigDecimal totalAmount;
    @Column(name = "TRANGTHAI")
    private Boolean status;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDGHIDIEN", nullable = false)
    private ElectricRecording electricRecording;
    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDNHANVIENTAO")
    private Employee invoiceCreator;
    @PrePersist
    protected void onCreate() {
        if (status == null) {
            status = false; // Giá trị mặc định cho trường status
        }
    }


}
