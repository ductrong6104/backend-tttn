package org.example.dienluc.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.dienluc.mapper.Assignment;
import org.example.dienluc.mapper.AssignmentDetail;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.PowerMeterService;
import org.example.dienluc.service.dto.powerMeter.PowerMeterCreateDto;
import org.example.dienluc.service.dto.powerMeter.UpdateCoordinatesDto;
import org.example.dienluc.service.dto.powerMeter.UpdateStatusRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/power-meters")
@CrossOrigin(origins = "http://localhost:3000")
public class PowerMeterController {
    private final PowerMeterService powerMeterService;

    public PowerMeterController(PowerMeterService powerMeterService) {
        this.powerMeterService = powerMeterService;
    }
    @GetMapping("")
    public ResponseEntity<?> getAllPowerMeter() {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getAllPowerMeter())
                .message("get power all meter")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/available")
    public ResponseEntity<?> getAvailablePowerMeters() {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getAvailablePowerMeters())
                .message("get power meter available")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("/{powerMeterId}/status")
    public ResponseEntity<?> updateStatusPowerMeter(@PathVariable Integer powerMeterId,@RequestBody UpdateStatusRequest updateStatusRequest) {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.updateStatusPowerMeter(powerMeterId, updateStatusRequest.getStatus()))
                .message("update status power meter")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createNewPowerMeter(@RequestBody PowerMeterCreateDto powerMeterCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.createNewPowerMeter(powerMeterCreateDto))
                .message("create new power meter")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/recordable")
    public ResponseEntity<?> getRecordablePowerMeters() {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getRecordablePowerMeters())
                .message("get power meter recordable")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{powerMeterId}/coordinates")
    public ResponseEntity<?> updateCoordinates(@PathVariable Integer powerMeterId,@RequestBody UpdateCoordinatesDto updateCoordinatesDto) {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.updateCoordinates(powerMeterId, updateCoordinatesDto))
                .message("update coordinates power meter")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/automation-assignment")
    public ResponseEntity<?> getAutomationAssignment() {
        // Chuyển đổi chuỗi thành Map
        String input = powerMeterService.getAutomationAssignment();
        input = input.replace("'", "\"") // Thay dấu nháy đơn thành dấu nháy kép
                .replace("np.float64(", "") // Loại bỏ ký hiệu np.float64
                .replace("np.int64(", "")
                .replace(")", ""); // Loại bỏ dấu đóng ngoặc
        ObjectMapper objectMapper = new ObjectMapper();
        List<Assignment> assignments = new ArrayList<>();
        try {
            // Parse JSON thành JsonNode
            JsonNode rootNode = objectMapper.readTree(input);

            // Lặp qua từng key trong JSON
            rootNode.fields().forEachRemaining(entry -> {
                String employeeId = entry.getKey();
                JsonNode detailsNode = entry.getValue();

                // Kiểm tra nếu là mảng rỗng
                List<AssignmentDetail> details = new ArrayList<>();
                if (detailsNode.isArray()) {
                    for (JsonNode detailNode : detailsNode) {
                        // Chuyển từng phần tử trong mảng thành AssignmentDetail
                        AssignmentDetail detail = objectMapper.convertValue(detailNode, AssignmentDetail.class);
                        details.add(detail);
                    }
                }

                // Thêm Assignment vào danh sách
                assignments.add(new Assignment(employeeId, details));
            });

        } catch (Exception e) {
            e.printStackTrace();
        }

        ResponseData responseData = ResponseData.builder()
                .data(assignments)
                .message("get automation assignment")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/infor-powerMeters")
    public ResponseEntity<?> getInforPowerMeters(@RequestBody List<Integer> powerMeterIds){
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getInforPowerMeters(powerMeterIds))
                .message("get automation assignment")
                .status(HttpStatus.OK.value())
                .build();

        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/contract/{contractId}")
    public ResponseEntity<?> getPowerMeterByContract(@PathVariable Integer contractId) {
        ResponseData responseData = ResponseData.builder()
                .data(powerMeterService.getPowerMeterByContract(contractId))
                .message("get power meter by contractId")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
