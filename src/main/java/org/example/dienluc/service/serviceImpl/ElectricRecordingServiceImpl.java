package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.mapper.Mapper;
import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingAssignedByEmployeeDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingAssignedDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingGetDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterAvailableDto;
import org.example.dienluc.service.dto.powerMeter.PowerMeterRecordableDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;
import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public List<ElectricRecordingAssignedDto> getAssignedElectricRecordings() {
        return electricRecordingRepository.findAll().stream()
                .map(electricRecording -> {
                    ElectricRecordingAssignedDto electricRecordingAssignedDto = modelMapper.map(electricRecording, ElectricRecordingAssignedDto.class);
                    electricRecordingAssignedDto.setEmployeeNameAndId(electricRecording.getEmployee().getIdAndFullName());
                    electricRecordingAssignedDto.setInstallationLocation(electricRecording.getPowerMeter().getInstallationLocation());
                    return electricRecordingAssignedDto;
                })
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public ElectricRecordingAssignedByEmployeeDto getAssignedElectricRecordingsByEmployeeId(Integer employeeId) {
        List<Object[]> results = electricRecordingRepository.getAssignedElectricRecordingsByEmployeeId(employeeId);
        String[] fields = {"powerMeterId","installationLocation", "oldIndex" };
        return results.isEmpty() ? null : MapperUtil.mapResults(results, ElectricRecordingAssignedByEmployeeDto.class, fields).get(0);
    }

    @Override
    public ElectricRecording updateElectricRecordingFirst(ElectricRecordingCreateDto electricRecordingCreateDto) {
        System.out.println(electricRecordingCreateDto);
        ElectricRecording electricRecording = electricRecordingRepository.findNotAssignedByPowerMeterIdAndEmployeeIdAndRecordingDate(electricRecordingCreateDto.getPowerMeterId(), electricRecordingCreateDto.getEmployeeId())
                        .orElseThrow(() -> new EntityNotFoundException("ElectricRecording to update first not found"));
        electricRecording.setOldIndex(electricRecordingCreateDto.getOldIndex());
        electricRecording.setNewIndex(electricRecordingCreateDto.getNewIndex());
        electricRecording.setRecordingDate(electricRecordingCreateDto.getRecordingDate());
        return electricRecordingRepository.save(electricRecording);


    }

    @Override
    public List<ElectricityUsedByClientDto> getElectricityUsedInYearByClientId(Integer clientId, String year) {
        List<Object[]> results = electricRecordingRepository.getElectricityUsedInYearByClientId(clientId, year);
        String[] fields = {"month", "electricUsed"};
        return MapperUtil.mapResults(results, ElectricityUsedByClientDto.class, fields);
    }

}
