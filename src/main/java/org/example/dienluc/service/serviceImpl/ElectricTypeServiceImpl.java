package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.entity.ElectricityPriceId;
import org.example.dienluc.repository.ElectricTypeRepository;
import org.example.dienluc.service.ElectricTypeService;
import org.example.dienluc.service.dto.electricType.ElectricTypeCreateDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ElectricTypeServiceImpl implements ElectricTypeService {
    private final ElectricTypeRepository electricTypeRepository;
    private final ModelMapper modelMapper;

    public ElectricTypeServiceImpl(ElectricTypeRepository electricTypeRepository, ModelMapper modelMapper) {
        this.electricTypeRepository = electricTypeRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<ElectricType> getAllElectricTypes() {
        return electricTypeRepository.findAll();
    }

    @Override
    public ElectricType createElectricType(ElectricTypeCreateDto electricTypeCreateDto) {
        return electricTypeRepository.save(modelMapper.map(electricTypeCreateDto, ElectricType.class));
    }

    @Override
    public String deleteElectricType(Integer electricTypeId) {
        if (electricTypeRepository.existsById(electricTypeId)) {
            electricTypeRepository.deleteById(electricTypeId);
            return "Xóa loại điện thành công";
        } else {
            throw new EntityNotFoundException("ElectricType not found with id: " + electricTypeId);
        }
    }

    @Override
    public ElectricType updateElectricType(Integer electricTypeId, ElectricTypeCreateDto electricTypeCreateDto) {
        ElectricType electricType = electricTypeRepository.findById(electricTypeId)
                .orElseThrow(() -> new EntityNotFoundException("ElectricType not found with id: " + electricTypeId));
        electricType.setName(electricTypeCreateDto.getName());
        return electricTypeRepository.save(electricType);
    }
}
