package org.example.dienluc.service.serviceImpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityNotFoundException;

import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.repository.PowerMeterRepository;
import org.example.dienluc.service.PowerMeterService;
import org.example.dienluc.service.dto.powerMeter.*;

import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Date;
import java.util.List;
import java.util.Map;
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
    public List<PowerMeterGetDto> getAllPowerMeter() {
        return powerMeterRepository.findAll().stream()
                .map(powerMeter -> modelMapper.map(powerMeter, PowerMeterGetDto.class))
                .collect(Collectors.toList());
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
        String[] fields = {"id", "installationLocation"};
        return MapperUtil.mapResults(results, PowerMeterRecordableDto.class, fields);
    }

    @Override
    public PowerMeter updateCoordinates(Integer powerMeterId, UpdateCoordinatesDto updateCoordinatesDto) {
        PowerMeter powerMeter = powerMeterRepository.findById(powerMeterId)
                .orElseThrow(() -> new EntityNotFoundException("Power meter not found with id: " + powerMeterId));
        powerMeter.setLongitude(updateCoordinatesDto.getLongitude());
        powerMeter.setLatitude(updateCoordinatesDto.getLatitude());
        powerMeter.setInstallationLocation(updateCoordinatesDto.getInstallationLocation());
        return powerMeterRepository.save(powerMeter);
    }

    @Override
    public String getAutomationAssignment() {

        StringBuilder output = new StringBuilder();
        StringBuilder errorOutput = new StringBuilder(); // Dùng để lưu lỗi
        try {
            String currentDirectory = System.getProperty("user.dir");
            String pythonCommand = currentDirectory + "/python/venv/Scripts/python"; // Đường dẫn đến Python interpreter trong môi trường ảo
            String pythonScript = currentDirectory + "/python/read_data.py"; // Đường dẫn đến script Python

            // Tạo một ProcessBuilder với lệnh Python và tên tệp Python
            ProcessBuilder processBuilder = new ProcessBuilder(pythonCommand, pythonScript);

            // Bắt đầu thực thi lệnh
            Process process = processBuilder.start();
            // Chạy lệnh Python bằng Runtime
//            Process process = Runtime.getRuntime().exec("python src/main/python/read_data.py");

            // Đọc output từ file Python
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");  // Ghi dữ liệu vào StringBuilder
            }

            // Đọc lỗi nếu có
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String errorLine;
            while ((errorLine = errorReader.readLine()) != null) {
                errorOutput.append(errorLine).append("\n");  // Ghi lỗi vào StringBuilder
            }

            System.out.println(output);

            // Đợi quá trình kết thúc và kiểm tra mã thoát
            int exitCode = process.waitFor();
            System.out.println("Exited with code: " + exitCode);

            // Nếu có lỗi, in ra lỗi
            if (exitCode != 0) {
                System.err.println("Errors occurred while executing the script:");
                System.err.println(errorOutput.toString());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return output.toString(); // Trả về dữ liệu thu được
    }

    @Override
    public List<PowerMeterInforByIds> getInforPowerMeters(List<Integer> powerMeterIds) {
        List<Object[]> results = powerMeterRepository.getInforPowerMeters(powerMeterIds);
        String[] fields = {"id", "installationDate","installationLocation", "longitude", "latitude"};
        return MapperUtil.mapResults(results, PowerMeterInforByIds.class, fields);

    }

    @Override
    public PowerMeter getPowerMeterByContract(Integer contractId) {
        return powerMeterRepository.getPowerMeterByContract(contractId);
    }


}
