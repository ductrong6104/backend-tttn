package org.example.dienluc.repository;

import org.example.dienluc.entity.AssignmentModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<AssignmentModel, Integer> {
    @Query(value = """
            select phancong.ID as id, IDDONGHODIEN as powerMeterId, VITRILAPDAT as installationLocation, idhoten as employeeIdAndFullName
          
            from phancong, (select id, VITRILAPDAT from DONGHODIEN where TRANGTHAI = 1) DH,
                        (select id, idhoten = CONVERT(NVARCHAR, id) + '-' + COALESCE(ho, '') + ' ' + COALESCE(TEN, '') from NHANVIEN where IDTAIKHOAN IN (SELECT ID FROM TAIKHOAN WHERE IDQUYEN = 2)) NV
                        where dh.ID = PHANCONG.IDDONGHODIEN and NV.ID = IDNHANVIEN
            """, nativeQuery = true)
//    CONVERT(NVARCHAR, NGAYPHANCONG, 23) AS assignmentDate
    List<Object[]> getAssignments();
}
