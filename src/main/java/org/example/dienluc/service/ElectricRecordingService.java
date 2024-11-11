package org.example.dienluc.service;

import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.service.dto.electricRecording.*;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;

import java.util.List;

public interface ElectricRecordingService {
    public List<ElectricRecordingGetDto> getAllElectricRecordings();

    public ElectricRecording createElectricRecording(ElectricRecordingCreateDto electricRecordingCreateDto);

    public List<ElectricRecordingAssignedDto> getAssignedElectricRecordings();

    public List<ElectricRecordingAssignedByEmployeeDto> getAssignedElectricRecordingsByEmployeeId(Integer employeeId);

    public ElectricRecording updateElectricRecordingFirst(ElectricRecordingCreateDto electricRecordingCreateDto);

    public List<ElectricityUsedByClientDto> getElectricityUsedInYearByClientId(Integer clientId, String year);


    public ElectricRecording updateElectricRecording(Integer electricRecordingId, ElectricRecordingUpdateDto electricRecordingUpdateDto);

    public String deleteElectricRecording(Integer electricRecordingId);

    public ElectricRecording updateElectricRecordingByEmployee(Integer electricRecordingId, ElectricRecordingUpdateByEmployeeDto electricRecordingUpdateByEmployeeDto);

    public String deleteRecordingByEmployee(Integer electricRecordingId);

    public List<ElectricRecordingHistoryByEmployeeDto> getRecordingHistoryByEmployee(Integer employeeId);

    public String automationAssignment(List<ElectricRecordingAutoAssign> electricRecordingAutoAssign);
}
