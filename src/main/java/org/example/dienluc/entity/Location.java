package org.example.dienluc.entity;

import lombok.Builder;
import lombok.Data;

// Lớp biểu diễn một điểm trên bản đồ với kinh độ và vĩ độ
@Data
@Builder
public class Location {
    double latitude;
    double longitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}