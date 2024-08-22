package org.example.dienluc.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.Hibernate;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ElectricityPriceId implements Serializable {
    private static final long serialVersionUID = -8459645749592332109L;

    @NotNull
    @Column(name = "IDLOAIDIEN", nullable = false)
    private Integer electricTypeId;

    @NotNull
    @Column(name = "IDBAC", nullable = false)
    private Integer levelId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        ElectricityPriceId entity = (ElectricityPriceId) o;
        return Objects.equals(this.electricTypeId, entity.electricTypeId) &&
                Objects.equals(this.levelId, entity.levelId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(electricTypeId, levelId);
    }
}
