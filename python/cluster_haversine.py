from scipy.spatial import distance
import numpy as np
import matplotlib.pyplot as plt
from matplotlib.patches import Circle

def geographic_centroid(coords):
    latitudes = np.radians([coord[0] for coord in coords])
    longitudes = np.radians([coord[1] for coord in coords])

    x = np.mean(np.cos(latitudes) * np.cos(longitudes))
    y = np.mean(np.cos(latitudes) * np.sin(longitudes))
    z = np.mean(np.sin(latitudes))

    longitude = np.arctan2(y, x)
    hyp = np.sqrt(x * x + y * y)
    latitude = np.arctan2(z, hyp)

    return np.degrees(latitude), np.degrees(longitude)

def haversine(coord1, coord2):
    lat1, lon1 = np.radians(coord1)
    lat2, lon2 = np.radians(coord2)

    dlat = lat2 - lat1
    dlon = lon2 - lon1
    a = np.sin(dlat / 2.0) ** 2 + np.cos(lat1) * np.cos(lat2) * np.sin(dlon / 2.0) ** 2
    c = 2 * np.arcsin(np.sqrt(a))

    R = 6371.0  # Bán kính trái đất (km)
    return R * c

def kmeans_haversine(dong_ho_coords, nhan_vien_coords, n_clusters, max_iterations=100):
    # Khởi tạo trung tâm cụm bằng tọa độ của nhân viên
    centroids = nhan_vien_coords.copy()
    labels = np.zeros(dong_ho_coords.shape[0])
#     print(f"labels {labels}")
    wcss_values = []
    silhouette_scores = []

    for j in range(max_iterations):
        # Bước 1: Gán từng đồng hồ vào cụm gần nhất
#         print(f"labels of {j}: {labels}")
        for i, dong_ho in enumerate(dong_ho_coords):
            # print(f"i: {i}, dong_ho {dong_ho}")
            # tính khoảng cách từ đồng hồ thứ i đến các cụm
            distances = [haversine(dong_ho, centroid) for centroid in centroids]
            # print(f"distances {distances}")
            # trả về index có giá trị khoảng cách min, index = 2 ở đây nghĩa là cụm thứ 2
            # labels[i] = 2: nghĩa là đồng hồ thứ i thuoc cụm thứ 2
            labels[i] = np.argmin(distances)
            # print(f"labels: {labels}")

        # Tính WCSS sau mỗi lần cập nhật
        wcss = calculate_wcss_haversine(dong_ho_coords, centroids, labels)
        wcss_values.append(wcss)

        # Tính Silhouette Score sau mỗi lần cập nhật
        avg_silhouette_score, _ = silhouette_score_haversine(dong_ho_coords, labels, centroids)
        silhouette_scores.append(avg_silhouette_score)

        # Bước 2: Cập nhật vị trí trung tâm cụm
        new_centroids = []
        for i in range(n_clusters):
            # lấy ra danh sách sách đồng hồ đã được chia cụm ở trên
            # labels == i: nghĩa là lấy từng giá trị trong labels so sánh với cụm thứ i
            points_in_cluster = dong_ho_coords[labels == i]
            # print(f"points_in_cluster {points_in_cluster}")
            if points_in_cluster.size > 0:
                # tính khoảng cách giữa các điểm trong cụm để tạo vị trí trung tâm cụm mới
                new_centroid = geographic_centroid(points_in_cluster)
                new_centroids.append(new_centroid)
            else:
                new_centroids.append(centroids[i])  # Giữ nguyên nếu không có điểm nào

        centroids = np.array(new_centroids)

    return labels, centroids, wcss_values, silhouette_scores

def plot_cluster(dong_ho_coords, nhan_vien_coords, labels, centroids):
    plt.figure(figsize=(10, 10))
    scatter = plt.scatter(dong_ho_coords[:, 0], dong_ho_coords[:, 1], c=labels, cmap='viridis', s=10, label="Đồng hồ")
    plt.scatter(nhan_vien_coords[:, 0], nhan_vien_coords[:, 1], c='red', marker='x', s=50, label="Nhân viên")
    plt.scatter(centroids[:, 0], centroids[:, 1], c='blue', marker='o', s=60, label="Trung tâm cụm")

    # Vẽ đường tròn bao quanh mỗi cụm
    for center in centroids:
        distances = np.linalg.norm(dong_ho_coords[labels == np.where(centroids == center)[0][0]] - center, axis=1)
        radius = np.mean(distances)
        circle = Circle(center, radius, color='orange', fill=False, linestyle='dotted', linewidth=2)
        plt.gca().add_patch(circle)

    plt.title("Biểu đồ phân cụm đồng hồ theo vị trí nhân viên")
    plt.xlabel("Tọa độ X")
    plt.ylabel("Tọa độ Y")
    plt.legend()
    plt.colorbar(scatter, label="Cụm")
    plt.show()


