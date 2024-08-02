package org.example.dienluc.service;

import org.example.dienluc.service.dto.statistical.client.AmountEveryMonthInEveryYearGetDto;
import org.example.dienluc.service.dto.statistical.client.AmountEveryMonthInEveryYearRequestDto;
import org.example.dienluc.service.dto.statistical.client.ElectricityUsedByClientDto;
import org.example.dienluc.service.dto.statistical.consumption.StatisticalComsumptionElectricTypeDto;

import java.util.List;

public interface StatisticalService {
    public List<StatisticalComsumptionElectricTypeDto> getConsumptionElectricTypes();

    public List<ElectricityUsedByClientDto> getElectricityUsedInYearByClientId(Integer clientId, String year);

    public List<AmountEveryMonthInEveryYearGetDto> getAmountEveryMonthInEveryYear(AmountEveryMonthInEveryYearRequestDto amountEveryMonthInEveryYearRequestDto);
}
