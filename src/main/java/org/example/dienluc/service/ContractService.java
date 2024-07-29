package org.example.dienluc.service;

import org.example.dienluc.entity.Contract;
import org.example.dienluc.service.dto.ResponseCheck;
import org.example.dienluc.service.dto.contract.ContractCreateDto;
import org.example.dienluc.service.dto.contract.ContractGetDto;
import org.example.dienluc.service.dto.contract.RegistrationFormDto;

import java.util.List;

public interface ContractService {
    public Contract createContract(ContractCreateDto contractCreateDto);

    public ResponseCheck checkContractExists(Integer clientId);

    public List<RegistrationFormDto> getAllRegistrationForm();

    public Contract updatePowerMeterOfContract(Integer contractId, Integer powerMeterId);
    public Contract updateProcessingEmployeeOfContract(Integer contractId, Integer employeeId);

    public List<ContractGetDto> getAllContract();

    public Contract terminateContract(Integer contractId);
}
