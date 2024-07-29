package org.example.dienluc.repository;

import org.example.dienluc.entity.Client;
import org.example.dienluc.entity.Contract;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.PowerMeter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    public Contract findByClientAndEndDate(Client client, String endDate);
    public List<Contract> findByPowerMeter(PowerMeter powerMeter);
    public List<Contract> findByProcessingEmployeeIsNull();
    public List<Contract> findByProcessingEmployeeIsNotNull();

}
