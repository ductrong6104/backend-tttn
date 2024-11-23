package org.example.dienluc.mapper;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LocationDistance {
    private int powerMeterId;  // ID của đồng hồ điện
    private double distance;   // Khoảng cách từ vị trí hiện tại (km)
}

