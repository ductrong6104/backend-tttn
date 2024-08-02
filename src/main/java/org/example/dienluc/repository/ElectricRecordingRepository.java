package org.example.dienluc.repository;

import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface ElectricRecordingRepository extends JpaRepository<ElectricRecording, Integer> {
    @Query("SELECT new org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto (" +
            "er.id, " +
            "er.powerMeter.id, " +
            "er.recordingDate, " +
            "er.oldIndex, " +
            "er.newIndex, " +
            "CONCAT(CAST(er.employee.id AS string), '-', er.employee.firstName, ' ', er.employee.lastName), " +
            "CASE WHEN er.id IN (SELECT b.electricRecording.id FROM Bill b) THEN TRUE ELSE FALSE END) " +
            "FROM ElectricRecording er")
    public List<ElectricRecordingGetDto> findAllElectricRecording();
    @Procedure("sp_getAssignedElectricRecordingsByEmployeeId")
    public List<Object[]> getAssignedElectricRecordingsByEmployeeId(@Param("employeeId") Integer employeeId);

    @Query("Select er from ElectricRecording er where er.powerMeter.id = ?1 and er.employee.id = ?2 and er.recordingDate is null")
    public Optional<ElectricRecording> findNotAssignedByPowerMeterIdAndEmployeeIdAndRecordingDate(Integer powerMeterId, Integer employeeId);
    @Query(value = "SELECT MONTH(NGAYGHICSD) AS month, (CHISOMOI - CHISOCU) AS electricUsed from ghidien where IDDONGHODIEN in (select IDDONGHODIEN from HOPDONG where IDKHACHHANG = :clientId)\n" +
            "and year(NGAYGHICSD) = :year",
            nativeQuery = true)
    List<Object[]> getElectricityUsedInYearByClientId(@Param("clientId") Integer clientId, @Param("year") String year);

}
