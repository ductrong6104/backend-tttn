package org.example.dienluc.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.*;

@Data
@Builder
// Lớp biểu diễn đồ thị
public class Graph {
    private final Map<Integer, Location> locations;
    private final Map<Integer, List<Edge>> adjList;

    public Graph() {
        this.locations = new HashMap<>();
        this.adjList = new HashMap<>();
    }
    // Constructor có tham số
    public Graph(Map<Integer, Location> locations, Map<Integer, List<Edge>> adjList) {
        this.locations = locations;
        this.adjList = adjList;
    }

    // Thêm địa điểm với tọa độ vào đồ thị
    public void addLocation(int id, double latitude, double longitude) {
        locations.put(id, new Location(latitude, longitude));
        adjList.putIfAbsent(id, new ArrayList<>());
    }

    // Tính khoảng cách giữa hai địa điểm bằng Haversine
    public double haversine(Location loc1, Location loc2) {
        final double R = 6371; // Bán kính Trái Đất (km)
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        double a = Math.pow(Math.sin(dLat / 2), 2) +
                Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dLon / 2), 2);
        double c = 2 * Math.asin(Math.sqrt(a));
        return R * c; // Khoảng cách tính bằng km
    }

    // Thêm cạnh giữa hai địa điểm
    public void addEdge(int from, int to) {
        Location loc1 = locations.get(from);
        Location loc2 = locations.get(to);
        double distance = haversine(loc1, loc2);

        adjList.get(from).add(new Edge(to, distance));
        adjList.get(to).add(new Edge(from, distance)); // Đồ thị vô hướng
    }

    // Thuật toán Dijkstra tìm đường đi ngắn nhất
    public List<Integer> dijkstra(int start, int end) {
        Map<Integer, Double> distances = new HashMap<>();
        Map<Integer, Integer> previous = new HashMap<>();
        PriorityQueue<int[]> pq = new PriorityQueue<>(Comparator.comparingDouble(a -> a[1]));

        // Khởi tạo khoảng cách ban đầu là vô cực
        for (int id : locations.keySet()) {
            distances.put(id, Double.MAX_VALUE);
        }
        distances.put(start, 0.0);
        pq.add(new int[]{start, 0});

        while (!pq.isEmpty()) {
            int[] current = pq.poll();
            int node = current[0];
            double currentDistance = current[1];

            if (node == end) break; // Tìm thấy đích, thoát vòng lặp

            for (Edge edge : adjList.get(node)) {
                double newDist = currentDistance + edge.weight;

                if (newDist < distances.get(edge.destination)) {
                    distances.put(edge.destination, newDist);
                    previous.put(edge.destination, node);
                    pq.add(new int[]{edge.destination, (int) newDist});
                }
            }
        }

        // Truy vết đường đi từ đích về nguồn
        List<Integer> path = new ArrayList<>();
        for (Integer at = end; at != null; at = previous.get(at)) {
            path.add(at);
        }
        Collections.reverse(path);
        return path;
    }
}