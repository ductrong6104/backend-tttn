import numpy as np
import pyodbc
import pandas as pd
# import json
from cluster_haversine import kmeans_haversine, visualize_cluster_distribution, compute_cluster_distribution, \
    is_distribution_even

import sys
import io
# Đặt mã hóa UTF-8 cho output: Thêm dòng này vào đầu file Python để đặt mã hóa mặc định cho stdout và stderr thành UTF-8:
sys.stdout = io.TextIOWrapper(sys.stdout.buffer, encoding='utf-8')
# Thông tin kết nối
server = 'localhost'  # Tên hoặc địa chỉ máy chủ SQL Server
database = 'DienLuc'  # Tên cơ sở dữ liệu
username = 'sa'  # Tên người dùng
password = 'P@ssw0rd!2024'  # Mật khẩu

# Tạo chuỗi kết nối
connection_string = f"DRIVER={{ODBC Driver 17 for SQL Server}};SERVER={server};DATABASE={database};UID={username};PWD={password}"
# Kết nối đến cơ sở dữ liệu
conn = pyodbc.connect(connection_string)
cursor = conn.cursor()
def read_data(query):
    # Thực hiện truy vấn

    data = pd.read_sql(query, conn)
    # print(data)
    return data




# Tạo tọa độ ngẫu nhiên cho đồng hồ và nhân viên
dong_ho = read_data('''
    DECLARE @startDate DATE = DATEADD(MONTH, DATEDIFF(MONTH, 0, GETDATE()), 0)
DECLARE @endDate DATE =  EOMONTH(GETDATE())
select ID, KINHDO, VIDO from 
    (SELECT ID, KINHDO, VIDO FROM DONGHODIEN where TRANGTHAI = 'true') dhd, 
    (select IDDONGHODIEN = ISNULL(hd.IDDONGHODIEN, gd.IDDONGHODIEN), CHISOMOI = ISNULL(CHISOMOI, 0) from 
        (select hd.IDDONGHODIEN from  HOPDONG hd WHERE hd.NGAYKTHD IS NULL AND hd.IDDONGHODIEN NOT IN 
            (SELECT IDDONGHODIEN FROM GHIDIEN GROUP BY IDDONGHODIEN)) hd full outer join
                (select IDDONGHODIEN, CHISOMOI from GHIDIEN WHERE IDDONGHODIEN NOT IN (SELECT IDDONGHODIEN FROM GHIDIEN WHERE NGAYGHICSD BETWEEN @startDate AND @endDate OR NGAYGHICSD IS NULL)) 
                gd on hd.IDDONGHODIEN = gd.IDDONGHODIEN) gd WHERE dhd.id = gd.IDDONGHODIEN group by id, KINHDO, VIDO
''')
# tọa độ ngẫu nhiên trong một khu vực 100x100
nhan_vien = read_data('''
SELECT ID, HOTEN = HO + ' ' + TEN, KINHDO, VIDO FROM NHANVIEN WHERE IDTAIKHOAN IN (SELECT ID FROM TAIKHOAN WHERE IDQUYEN = 2)
'''
                      )

dong_ho_coords = dong_ho.drop(columns=['ID']).to_numpy()
nhan_vien_coords = nhan_vien.drop(  columns=['ID', 'HOTEN']).to_numpy()

number_power_meter = len(dong_ho_coords)
number_employee = len(nhan_vien_coords)

#     print(dong_ho_coords)
#     print(nhan_vien_coords)
#
# # Phân cụm đồng hồ bằng khoảng cách Haversine
labels, centroids = kmeans_haversine(dong_ho_coords, nhan_vien_coords, n_clusters=number_employee,
                                     max_iterations=10)
#     print(f"results of labels: {labels}")
#     print(f"result of centroids: {centroids}")
# Bước 2: Tính toán và hiển thị phân phối cụm
# sizes = compute_cluster_distribution(dong_ho_coords, labels, number_employee)
# visualize_cluster_distribution(sizes)
#
# # Bước 3: Kiểm tra xem sự phân bố có đều hay không
# if is_distribution_even(sizes):
#     print("Các đồng hồ đã được phân bố đều trong các cụm.")
# else:
#     print("Sự phân bố của các đồng hồ trong các cụm chưa đều.")
assignments = {}
for i in range(number_power_meter):
    dong_ho_id = int(dong_ho.iloc[i]['ID'])
    id_hotennv = str(nhan_vien.iloc[int(labels[i])]['ID']) + '-' + nhan_vien.iloc[int(labels[i])]['HOTEN']
    # Kiểm tra nếu dong_ho_id chưa có trong assignments, khởi tạo list rỗng
    if f"{id_hotennv}" not in assignments:
        assignments[f"{id_hotennv}"] = []

    # Thêm phần tử mới vào danh sách của dong_ho_id
    assignments[f"{id_hotennv}"].append(dong_ho_id)  # 'new_value' là cột bạn muốn thêm
#     print(assignments)

# # Chuẩn bị dữ liệu cho bulk insert
# insert_data = []
# for employee_id, power_meter_ids in assignments.items():
#     for meter_id in power_meter_ids:
#         insert_data.append((employee_id, meter_id))
#
# # Thực hiện bulk insert với execute_many hoặc Pandas (nếu có nhiều dữ liệu)
# cursor.executemany("INSERT INTO AssignedMeters (EmployeeID, MeterID) VALUES (?, ?)", insert_data)
# conn.commit()
# Đóng kết nối
cursor.close()
conn.close()
print(assignments)


