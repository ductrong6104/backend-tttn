package org.example.dienluc.service;

import org.example.dienluc.entity.Level;
import org.example.dienluc.service.dto.level.LevelCreateDto;
import org.example.dienluc.service.dto.level.LevelGetDto;

import java.util.List;

public interface LevelService {
    public List<LevelGetDto> getAllLevel();

    public Level createLevel(LevelCreateDto levelCreateDto);

    public String deleteLevelById(Integer levelId);

    public List<LevelGetDto> getLevelNotUseByElectricType(Integer electricTypeId);
}
