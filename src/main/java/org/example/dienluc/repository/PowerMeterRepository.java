package org.example.dienluc.repository;

import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.powerMeter.PowerMeterGetDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PowerMeterRepository extends JpaRepository<PowerMeter, Integer> {
    public List<PowerMeter> findByStatus(Boolean status);
    @Procedure(procedureName = "sp_getRecordablePowerMeter")
    public List<Object[]> getRecordablePowerMeter(@Param("startDate") String startDate, @Param("endDate") String endDate);
    @Query("""
    SELECT pm.id, pm.installationDate, pm.installationLocation, pm.longitude, pm.latitude
      FROM PowerMeter pm where pm.id in ?1       
            """
    )
    public List<Object[]> getInforPowerMeters(List<Integer> powerMeterIds);
    @Query(value = "select * from DONGHODIEN WHERE ID = (SELECT IDDONGHODIEN FROM HOPDONG WHERE ID = ?1)", nativeQuery = true)
    public PowerMeter getPowerMeterByContract(Integer contractId);
}
