package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterRecordableDto;
import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectricRecordingServiceImpl implements ElectricRecordingService {
    private final ElectricRecordingRepository electricRecordingRepository;
    private final ModelMapper modelMapper;

    public ElectricRecordingServiceImpl(ElectricRecordingRepository electricRecordingRepository, ModelMapper modelMapper) {
        this.electricRecordingRepository = electricRecordingRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ElectricRecordingGetDto> getAllElectricRecordings() {
        return electricRecordingRepository.findAllElectricRecording();

    }

    @Override
    public ElectricRecording createElectricRecording(ElectricRecordingCreateDto electricRecordingCreateDto) {
        return electricRecordingRepository.save(modelMapper.map(electricRecordingCreateDto, ElectricRecording.class));
    }

}
