package org.example.dienluc.service.serviceImpl;

import org.example.dienluc.repository.BillRepository;
import org.example.dienluc.repository.ContractRepository;

import org.example.dienluc.repository.ElectricRecordingRepository;
import org.example.dienluc.service.StatisticalService;
import org.example.dienluc.service.dto.statistical.client.*;
import org.example.dienluc.service.dto.statistical.consumption.StatisticalComsumptionElectricTypeDto;
import org.example.dienluc.util.MapperUtil;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatisticalServiceImpl implements StatisticalService {
    private final ContractRepository contractRepository;
    private final ElectricRecordingRepository electricRecordingRepository;
    private final BillRepository billRepository;

    public StatisticalServiceImpl(ContractRepository contractRepository, ElectricRecordingRepository electricRecordingRepository, BillRepository billRepository) {
        this.contractRepository = contractRepository;
        this.electricRecordingRepository = electricRecordingRepository;
        this.billRepository = billRepository;
    }

    @Override
    public List<StatisticalComsumptionElectricTypeDto> getConsumptionElectricTypes() {
        List<Object[]> result = contractRepository.getConsumptionElectricTypes();
        String[] fields = {"electricTypeId", "consumption"};

        return MapperUtil.mapResults(result, StatisticalComsumptionElectricTypeDto.class, fields);
    }

    @Override
    public List<ElectricityUsedByClientDto> getElectricityUsedInYearByClientId(Integer clientId, String year) {
        List<Object[]> results = electricRecordingRepository.getElectricityUsedInYearByClientId(clientId, year);
        String[] fields = {"month", "electricUsed"};
        return MapperUtil.mapResults(results, ElectricityUsedByClientDto.class, fields);
    }

    @Override
    public List<AmountEveryMonthInEveryYearGetDto> getAmountEveryMonthInEveryYear(AmountEveryMonthInEveryYearRequestDto amountEveryMonthInEveryYearRequestDto) {
        List<Object[]> results = billRepository.getAmountEveryMonthInEveryYear(amountEveryMonthInEveryYearRequestDto.getClientId(), amountEveryMonthInEveryYearRequestDto.getStartYear(), amountEveryMonthInEveryYearRequestDto.getEndYear());
        String[] fields = {"year", "month", "amount"};
        return MapperUtil.mapResults(results, AmountEveryMonthInEveryYearGetDto.class, fields);
    }

    @Override
    public List<ConsumptionFromDateToDateGetDto> getConsumptionFromDateToDate(ConsumptionFromDateToDateDto consumptionFromDateToDateDto) {
        List<Object[]> results = electricRecordingRepository.getConsumptionFromDateToDate(consumptionFromDateToDateDto.getFromDate(), consumptionFromDateToDateDto.getToDate());
        String[] fields = {"dayMonth", "consumption"};

        return MapperUtil.mapResults(results, ConsumptionFromDateToDateGetDto.class, fields);
    }
}
