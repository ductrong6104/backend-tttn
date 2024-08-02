package org.example.dienluc.service;

import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingAssignedByEmployeeDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingAssignedDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;

import java.util.List;

public interface ElectricRecordingService {
    public List<ElectricRecordingGetDto> getAllElectricRecordings();

    public ElectricRecording createElectricRecording(ElectricRecordingCreateDto electricRecordingCreateDto);

    public List<ElectricRecordingAssignedDto> getAssignedElectricRecordings();

    public ElectricRecordingAssignedByEmployeeDto getAssignedElectricRecordingsByEmployeeId(Integer employeeId);

    public ElectricRecording updateElectricRecordingFirst(ElectricRecordingCreateDto electricRecordingCreateDto);

    public List<ElectricityUsedByClientDto> getElectricityUsedInYearByClientId(Integer clientId, String year);
}
