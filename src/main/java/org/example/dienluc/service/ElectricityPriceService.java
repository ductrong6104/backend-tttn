package org.example.dienluc.service;

import org.example.dienluc.entity.ElectricityPrice;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceCreateDto;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceDeleteDto;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceGetDto;

import java.util.List;

public interface ElectricityPriceService {
    public List<ElectricityPriceGetDto> getAllElectricityPrices();

    public ElectricityPrice createElectricityPrice(ElectricityPriceCreateDto electricityPriceCreateDto);

    public String deleteElectricityPrice(Integer electricTypeId, Integer levelId);

}
