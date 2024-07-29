package org.example.dienluc.service;

import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;

import java.util.List;

public interface ElectricRecordingService {
    public List<ElectricRecordingGetDto> getAllElectricRecordings();

    public ElectricRecording createElectricRecording(ElectricRecordingCreateDto electricRecordingCreateDto);
}
