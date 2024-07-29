package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.controller.ContractController;
import org.example.dienluc.entity.Client;
import org.example.dienluc.entity.Contract;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.repository.ClientRepository;
import org.example.dienluc.repository.ContractRepository;
import org.example.dienluc.repository.EmployeeRepository;
import org.example.dienluc.repository.PowerMeterRepository;
import org.example.dienluc.service.ContractService;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.contract.ContractCreateDto;
import org.example.dienluc.service.dto.contract.ContractGetDto;
import org.example.dienluc.service.dto.contract.RegistrationFormDto;
import org.example.dienluc.util.DateUtil;
import org.modelmapper.ModelMapper;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

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


    public ContractServiceImpl(ContractRepository contractRepository, ClientRepository clientRepository, PowerMeterRepository powerMeterRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository) {
        this.contractRepository = contractRepository;
        this.clientRepository = clientRepository;
        this.powerMeterRepository = powerMeterRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Contract createContract(ContractCreateDto contractCreateDto) {
        String currentDate = DateUtil.formatDateForDatabase(LocalDate.now());
        PowerMeter newPowerMeter = powerMeterRepository.save(PowerMeter.builder()
                .installationDate(currentDate)
                .installationLocation(contractCreateDto.getElectricitySupplyAddress())
                .build());
        Contract contract = modelMapper.map(contractCreateDto, Contract.class);
        contract.setPowerMeter(newPowerMeter);
        return contractRepository.save(contract);
    }

    @Override
    public ResponseCheck checkContractExists(Integer clientId) {
        Optional<Client> clientOptional = clientRepository.findById(clientId);
        if (clientOptional.isPresent()){
            Optional<Contract> optionalContract = Optional.ofNullable(contractRepository.findByClientAndEndDate(clientOptional.get(), null));
            if (optionalContract.isPresent()){
                return new ResponseCheck(true);
            }
        }
        return new ResponseCheck(false);
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

        Contract contract = contractRepository.findById(contractId)
                .orElseThrow(() -> new EntityNotFoundException("Contract not found with id: " + contractId));
        // Định dạng LocalDate.now() sang String với định dạng yyyy-MM-dd
        PowerMeter powerMeter = contract.getPowerMeter();
        powerMeter.setStatus(false);
        powerMeterRepository.save(powerMeter);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String endDate = LocalDate.now().format(formatter);
        contract.setEndDate(endDate);
        return contractRepository.save(contract);

    }
}
