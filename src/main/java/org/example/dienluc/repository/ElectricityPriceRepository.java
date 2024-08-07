package org.example.dienluc.repository;

import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.entity.ElectricityPrice;
import org.example.dienluc.entity.Level;
import org.example.dienluc.service.dto.electricityPrice.ElectricPriceGetPriceDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ElectricityPriceRepository extends JpaRepository<ElectricityPrice, Integer> {
    public int deleteByElectricTypeAndLevel(ElectricType electricType, Level level);
    @Query("SELECT ep FROM ElectricityPrice ep WHERE ep.electricType.id = ?1 AND ep.level.id = ?2")
    public ElectricityPrice findByElectricTypeIdAndLevelId(Integer electricTypeId, Integer levelId);

    @Query(value = "select DONGIA as price from giadien where IDBAC IN (select ID from bac where (MUCDAU <= ?1 OR MUCDAU is null) and (MUCCUOI >= ?1 or MUCCUOI is null)) and IDLOAIDIEN = ?2", nativeQuery = true)
    public List<Object[]> findPriceByElectricTypeAndElectricUsed(Double electricUsed, Integer id);
}
