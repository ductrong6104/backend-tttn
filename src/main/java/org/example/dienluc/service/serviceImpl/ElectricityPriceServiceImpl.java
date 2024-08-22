package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;

import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.entity.ElectricityPrice;
import org.example.dienluc.entity.Level;
import org.example.dienluc.repository.ElectricTypeRepository;
import org.example.dienluc.repository.ElectricityPriceRepository;
import org.example.dienluc.repository.LevelRepository;
import org.example.dienluc.service.ElectricityPriceService;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceCreateDto;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceDeleteDto;
import org.example.dienluc.service.dto.electricityPrice.ElectricityPriceGetDto;
import org.hibernate.validator.internal.constraintvalidators.bv.time.futureorpresent.FutureOrPresentValidatorForMonthDay;
import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectricityPriceServiceImpl implements ElectricityPriceService {
    private final ElectricityPriceRepository electricityPriceRepository;
    private final ModelMapper modelMapper;
    private final ElectricTypeRepository electricTypeRepository;
    private final LevelRepository levelRepository;

    public ElectricityPriceServiceImpl(ElectricityPriceRepository electricityPriceRepository, ModelMapper modelMapper, ElectricTypeRepository electricTypeRepository, LevelRepository levelRepository) {
        this.electricityPriceRepository = electricityPriceRepository;
        this.modelMapper = modelMapper;
        this.electricTypeRepository = electricTypeRepository;
        this.levelRepository = levelRepository;
    }

    @Override
    public List<ElectricityPriceGetDto> getAllElectricityPrices() {
        return electricityPriceRepository.findAll().stream()
                .map(electricityPrice ->
                 modelMapper.map(electricityPrice, ElectricityPriceGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public ElectricityPrice createElectricityPrice(ElectricityPriceCreateDto electricityPriceCreateDto) {
        ElectricityPrice electricityPrice = electricityPriceRepository.findByElectricTypeIdAndLevelId(electricityPriceCreateDto.getElectricTypeId(), electricityPriceCreateDto.getLevelId());
        if (electricityPrice != null)
            throw new DataIntegrityViolationException("Electricity Price already exists");
        return electricityPriceRepository.save(modelMapper.map(electricityPriceCreateDto, ElectricityPrice.class));
    }
    @Transactional
    @Override
    public String deleteElectricityPrice(ElectricityPriceDeleteDto electricityPriceDeleteDto) {
        ElectricType electricType = electricTypeRepository.findById(electricityPriceDeleteDto.getElectricTypeId())
                        .orElseThrow(() -> new EntityNotFoundException("ElectricType not found with id: " + electricityPriceDeleteDto.getElectricTypeId()));
        Level level = levelRepository.findById(electricityPriceDeleteDto.getLevelId())
                        .orElseThrow(() -> new EntityNotFoundException("Level not found with id: " + electricityPriceDeleteDto.getLevelId()));
        int deletedCount = electricityPriceRepository.deleteByElectricTypeAndLevel(electricType, level);
        // deltedCount: số bản ghi được xóa
        if (deletedCount > 0) {
            return "Xóa giá điện thành công";
        } else {
            throw new EntityNotFoundException("No ElectricityPrice found for ElectricType id: " + electricityPriceDeleteDto.getElectricTypeId() + " and Level id: " + electricityPriceDeleteDto.getLevelId());
        }
    }
}
