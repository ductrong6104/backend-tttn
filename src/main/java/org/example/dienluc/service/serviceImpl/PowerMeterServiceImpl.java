package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.repository.PowerMeterRepository;
import org.example.dienluc.service.PowerMeterService;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterCreateDto;

import org.example.dienluc.service.dto.powerMeter.PowerMeterRecordableDto;
import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class PowerMeterServiceImpl implements PowerMeterService {
    private final PowerMeterRepository powerMeterRepository;
    private final ModelMapper modelMapper;

    public PowerMeterServiceImpl(PowerMeterRepository powerMeterRepository, ModelMapper modelMapper) {
        this.powerMeterRepository = powerMeterRepository;
        this.modelMapper = modelMapper;

    }

    @Override
    public List<PowerMeterAvailableDto> getAvailablePowerMeters() {
        return powerMeterRepository.findByStatus(true).stream()
                .map(powerMeter -> {
                    PowerMeterAvailableDto powerMeterAvailableDto = modelMapper.map(powerMeter, PowerMeterAvailableDto.class);
                    powerMeterAvailableDto.setInstallationLocation(powerMeter.getIdAndInstallationLocation());
                    return powerMeterAvailableDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<PowerMeter> getAllPowerMeter() {
        return powerMeterRepository.findAll();
    }

    @Override
    public PowerMeter updateStatusPowerMeter(Integer powerMeterId, Boolean status) {
        PowerMeter powerMeter = powerMeterRepository.findById(powerMeterId)
                .orElseThrow(() -> new EntityNotFoundException("Power meter not found with id: " + powerMeterId));
        powerMeter.setStatus(status);
        return powerMeterRepository.save(powerMeter);
    }

    @Override
    public PowerMeter createNewPowerMeter(PowerMeterCreateDto powerMeterCreateDto) {
        return powerMeterRepository.save(modelMapper.map(powerMeterCreateDto, PowerMeter.class));
    }
    @Override
    @Transactional(readOnly = true)
    public List<PowerMeterRecordableDto> getRecordablePowerMeters() {
        String startDate = DateUtil.getStartOfMonth();
        String endDate = DateUtil.getEndOfMonth();

        List<Object[]> results = powerMeterRepository.getRecordablePowerMeter(startDate, endDate);
        String[] fields = {"id", "installationLocation", "newIndex"};
        return MapperUtil.mapResults(results, PowerMeterRecordableDto.class, fields);
    }

}
