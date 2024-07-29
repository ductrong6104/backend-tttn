package org.example.dienluc.repository;

import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.entity.ElectricityPrice;
import org.example.dienluc.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ElectricityPriceRepository extends JpaRepository<ElectricityPrice, Integer> {
    public int deleteByElectricTypeAndLevel(ElectricType electricType, Level level);
    @Query("SELECT ep FROM ElectricityPrice ep WHERE ep.electricType.id = ?1 AND ep.level.id = ?2")
    public ElectricityPrice findByElectricTypeIdAndLevelId(Integer electricTypeId, Integer levelId);
}
