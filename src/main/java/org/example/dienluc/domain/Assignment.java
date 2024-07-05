package org.example.dienluc.domain;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "CHITIETPHANCONG", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"powerMeter", "recordDate"})
})
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Assignment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Integer id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDNHANVIEN")
    private Employee employee;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "IDDONGHODIEN")
    private PowerMeter powerMeter;
    @Column(name = "NGAYGHICSD")
    private Date recordDate;
    @Column(name = "CHISOMOI")
    private Float indexNew;
    @Column(name = "CHISOCU")
    private Float indexOld;
}
