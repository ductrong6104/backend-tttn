package org.example.dienluc.mapper;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AssignmentDetail {
    private Integer id;
    private String installationLocation;
    private Float longitude;
    private Float latitude;
}
