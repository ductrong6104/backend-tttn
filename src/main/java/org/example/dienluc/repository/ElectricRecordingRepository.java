package org.example.dienluc.repository;

import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

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

}
