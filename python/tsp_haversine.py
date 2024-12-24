import numpy as np
from ortools.constraint_solver import routing_enums_pb2
from ortools.constraint_solver import pywrapcp



from cluster_haversine import calculate_wcss_haversine, haversine


def solve_tsp_with_distance_matrix(distance_matrix):
    """Giải bài toán TSP cho một cụm."""
    manager = pywrapcp.RoutingIndexManager(len(distance_matrix), 1, 0)
    routing = pywrapcp.RoutingModel(manager)

    # Callback để tính chi phí giữa các điểm
    def distance_callback(from_index, to_index):
        return distance_matrix[from_index][to_index]

    transit_callback_index = routing.RegisterTransitCallback(distance_callback)
    routing.SetArcCostEvaluatorOfAllVehicles(transit_callback_index)

    # Thiết lập tham số tìm kiếm
    search_parameters = pywrapcp.DefaultRoutingSearchParameters()
    search_parameters.first_solution_strategy = routing_enums_pb2.FirstSolutionStrategy.PATH_CHEAPEST_ARC

    # Giải thuật toán
    solution = routing.SolveWithParameters(search_parameters)
    if not solution:
        return None  # Không tìm thấy giải pháp

    # Lấy ra tuyến đường tối ưu
    route = []
    index = routing.Start(0)
    while not routing.IsEnd(index):
        route.append(manager.IndexToNode(index))
        index = solution.Value(routing.NextVar(index))
    route.append(manager.IndexToNode(index))  # Quay lại điểm bắt đầu
    return route


def tsp_haversine(dong_ho_coords, nhan_vien_coords, n_clusters, max_iterations=100):
    # Bước 1: Khởi tạo trung tâm cụm bằng tọa độ của nhân viên
    centroids = nhan_vien_coords.copy()
    labels = np.zeros(dong_ho_coords.shape[0])
    wcss_values = []
    optimal_routes = []  # Lưu tuyến đường tối ưu của từng cụm
    total_distances = []  # Lưu tổng quãng đường của từng cụm

    for _ in range(max_iterations):
        # Bước 2: Gán đồng hồ vào cụm gần nhất (dựa trên khoảng cách Haversine)
        for i, dong_ho in enumerate(dong_ho_coords):
            distances = [haversine(dong_ho, centroid) for centroid in centroids]
            labels[i] = np.argmin(distances)

        # Tính WCSS sau mỗi lần cập nhật
        wcss = calculate_wcss_haversine(dong_ho_coords, centroids, labels)
        wcss_values.append(wcss)

        # Bước 3: Cập nhật vị trí trung tâm cụm dựa trên tuyến đường TSP
        new_centroids = []
        optimal_routes.clear()  # Reset danh sách tuyến đường
        total_distances.clear()  # Reset danh sách quãng đường

        for cluster_index in range(n_clusters):
            points_in_cluster = dong_ho_coords[labels == cluster_index]
            if points_in_cluster.size > 0:
                # Tính ma trận khoảng cách giữa các điểm trong cụm
                distance_matrix = [[haversine(p1, p2) for p2 in points_in_cluster] for p1 in points_in_cluster]

                # Giải bài toán TSP để tìm tuyến đường tối ưu trong cụm
                tsp_route = solve_tsp_with_distance_matrix(distance_matrix)

                # Tính tổng quãng đường cho tuyến TSP
                total_distance = sum(
                    haversine(points_in_cluster[tsp_route[i]], points_in_cluster[tsp_route[i + 1]])
                    for i in range(len(tsp_route) - 1)
                )

                # Lưu tuyến đường và tổng quãng đường
                optimal_routes.append([points_in_cluster[i] for i in tsp_route])
                total_distances.append(total_distance)

                # Chọn điểm đầu tiên trong tuyến TSP làm trung tâm mới
                new_centroids.append(points_in_cluster[tsp_route[0]])
            else:
                new_centroids.append(centroids[cluster_index])  # Giữ nguyên nếu cụm không có điểm

        centroids = np.array(new_centroids)

    return labels, centroids, wcss_values, optimal_routes, total_distances
