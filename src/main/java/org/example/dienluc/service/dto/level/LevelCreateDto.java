package org.example.dienluc.service.dto.level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelCreateDto {
    private Integer firstLevel;
    private Integer secondLevel;
}
