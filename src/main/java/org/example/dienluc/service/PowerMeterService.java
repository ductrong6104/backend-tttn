package org.example.dienluc.service;

import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.powerMeter.*;

import java.util.List;

public interface PowerMeterService {
    public List<PowerMeterAvailableDto> getAvailablePowerMeters();

    public List<PowerMeterGetDto> getAllPowerMeter();

    public PowerMeter updateStatusPowerMeter(Integer powerMeterId, Boolean status);

    public PowerMeter createNewPowerMeter(PowerMeterCreateDto powerMeterCreateDto);

    public List<PowerMeterRecordableDto> getRecordablePowerMeters();

    public PowerMeter updateCoordinates(Integer powerMeterId, UpdateCoordinatesDto updateCoordinatesDto);

    public String getAutomationAssignment();

    public List<PowerMeterInforByIds> getInforPowerMeters(List<Integer> powerMeterIds);

    public PowerMeter getPowerMeterByContract(Integer contractId);
}
