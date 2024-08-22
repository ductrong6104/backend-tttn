package org.example.dienluc.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.math.BigDecimal;

@Entity
@Table(name = "GIADIEN")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class ElectricityPrice {
    @EmbeddedId
    private ElectricityPriceId id;

    @MapsId("electricTypeId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDLOAIDIEN", nullable = false)
    private ElectricType electricType;

    @MapsId("levelId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "IDBAC", nullable = false)
    private Level level;

    @NotNull
    @Column(name = "DONGIA", nullable = false)
    private BigDecimal price;

    // Constructor để tạo ElectricityPrice với các tham số cần thiết
    public ElectricityPrice(ElectricType electricType, Level level, BigDecimal price) {
        this.id = new ElectricityPriceId(electricType.getId(), level.getId());
        this.electricType = electricType;
        this.level = level;
        this.price = price;
    }
}
