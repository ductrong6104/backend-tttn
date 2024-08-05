package org.example.dienluc.service;

import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.service.dto.electricType.ElectricTypeCreateDto;

import java.util.List;

public interface ElectricTypeService {
    public List<ElectricType> getAllElectricTypes();

    public ElectricType createElectricType(ElectricTypeCreateDto electricTypeCreateDto);

    public String  deleteElectricType(Integer electricTypeId);

    public ElectricType updateElectricType(Integer electricTypeId, ElectricTypeCreateDto electricTypeCreateDto);
}
