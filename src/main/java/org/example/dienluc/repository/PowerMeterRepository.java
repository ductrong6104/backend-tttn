package org.example.dienluc.repository;

import org.example.dienluc.entity.PowerMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerMeterRepository extends JpaRepository<PowerMeter, Integer> {
    public List<PowerMeter> findByStatus(Boolean status);
    @Procedure(procedureName = "sp_getRecordablePowerMeter")
    public List<Object[]> getRecordablePowerMeter(@Param("startDate") String startDate, @Param("endDate") String endDate);
}
