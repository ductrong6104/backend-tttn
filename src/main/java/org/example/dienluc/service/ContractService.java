package org.example.dienluc.service;

import org.example.dienluc.entity.Contract;
import org.example.dienluc.service.dto.client.ClientUpdateReasonRejectDto;
import org.example.dienluc.service.dto.contract.*;

import java.util.List;

public interface ContractService {
    public Contract createContract(ContractCreateDto contractCreateDto);

    public ContractExistDto checkContractExists(Integer clientId);

    public List<RegistrationFormDto> getAllRegistrationForm();

    public Contract updatePowerMeterOfContract(Integer contractId, Integer powerMeterId);
    public Contract updateProcessingEmployeeOfContract(Integer contractId, Integer employeeId);

    public List<ContractGetDto> getAllContract();

    public ContractTerminateGetDto terminateContract(Integer contractId);


    public ContractStatusGetDto getContractStatusByClientId(Integer clientId);

    public Contract rejectContract(Integer contractId, Integer employeeId, ContractReasonRejectDto contractReasonRejectDto);

    public String cancelRegisterByClientId(Integer clientId);

    public Contract updateInforForReject(Integer contractId, Integer clientId, ClientUpdateReasonRejectDto clientUpdateReasonRejectDto);

    public List<RegistrationFormGetDto> getRegistrationFormByClientId(Integer clientId);
}
