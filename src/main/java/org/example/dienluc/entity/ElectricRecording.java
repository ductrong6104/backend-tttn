package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.example.dienluc.util.DateUtil;

import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;


@Entity
@Table(name = "GHIDIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ElectricRecording {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID", nullable = false)
    private Integer id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDNHANVIEN", nullable = false)
    private Employee employee;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDDONGHODIEN", nullable = false)
    private PowerMeter powerMeter;

    @Column(name = "NGAYGHICSD", nullable = false)
    private String recordingDate;

    @Column(name = "CHISOMOI")
    private Double newIndex;

    @Column(name = "CHISOCU")
    private Double oldIndex;
    @PrePersist
    protected void onCreate(){
        if (recordingDate == null){
            recordingDate = DateUtil.formatDateForDatabase(LocalDate.now());
            newIndex = (double) 0;
            oldIndex = (double) 0;
        }
    }
    @JsonIgnore
    public Double getPowerConsumption(){
        return newIndex - oldIndex;
    }
}