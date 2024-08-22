package org.example.dienluc.service;

import org.example.dienluc.service.dto.statistical.client.*;
import org.example.dienluc.service.dto.statistical.consumption.ConsumptionByYear;
import org.example.dienluc.service.dto.statistical.consumption.ConsumptionByYearGetDto;
import org.example.dienluc.service.dto.statistical.consumption.StatisticalComsumptionElectricTypeDto;
import org.example.dienluc.service.dto.statistical.revenue.RevenueByYearDto;
import org.example.dienluc.service.dto.statistical.revenue.RevenueByYearGetDto;

import java.util.List;

public interface StatisticalService {
    public List<StatisticalComsumptionElectricTypeDto> getConsumptionElectricTypes();

    public List<ElectricityUsedByClientDto> getElectricityUsedInYearByClientId(Integer clientId, String year);

    public List<AmountEveryMonthInEveryYearGetDto> getAmountEveryMonthInEveryYear(AmountEveryMonthInEveryYearRequestDto amountEveryMonthInEveryYearRequestDto);

    public List<ConsumptionFromDateToDateGetDto> getConsumptionFromDateToDate(ConsumptionFromDateToDateDto consumptionFromDateToDateDto);

    public List<ConsumptionByYearGetDto> getConsumptionByYear(ConsumptionByYear consumptionByYear);

    public List<RevenueByYearGetDto> getRevenueByYear(RevenueByYearDto revenueByYearDto);
}
