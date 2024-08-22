package org.example.dienluc.repository;

import org.example.dienluc.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    @Query("SELECT l FROM Level l where l.id not in (select ep.level.id from ElectricityPrice ep where ep.electricType.id = ?1)")
    public List<Level> getLevelNotUseByElectricType(Integer electricTypeId);
}
