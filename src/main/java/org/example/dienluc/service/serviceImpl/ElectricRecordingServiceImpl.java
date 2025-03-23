package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.*;
import org.example.dienluc.repository.ClientRepository;
import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.repository.PowerMeterRepository;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.electricRecording.*;
import org.example.dienluc.service.dto.powerMeter.ElectricRecordingHistoryByPowerMeterDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;
import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectricRecordingServiceImpl implements ElectricRecordingService {
    private final ElectricRecordingRepository electricRecordingRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final PowerMeterRepository powerMeterRepository;
    private final ClientRepository clientRepository;
    @Autowired
    private final EmailServiceImpl emailService;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    public ElectricRecordingServiceImpl(ElectricRecordingRepository electricRecordingRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository, PowerMeterRepository powerMeterRepository, ClientRepository clientRepository, EmailServiceImpl emailService) {
        this.electricRecordingRepository = electricRecordingRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.powerMeterRepository = powerMeterRepository;
        this.clientRepository = clientRepository;
        this.emailService = emailService;
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
                    electricRecordingAssignedDto.setLongitude(electricRecording.getPowerMeter().getLongitude());
                    electricRecordingAssignedDto.setLatitude(electricRecording.getPowerMeter().getLatitude());
                    return electricRecordingAssignedDto;
                })
                .collect(Collectors.toList());
    }
    @Transactional
    @Override
    public List<ElectricRecordingAssignedByEmployeeDto> getAssignedElectricRecordingsByEmployeeId(Integer employeeId) {
        List<Object[]> results = electricRecordingRepository.getAssignedElectricRecordingsByEmployeeId(employeeId);
        String[] fields = {"powerMeterId","installationLocation", "oldIndex", "longitude", "latitude" };
        return MapperUtil.mapResults(results, ElectricRecordingAssignedByEmployeeDto.class, fields);
    }

    @Override
    public ElectricRecording updateElectricRecordingFirst(ElectricRecordingCreateDto electricRecordingCreateDto) {
//        ElectricRecording electricRecording = electricRecordingRepository.findNotAssignedByPowerMeterIdAndEmployeeIdAndRecordingDate(electricRecordingCreateDto.getPowerMeterId(), electricRecordingCreateDto.getEmployeeId())
//                        .orElseThrow(() -> new EntityNotFoundException("ElectricRecording to update first not found"));
//        electricRecording.setOldIndex(electricRecordingCreateDto.getOldIndex());
//        electricRecording.setNewIndex(electricRecordingCreateDto.getNewIndex());
//        electricRecording.setRecordingDate(electricRecordingCreateDto.getRecordingDate());

        return electricRecordingRepository.save(modelMapper.map(electricRecordingCreateDto, ElectricRecording.class));


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
//        electricRecording.setRecordingDate(null);
//        electricRecording.setOldIndex(null);
//        electricRecording.setNewIndex(null);
        electricRecordingRepository.deleteById(electricRecordingId);

        return "Xóa phân công thành công";

    }

    @Override
    public List<ElectricRecordingHistoryByEmployeeDto> getRecordingHistoryByEmployee(Integer employeeId) {
        List<Object[]> results = electricRecordingRepository.findByEmployeeIdOrderByRecordingDate(employeeId);
        String[] fields = {"id", "powerMeterId", "recordingDate", "oldIndex", "newIndex"};
        return MapperUtil.mapResults(results, ElectricRecordingHistoryByEmployeeDto.class, fields);
    }

    @Override
    public String automationAssignment(List<ElectricRecordingAutoAssign> electricRecordingAutoAssign) {
        List<Object[]> batchArgs = new ArrayList<>();

        // Lặp qua danh sách dữ liệu
        for (ElectricRecordingAutoAssign item : electricRecordingAutoAssign) {
            Integer employeeId = item.getEmployeeId();
            for (Integer powerMeterId : item.getPowerMeterIds()) {
                // Thêm dữ liệu vào batchArgs dưới dạng mảng Object
                batchArgs.add(new Object[] { employeeId, powerMeterId });
            }
        }

        // Câu lệnh SQL để chèn dữ liệu vào bảng EmployeePowerMeters
        String sql = "INSERT INTO PHANCONG (IDNHANVIEN, IDDONGHODIEN) VALUES (?, ?)";

        // Thực hiện chèn hàng loạt
        jdbcTemplate.batchUpdate(sql, batchArgs);
        return "automation success";
    }

    @Override
    public String createAutomationAssignmentOneEmployee(ElectricRecordingAutoAssign electricRecordingAutoAssign) {
        List<Object[]> batchArgs = new ArrayList<>();

        Integer employeeId = electricRecordingAutoAssign.getEmployeeId();
        for (Integer powerMeterId : electricRecordingAutoAssign.getPowerMeterIds()) {
            // Thêm dữ liệu vào batchArgs dưới dạng mảng Object
            batchArgs.add(new Object[] { employeeId, powerMeterId});
            System.out.println("employeeId" + employeeId);
            System.out.println("powerMeterId " + powerMeterId);
        }


        // Câu lệnh SQL để chèn dữ liệu vào bảng EmployeePowerMeters
        String sql = "INSERT INTO PHANCONG (IDNHANVIEN, IDDONGHODIEN) VALUES (?, ?)";

        // Thực hiện chèn hàng loạt
        try {
            // Thực hiện chèn hàng loạt
            jdbcTemplate.batchUpdate(sql, batchArgs);
            return "Automation success";
        } catch (Exception e) {
            // Ghi log chi tiết lỗi
            StringBuilder errorDetails = new StringBuilder("Error during batch insertion:\n");

            for (Object[] args : batchArgs) {
                errorDetails.append("Failed to insert: IDNHANVIEN = ")
                        .append(args[0])
                        .append(", IDDONGHODIEN = ")
                        .append(args[1])
                        .append("\n");
            }

            errorDetails.append("Exception: ").append(e.getMessage());
            System.err.println(errorDetails); // Log lỗi ra console (có thể thay bằng logger)

            // Ném ngoại lệ với thông tin chi tiết
            throw new RuntimeException(errorDetails.toString(), e);
        }
    }

    @Override
    public List<ElectricRecordingHistoryByPowerMeterDto> getRecordingHistoryByPowerMeter(Integer powerMeterId) {
        List<Object[]> results = electricRecordingRepository.findByPowerMeterIdOrderByRecordingDate(powerMeterId);
        String[] fields = {"id", "powerMeterId", "recordingDate", "oldIndex", "newIndex"};
        return MapperUtil.mapResults(results, ElectricRecordingHistoryByPowerMeterDto.class, fields);
    }

    @Override
    public String notifyReadingDay(Integer powerMeterId) {
        PowerMeter powerMeter = powerMeterRepository.findById(powerMeterId)
                .orElseThrow(() -> new EntityNotFoundException("PowerMeter not found with id: " + powerMeterId));
        String subject = "Thông báo: Nhân viên ghi điện tới vào ngày mai";
        String body = String.format(
                "Kính gửi %s,\n\nChúng tôi xin thông báo rằng nhân viên sẽ tới ghi chỉ số điện của bạn vào ngày mai, %s, tại vị trí: %s.\n\nCảm ơn bạn!",
                "n20dccn079@student.ptithcm.edu.vn",
                DateUtil.formatDateForDatabase(LocalDate.now().plusDays(1)),
                powerMeter.getInstallationLocation()
        );
        emailService.sendEmail("n20dccn079@student.ptithcm.edu.vn", subject, body);
        return "notify reading day client success";
    }

}
