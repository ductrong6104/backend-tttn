package org.example.dienluc.controller;

import org.example.dienluc.entity.Graph;
import org.example.dienluc.entity.Location;
import org.example.dienluc.mapper.LocationDistance;
import org.example.dienluc.payload.ResponseData;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.electricRecording.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Comparator;
import java.util.List;

@RestController
@RequestMapping("/electric-recordings")
public class ElectricRecordingController {
    private final ElectricRecordingService electricRecordingService;

    public ElectricRecordingController(ElectricRecordingService electricRecordingService) {
        this.electricRecordingService = electricRecordingService;
    }

    @GetMapping("")
    public ResponseEntity<?> getAllElectricRecordings() {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getAllElectricRecordings())
                .message("get all electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("/assigned")
    public ResponseEntity<?> getAssignedElectricRecordings() {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getAssignedElectricRecordings())
                .message("get all assigned electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/assigned/{employeeId}")
    public ResponseEntity<?> getAssignedElectricRecordingsByEmployeeId(@PathVariable Integer employeeId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getAssignedElectricRecordingsByEmployeeId(employeeId))
                .message("get all assigned electric recording by employee id")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("")
    public ResponseEntity<?> createElectricRecording(@RequestBody ElectricRecordingCreateDto electricRecordingCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.createElectricRecording(electricRecordingCreateDto))
                .message("create new electric recording")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PutMapping("")
    public ResponseEntity<?> updateElectricRecordingFirst(@RequestBody ElectricRecordingCreateDto electricRecordingCreateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.updateElectricRecordingFirst(electricRecordingCreateDto))
                .message("update first electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/{electricRecordingId}")
    public ResponseEntity<?> updateElectricRecording(@PathVariable Integer electricRecordingId, @RequestBody ElectricRecordingUpdateDto electricRecordingUpdateDto) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.updateElectricRecording(electricRecordingId, electricRecordingUpdateDto))
                .message("update employee or power meter electric recording")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @DeleteMapping("/{electricRecordingId}")
    public ResponseEntity<?> deleteElectricRecording(@PathVariable Integer electricRecordingId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.deleteElectricRecording(electricRecordingId))
                .message("delete electric recording")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @DeleteMapping("/employee/{electricRecordingId}")
    public ResponseEntity<?> deleteRecordingByEmployee(@PathVariable Integer electricRecordingId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.deleteRecordingByEmployee(electricRecordingId))
                .message("delete electric recording")
                .status(HttpStatus.NO_CONTENT.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PutMapping("/employee/{electricRecordingId}")
    public ResponseEntity<?> updateElectricRecordingByEmployee(@PathVariable Integer electricRecordingId, @RequestBody ElectricRecordingUpdateByEmployeeDto electricRecordingUpdateByEmployeeDto) {
//        System.out.println("electricRecordingId"  + electricRecordingId);
//        System.out.println("electricRecordingUpdateByEmployeeDto"  + electricRecordingUpdateByEmployeeDto);
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.updateElectricRecordingByEmployee(electricRecordingId, electricRecordingUpdateByEmployeeDto))
                .message("update electric recording by employee")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @PostMapping("/assigned/{employeeId}/shortest")
    public ResponseEntity<?> getShortestPath(@PathVariable Integer employeeId, @RequestBody Location locationCurrent) {
        List<ElectricRecordingAssignedByEmployeeDto> electricRecordingAssignedByEmployeeDtoList = electricRecordingService.getAssignedElectricRecordingsByEmployeeId(employeeId);
        Graph graph = new Graph();
        // Duyệt qua từng DTO và thêm các địa điểm vào đồ thị
        electricRecordingAssignedByEmployeeDtoList.forEach(dto ->
                graph.addLocation(dto.getPowerMeterId(), dto.getLatitude(), dto.getLongitude())
        );
//        graph.addLocation(12322, 10.846288060343714, 106.78625800058298);
//        electricRecordingAssignedByEmployeeDtoList.add(ElectricRecordingAssignedByEmployeeDto
//                .builder()
//                .powerMeterId(12322)
//                .build());
        // Thêm các cạnh giữa tất cả các địa điểm
//        for (int i = 0; i < electricRecordingAssignedByEmployeeDtoList.size() - 1; i++) {
//            for (int j = i + 1; j < electricRecordingAssignedByEmployeeDtoList.size(); j++) {
//                int fromId = electricRecordingAssignedByEmployeeDtoList.get(i).getPowerMeterId();
//                int toId = electricRecordingAssignedByEmployeeDtoList.get(j).getPowerMeterId();
//
//                graph.addEdge(fromId, toId);
//            }
//        }
        // Tìm đường đi ngắn nhất từ HCM (1) đến Da Lat (4)
//        List<Integer> path = graph.dijkstra(electricRecordingAssignedByEmployeeDtoList.get(4).getPowerMeterId(), electricRecordingAssignedByEmployeeDtoList.get(1).getPowerMeterId());
        // Tính khoảng cách từ vị trí hiện tại đến từng đồng hồ và lưu vào danh sách
        List<LocationDistance> distances = electricRecordingAssignedByEmployeeDtoList.stream()
                .map(dto -> {
                    double distance = graph.haversine(
                            locationCurrent,
                            new Location(dto.getLatitude(), dto.getLongitude())
                    );
                    return new LocationDistance(dto.getPowerMeterId(), distance);
                })
                .sorted(Comparator.comparingDouble(LocationDistance::getDistance)) // Sắp xếp theo khoảng cách tăng dần
                .toList();

        // Tạo phản hồi
        ResponseData responseData = ResponseData.builder()
                .data(distances)
                .message("Sorted locations by distance")
                .status(HttpStatus.OK.value())
                .build();
//        System.out.println("Đường đi ngắn nhất: " + path);
//        ResponseData responseData = ResponseData.builder()
//                .data(path)
//                .message("get shortest path")
//                .status(HttpStatus.OK.value())
//                .build();
        return ResponseEntity.ok(responseData);
    }

    @GetMapping("/recording-history/{employeeId}")
    public ResponseEntity<?> getRecordingHistoryByEmployee(@PathVariable Integer employeeId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getRecordingHistoryByEmployee(employeeId))
                .message("get recording history by employee")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/automation-assignment")
    public ResponseEntity<?> createAutomationAssignment(@RequestBody List<ElectricRecordingAutoAssign> electricRecordingAutoAssign) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.automationAssignment(electricRecordingAutoAssign))
                .message("create electric recording automation assignment")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }

    @PostMapping("/automation-assignment-one-employee")
    public ResponseEntity<?> createAutomationAssignmentOneEmployee(@RequestBody ElectricRecordingAutoAssign electricRecordingAutoAssign) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.createAutomationAssignmentOneEmployee(electricRecordingAutoAssign))
                .message("create electric recording automation assignment one employee")
                .status(HttpStatus.CREATED.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
    @GetMapping("recording-history/powerMeter/{powerMeterId}")
    public ResponseEntity<?> getRecordingHistoryByPowerMeter(@PathVariable Integer powerMeterId) {
        ResponseData responseData = ResponseData.builder()
                .data(electricRecordingService.getRecordingHistoryByPowerMeter(powerMeterId))
                .message("get recording history by powerMeter")
                .status(HttpStatus.OK.value())
                .build();
        return ResponseEntity.ok(responseData);
    }
}
