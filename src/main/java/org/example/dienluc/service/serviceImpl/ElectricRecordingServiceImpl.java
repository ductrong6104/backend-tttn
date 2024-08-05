package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.apache.catalina.mapper.Mapper;
import org.example.dienluc.entity.ElectricRecording;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.repository.PowerMeterRepository;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.electricRecording.*;
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
    private final EmployeeRepository employeeRepository;
    private final PowerMeterRepository powerMeterRepository;

    public ElectricRecordingServiceImpl(ElectricRecordingRepository electricRecordingRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository, PowerMeterRepository powerMeterRepository) {
        this.electricRecordingRepository = electricRecordingRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.powerMeterRepository = powerMeterRepository;
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

    @Override
    public ElectricRecording updateElectricRecording(Integer electricRecordingId, ElectricRecordingUpdateDto electricRecordingUpdateDto) {
        Employee employee = employeeRepository.findById(electricRecordingUpdateDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + electricRecordingUpdateDto.getEmployeeId()));
        PowerMeter powerMeter = powerMeterRepository.findById(electricRecordingUpdateDto.getPowerMeterId())
                .orElseThrow(() -> new EntityNotFoundException("PowerMeter not found with id: " + electricRecordingUpdateDto.getPowerMeterId()));
        ElectricRecording electricRecording = electricRecordingRepository.findById(electricRecordingId)
                .orElseThrow(() -> new EntityNotFoundException("ElectricRecording to update first not found"));
        electricRecording.setEmployee(employee);
        electricRecording.setPowerMeter(powerMeter);
        return electricRecordingRepository.save(electricRecording);
    }

    @Override
    public String deleteElectricRecording(Integer electricRecordingId) {
        if (electricRecordingRepository.existsById(electricRecordingId)){
            electricRecordingRepository.deleteById(electricRecordingId);
            return "Xóa phân công thành công";
        } else {
            throw new EntityNotFoundException("Electric Recording not found with username: " + electricRecordingId);
        }
    }

    @Override
    public ElectricRecording updateElectricRecordingByEmployee(Integer electricRecordingId, ElectricRecordingUpdateByEmployeeDto electricRecordingUpdateByEmployeeDto) {
        Employee employee = employeeRepository.findById(electricRecordingUpdateByEmployeeDto.getEmployeeId())
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + electricRecordingUpdateByEmployeeDto.getEmployeeId()));
        PowerMeter powerMeter = powerMeterRepository.findById(electricRecordingUpdateByEmployeeDto.getPowerMeterId())
                .orElseThrow(() -> new EntityNotFoundException("PowerMeter not found with id: " + electricRecordingUpdateByEmployeeDto.getPowerMeterId()));
        ElectricRecording electricRecording = electricRecordingRepository.findById(electricRecordingId)
                .orElseThrow(() -> new EntityNotFoundException("ElectricRecording not found with id: " + electricRecordingId));
        System.out.println(electricRecording);
        electricRecording.setRecordingDate(electricRecordingUpdateByEmployeeDto.getRecordingDate());
        electricRecording.setNewIndex(electricRecordingUpdateByEmployeeDto.getNewIndex());
        electricRecording.setEmployee(employee);
        electricRecording.setPowerMeter(powerMeter);
        System.out.println(electricRecording);
        return electricRecordingRepository.save(electricRecording);
    }

    @Override
    public String deleteRecordingByEmployee(Integer electricRecordingId) {
        ElectricRecording electricRecording = electricRecordingRepository.findById(electricRecordingId)
                .orElseThrow(() -> new EntityNotFoundException("ElectricRecording not found with id: " + electricRecordingId));
        electricRecording.setRecordingDate(null);
        electricRecording.setOldIndex(null);
        electricRecording.setNewIndex(null);
        electricRecordingRepository.save(electricRecording);

        return "Xóa phân công thành công";

    }

}
