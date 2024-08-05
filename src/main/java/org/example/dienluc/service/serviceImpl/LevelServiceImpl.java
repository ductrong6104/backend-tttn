package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.Level;
import org.example.dienluc.repository.LevelRepository;
import org.example.dienluc.service.LevelService;
import org.example.dienluc.service.dto.level.LevelCreateDto;
import org.example.dienluc.service.dto.level.LevelGetDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class LevelServiceImpl implements LevelService {
    private final LevelRepository levelRepository;
    private final ModelMapper modelMapper;

    public LevelServiceImpl(LevelRepository levelRepository, ModelMapper modelMapper) {
        this.levelRepository = levelRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<LevelGetDto> getAllLevel() {
        return levelRepository.findAll().stream()
                .map(level -> modelMapper.map(level, LevelGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Level createLevel(LevelCreateDto levelCreateDto) {
        return levelRepository.save(modelMapper.map(levelCreateDto, Level.class));
    }

    @Override
    public String deleteLevelById(Integer levelId) {
        if (levelRepository.existsById(levelId)) {
            levelRepository.deleteById(levelId);
            return "Xóa bậc thành công";
        } else {
            throw new EntityNotFoundException("Level not found with id: " + levelId);
        }
    }

    @Override
    public List<LevelGetDto> getLevelNotUseByElectricType(Integer electricTypeId) {
        return levelRepository.getLevelNotUseByElectricType(electricTypeId).stream()
                .map(level -> modelMapper.map(level, LevelGetDto.class))
                .collect(Collectors.toList());
    }

    @Override
    public Level updateLevel(Integer levelId, LevelCreateDto levelCreateDto) {
        Level level = levelRepository.findById(levelId)
                .orElseThrow(() -> new EntityNotFoundException("Level not found with id: " + levelId));
        level.setFirstLevel(levelCreateDto.getFirstLevel());
        level.setSecondLevel(levelCreateDto.getSecondLevel());
        return levelRepository.save(level);
    }
}
