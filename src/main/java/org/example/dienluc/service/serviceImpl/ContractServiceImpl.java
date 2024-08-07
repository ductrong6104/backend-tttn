package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

import org.example.dienluc.controller.ContractController;
import org.example.dienluc.entity.*;
import org.example.dienluc.repository.*;
import org.example.dienluc.service.ContractService;
import org.example.dienluc.service.ElectricRecordingService;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.contract.*;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingCreateDto;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingRecentDto;
import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.modelmapper.ModelMapper;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorMvcAutoConfiguration;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ContractServiceImpl implements ContractService {
    private final ContractRepository contractRepository;
    private final ClientRepository clientRepository;
    private final PowerMeterRepository powerMeterRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final ElectricRecordingRepository electricRecordingRepository;
    private final ElectricRecordingService electricRecordingService;


    public ContractServiceImpl(ContractRepository contractRepository, ClientRepository clientRepository, PowerMeterRepository powerMeterRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository, ElectricRecordingRepository electricRecordingRepository, ElectricRecordingServiceImpl electricRecordingServiceImpl, ElectricRecordingService electricRecordingService) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
        this.powerMeterRepository = powerMeterRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.electricRecordingRepository = electricRecordingRepository;

        this.electricRecordingService = electricRecordingService;
    }
    @Transactional
    @Override
    public Contract createContract(ContractCreateDto contractCreateDto) {
        String currentDate = DateUtil.formatDateForDatabase(LocalDate.now());
        PowerMeter newPowerMeter = powerMeterRepository.save(PowerMeter.builder()
                .installationDate(currentDate)
                .installationLocation(contractCreateDto.getElectricitySupplyAddress())
                .build());
        Contract contract = modelMapper.map(contractCreateDto, Contract.class);
        contract.setPowerMeter(newPowerMeter);
        System.out.println(contract);
        return contractRepository.save(contract);
    }

    @Override
    public ContractExistDto checkContractExists(Integer clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()){
            Optional<Contract> optionalContract = Optional.ofNullable(contractRepository.findByClientAndEndDate(clientOptional.get(), null));
            if (optionalContract.isPresent()){
                return new ContractExistDto(optionalContract.get().getId(), true, optionalContract.get().getElectricType().getId());
            }
        }
        return ContractExistDto.builder()
                .exists(false)
                .build();
    }

    @Override
    public List<RegistrationFormDto> getAllRegistrationForm() {
        List<RegistrationFormDto> registrationFormDtos = new ArrayList<>();
        contractRepository.findByProcessingEmployeeIsNull().forEach((contract) -> {
            RegistrationFormDto registrationFormDto = modelMapper.map(contract, RegistrationFormDto.class);
            registrationFormDto.setFirstName(contract.getClient().getFirstName());
            registrationFormDto.setLastName(contract.getClient().getLastName());
            registrationFormDto.setElectricitySupplyAddress(contract.getPowerMeter().getIdAndInstallationLocation());
            registrationFormDtos.add(registrationFormDto);
        });
        return registrationFormDtos;
    }

    @Override
    public Contract updatePowerMeterOfContract(Integer contractId, Integer powerMeterId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));

        PowerMeter powerMeter = powerMeterRepository.findById(powerMeterId)
                .orElseThrow(() -> new EntityNotFoundException("PowerMeter not found with id: " + powerMeterId));
        contract.setPowerMeter(powerMeter);
        return contractRepository.save(contract);
    }

    @Override
    public Contract updateProcessingEmployeeOfContract(Integer contractId, Integer employeeId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));

        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        contract.setProcessingEmployee(employee);
        contract.setContractStatus(ContractStatus.builder().id(5).build());
        return contractRepository.save(contract);
    }

    @Override
    public List<ContractGetDto> getAllContract() {
        return contractRepository.findByProcessingEmployeeIsNotNull().stream()
                .map(contract -> {
                    ContractGetDto contractGetDto = modelMapper.map(contract, ContractGetDto.class);
                    contractGetDto.setFirstName(contract.getClient().getFirstName());
                    contractGetDto.setLastName(contract.getClient().getLastName());
                    contractGetDto.setElectricitySupplyAddress(contract.getPowerMeter().getIdAndInstallationLocation());
                    contractGetDto.setProcessingEmployeeIdAndName(contract.getProcessingEmployee().getIdAndFullName());
                    return contractGetDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public Contract terminateContract(Integer contractId) {
        List<Object[]> results = electricRecordingRepository.getElectricRecordingRecentByContract(contractId);
        String[] fields = {"employeeId", "powerMeterId"};
        List<ElectricRecordingRecentDto> electricRecordingRecentDtos = MapperUtil.mapResults(results, ElectricRecordingRecentDto.class, fields);

        // ==null: nghĩa là hợp đồng này đã ghi điện trong ngày hiện tại nên cho phép kết thúc hợp đồng
        // != null: lấy ra ngày ghi điện gần đây nhất và phân công ghi điện mới dựa theo id nhân viên và id mã đồng hồ của lần ghi điện gần đây

        if (electricRecordingRecentDtos.isEmpty()){
            // id = 3: trạng thái kết thúc hợp đồng
            System.out.println("hihih");
            Contract contract = contractRepository.findById(contractId)
                    .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
            contract.setContractStatus(ContractStatus.builder().id(3).build());
            contract.setEndDate(DateUtil.formatDateForDatabase(LocalDate.now()));
            PowerMeter powerMeter = contract.getPowerMeter();
            powerMeter.setStatus(false);
            powerMeterRepository.save(powerMeter);
            return contractRepository.save(contract);
        }
        else{
            // thuc hien 2 them moi den database nen phai them @transactional
            PowerMeter powerMeter = powerMeterRepository.findById(electricRecordingRecentDtos.get(0).getPowerMeterId())
                    .orElseThrow(() -> new EntityNotFoundException("PowerMeter not found with id: " + electricRecordingRecentDtos.get(0).getPowerMeterId()));
            Optional<ElectricRecording> electricRecording = electricRecordingRepository.findByPowerMeterAndRecordingDateIsNull(powerMeter);

            if (electricRecording.isEmpty()){
                electricRecordingService.createElectricRecording(
                        ElectricRecordingCreateDto.builder()
                                .employeeId(electricRecordingRecentDtos.get(0).getEmployeeId())
                                .powerMeterId(electricRecordingRecentDtos.get(0).getPowerMeterId())
                                .build());
                // // id = 2: trạng thái chờ kết thúc hợp đồng
                Contract contract = contractRepository.findById(contractId)
                        .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
                contract.setContractStatus(ContractStatus.builder().id(2).build());
                System.out.println("contract Status: " + contract);
                System.out.println(contractRepository.save(contract));
            }
            throw new EntityNotFoundException("Phải ghi điện cho hợp đồng này trước khi kết thúc");


        }
    }

    @Override
    public ContractStatusGetDto getContractStatusByClientId(Integer clientId) {
//        Contract contract = contractRepository.findByClientId(clientId);
//        if (contract != null){
//
//        }
        List<Contract> contract = contractRepository.findByClientId(clientId);
        if (contract != null){

            ContractStatusGetDto contractStatusGetDto = modelMapper.map(contract.get(0), ContractStatusGetDto.class);
            contractStatusGetDto.setContractId(contract.get(0).getId());
            return contractStatusGetDto;
        }
        else
            return null;

    }

    @Override
    public Contract rejectContract(Integer contractId, Integer employeeId) {
        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
        Employee employee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException("Employee not found with id: " + employeeId));
        // Định dạng LocalDate.now() sang String với định dạng yyyy-MM-dd
        PowerMeter powerMeter = contract.getPowerMeter();
        powerMeter.setStatus(false);
        powerMeterRepository.save(powerMeter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDate = LocalDate.now().format(formatter);
        contract.setEndDate(endDate);
        contract.setProcessingEmployee(employee);
        contract.setContractStatus(ContractStatus.builder().id(4).build());
        return contractRepository.save(contract);
    }
    @Transactional
    @Override
    public String cancelRegisterByClientId(Integer clientId) {
        if (clientRepository.existsById(clientId)) {
            System.out.println(clientId);
            Contract contract = contractRepository.findByClientIdAndStatusId(clientId, 1);
            if (contract != null){
                Integer powerMeterId = contract.getPowerMeter().getId();
                contractRepository.deleteById(contract.getId());
                powerMeterRepository.deleteById(contract.getPowerMeter().getId());
                return "Hủy đăng ký thành công";
            }
        } else {
            throw new EntityNotFoundException("Client not found with id: " + clientId);
        }
        return "";
    }
}
