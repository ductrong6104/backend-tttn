package org.example.dienluc.service;

import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterCreateDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterRecordableDto;

import java.util.List;

public interface PowerMeterService {
    public List<PowerMeterAvailableDto> getAvailablePowerMeters();

    public List<PowerMeterGetDto> getAllPowerMeter();

    public PowerMeter updateStatusPowerMeter(Integer powerMeterId, Boolean status);

    public PowerMeter createNewPowerMeter(PowerMeterCreateDto powerMeterCreateDto);

    public List<PowerMeterRecordableDto> getRecordablePowerMeters();
}
