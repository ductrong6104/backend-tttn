package org.example.dienluc.repository;

import org.example.dienluc.entity.ElectricType;
import org.example.dienluc.entity.Level;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElectricTypeRepository extends JpaRepository<ElectricType, Integer> {

}
