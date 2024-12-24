package org.example.dienluc.repository;

import org.example.dienluc.entity.Level;
import org.example.dienluc.service.dto.level.LevelGetByElectricRecordingDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LevelRepository extends JpaRepository<Level, Integer> {
    @Query("SELECT l FROM Level l where l.id not in (select ep.level.id from ElectricityPrice ep where ep.electricType.id = ?1)")
    public List<Level> getLevelNotUseByElectricType(Integer electricTypeId);

    @Query(value = """
            select MUCDAU as oldIndex, MUCCUOI as newIndex, DONGIA as price from GIADIEN,
            	(select IDLOAIDIEN, diennang from hopdong,\s
            		(select IDDONGHODIEN, diennang = CHISOMOI - CHISOCU from ghidien where id = ?1) GD
            		WHERE GD.IDDONGHODIEN = HOPDONG.IDDONGHODIEN) HD, BAC
            WHERE GIADIEN.IDLOAIDIEN = HD.IDLOAIDIEN AND BAC.ID = GIADIEN.IDBAC order by oldIndex
            """, nativeQuery = true)
    public List<Object[]> findByElectricRecordingId(Integer electricRecordingId);
}
