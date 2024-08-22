package org.example.dienluc.service.dto.level;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LevelGetDto {
    private String id;
    private String firstLevel;
    private String secondLevel;
}
