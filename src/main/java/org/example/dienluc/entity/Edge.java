package org.example.dienluc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
// Lớp biểu diễn cạnh giữa hai địa điểm với trọng số là khoảng cách
public class Edge {
    int destination;
    double weight;
    public Edge(int destination, double weight) {
        this.destination = destination;
        this.weight = weight;
    }
}