def compute_cluster_distribution(dong_ho_coords, labels, number_employee):
    assignments = {i: [] for i in range(number_employee)}
    for i in range(len(labels)):
        assignments[labels[i]].append(dong_ho_coords[i])

    sizes = [len(assignments[i]) for i in range(number_employee)]
    return sizes


def visualize_cluster_distribution(sizes):
    plt.bar(range(len(sizes)), sizes)
    plt.xlabel('Cụm')
    plt.ylabel('Số lượng đồng hồ')
    plt.title('Phân phối số lượng đồng hồ trong các cụm')
    plt.xticks(range(len(sizes)))
    plt.show()

def is_distribution_even(sizes):
    mean_size = np.mean(sizes)
    std_dev = np.std(sizes)
    print(f"Kích thước trung bình của các cụm: {mean_size}")
    print(f"Độ lệch chuẩn: {std_dev}")
    return std_dev < 1  # Có thể điều chỉnh ngưỡng này
def calculate_wcss_haversine(dong_ho_coords, centroids, labels):
    """
    Tính toán Within-Cluster Sum of Squares (WCSS) cho KMeans sử dụng khoảng cách Haversine.
    """
    wcss = 0
    for i, centroid in enumerate(centroids):
        # Lấy tất cả điểm trong cụm `i`
        points_in_cluster = dong_ho_coords[labels == i]
        # Tính tổng bình phương khoảng cách từ các điểm đến tâm cụm
        for point in points_in_cluster:
            wcss += haversine(point, centroid) ** 2
    return wcss

def silhouette_score_haversine(dong_ho_coords, labels, centroids):
    """
    Tính Silhouette Score cho từng điểm và trung bình của toàn bộ dataset.
    """
    n_points = dong_ho_coords.shape[0]
    silhouette_scores = []

    for i, point in enumerate(dong_ho_coords):
        # Nhãn cụm của điểm hiện tại
        cluster_label = int(labels[i])

        # Các điểm trong cùng cụm
        same_cluster_points = dong_ho_coords[labels == cluster_label]

        # Tính khoảng cách trung bình đến các điểm trong cùng cụm (a)
        if len(same_cluster_points) > 1:
            a = np.mean([haversine(point, other) for other in same_cluster_points if not np.array_equal(point, other)])
        else:
            a = 0  # Nếu chỉ có một điểm, khoảng cách trung bình là 0

        # Tính khoảng cách trung bình đến các cụm khác (b)
        b = float('inf')
        for j, centroid in enumerate(centroids):
            if j != cluster_label:
                other_cluster_points = dong_ho_coords[labels == j]
                if len(other_cluster_points) > 0:
                    avg_dist_to_other_cluster = np.mean([haversine(point, other) for other in other_cluster_points])
                    b = min(b, avg_dist_to_other_cluster)

        # Tính Silhouette Score cho điểm hiện tại
        s = (b - a) / max(a, b) if max(a, b) > 0 else 0
        silhouette_scores.append(s)

    # Tính Silhouette Score trung bình
    avg_silhouette_score = np.mean(silhouette_scores)
    return avg_silhouette_score, silhouette_scores
def plotWCSS(wcss_values):
    plt.plot(range(len(wcss_values)), wcss_values, marker='o')
    plt.title('WCSS vs Iterations')
    plt.xlabel('Iterations')
    plt.ylabel('WCSS')
    plt.show()

def plotSilhouetteScore(silhouette_scores):
    plt.plot(range(len(silhouette_scores)), silhouette_scores, marker='o', label='Silhouette Score')
    plt.title('Silhouette Score vs Iterations')
    plt.xlabel('Iterations')
    plt.ylabel('Silhouette Score')
    plt.legend()
    plt.show()
if __name__ == "__main__":
    # Số lượng đồng hồ và nhân viên
    np.random.seed(0)
    number_power_meter = 100
    number_employee = 9
    # Tạo tọa độ ngẫu nhiên cho đồng hồ và nhân viên
    dong_ho_coords = np.random.rand(number_power_meter, 2) * 100  # tọa độ ngẫu nhiên trong một khu vực 100x100
    nhan_vien_coords = np.random.rand(number_employee, 2) * 100

    # Phân cụm đồng hồ bằng khoảng cách Haversine
    labels, centroids = kmeans_haversine(dong_ho_coords, nhan_vien_coords, n_clusters=number_employee, max_iterations=10)
    print(f"results of labels: {labels}")
    print(f"result of centroids: {centroids}")

    # Bước 2: Tính toán và hiển thị phân phối cụm
    sizes = compute_cluster_distribution(dong_ho_coords, labels, number_employee)
    visualize_cluster_distribution(sizes)

    # Bước 3: Kiểm tra xem sự phân bố có đều hay không
    if is_distribution_even(sizes):
        print("Các đồng hồ đã được phân bố đều trong các cụm.")
    else:
        print("Sự phân bố của các đồng hồ trong các cụm chưa đều.")
    # Vẽ biểu đồ kết quả
    # plot_cluster(dong_ho_coords, nhan_vien_coords, labels, centroids)
