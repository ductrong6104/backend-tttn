package org.example.dienluc.repository;

import org.example.dienluc.entity.Client;
import org.example.dienluc.entity.Contract;
import org.example.dienluc.entity.Employee;
import org.example.dienluc.entity.PowerMeter;
import org.example.dienluc.service.dto.statistical.consumption.StatisticalComsumptionElectricTypeDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContractRepository extends JpaRepository<Contract, Integer> {
    public Contract findByClientAndEndDate(Client client, String endDate);
    public List<Contract> findByPowerMeter(PowerMeter powerMeter);
    public List<Contract> findByProcessingEmployeeIsNull();
    public List<Contract> findByProcessingEmployeeIsNotNull();
    @Query(value = "select hd.IDLOAIDIEN as electricTypeId, SUM(CHISOMOI-CHISOCU) as consumption from GHIDIEN GD, (SELEct IDLOAIDIEN, IDDONGHODIEN from hopdong) HD\n" +
            "WHERE HD.IDDONGHODIEN = GD.IDDONGHODIEN GROUP BY IDLOAIDIEN", nativeQuery = true)
    List<Object[]> getConsumptionElectricTypes();
    @Query("SELECT c FROM Contract c where c.client.id = ?1 and c.contractStatus.id != 3")
    public List<Contract> findByClientId(Integer clientId);
    @Query("SELECT c FROM Contract c where c.client.id = ?1 and c.contractStatus.id = ?2")
    Contract findByClientIdAndStatusId(Integer clientId, Integer statusId);


}
