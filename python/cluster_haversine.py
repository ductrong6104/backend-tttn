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

    return labels, centroids

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
