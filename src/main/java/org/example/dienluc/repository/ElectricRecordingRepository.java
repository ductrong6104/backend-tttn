package org.example.dienluc.repository;

import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingRecentDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
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

    @Query(value = "SELECT MONTH(NGAYGHICSD) AS month, SUM(CHISOMOI - CHISOCU) AS electricUsed from ghidien where IDDONGHODIEN in (select IDDONGHODIEN from HOPDONG where IDKHACHHANG = :clientId)\n" +
            "and year(NGAYGHICSD) = :year GROUP BY MONTH(NGAYGHICSD)",
            nativeQuery = true)
    List<Object[]> getElectricityUsedInYearByClientId(@Param("clientId") Integer clientId, @Param("year") String year);

    // convert GETDATE() sang DATE() để chỉ so sanh dddd,mm,yy không cần so hh,mm,ss
    @Query(value = "SELECT IDNHANVIEN as employeeId, IDDONGHODIEN as powerMeterId\n" +
            "FROM GHIDIEN \n" +
            "WHERE IDDONGHODIEN = (SELECT IDDONGHODIEN FROM hopdong WHERE ID = ?1) \n" +
            "AND NGAYGHICSD < CAST(GETDATE() AS DATE)\n" +
            "AND NOT EXISTS (\n" +
            "    SELECT ID\n" +
            "    FROM GHIDIEN \n" +
            "    WHERE IDDONGHODIEN = (SELECT IDDONGHODIEN FROM hopdong WHERE ID = ?1) \n" +
            "    AND NGAYGHICSD = CAST(GETDATE() AS DATE)\n" +
            ") GROUP BY IDNHANVIEN, IDDONGHODIEN", nativeQuery = true)
    public List<Object[]> getElectricRecordingRecentByContract(Integer contractId);

    public Optional<ElectricRecording> findByPowerMeterAndRecordingDateIsNull(PowerMeter powerMeter);

    @Query(value = "select concat(DAY(NGAYGHICSD), '/', MONTH(NGAYGHICSD)) as dayMonth, SUM(CHISOMOI- CHISOCU) as consumption from GHIDIEN where NGAYGHICSD >= '2024-07-01' and NGAYGHICSD <= '2024-08-01' GROUP BY NGAYGHICSD", nativeQuery = true)
    public List<Object[]> getConsumptionFromDateToDate(String fromDate, String toDate);

    @Query(value = "SELECT MAX(NGAYGHICSD) as fromDate from ghidien where NGAYGHICSD < ?1 AND IDDONGHODIEN = ?2 GROUP BY IDDONGHODIEN", nativeQuery = true)
    public String getRecordingDateBefore(String toDate, Integer powerMeterId);

    @Query(value = "select month = MONTH(NGAYGHICSD), SUM(CHISOMOI - CHISOCU) as consumption from GHIDIEN WHERE YEAR(NGAYGHICSD) = ?1 GROUP BY MONTH(NGAYGHICSD)", nativeQuery = true)
    List<Object[]> getConsumptionByYear(Integer year);

    @Query(value = "select MONTH(NGAYDONGTIEN) as month, SUM(TONGTIEN) as revenue from HOADON WHERE TRANGTHAI = 1 AND YEAR(NGAYDONGTIEN) = ?1 GROUP BY MONTH(NGAYDONGTIEN)", nativeQuery = true)
    List<Object[]> getRevenueByYear(Integer year);

    @Query("Select er.id, er.powerMeter.id as powerMeterId, er.recordingDate, er.oldIndex, er.newIndex from ElectricRecording er where er.employee.id = ?1 and er.recordingDate IS NOT NULL ORDER BY er.recordingDate DESC")
    List<Object[]> findByEmployeeIdOrderByRecordingDate(Integer employeeId);
}
