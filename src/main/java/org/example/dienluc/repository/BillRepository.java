package org.example.dienluc.repository;

import org.example.dienluc.entity.Bill;
import org.example.dienluc.service.dto.bill.BillGetDto;
import org.example.dienluc.service.dto.bill.BillOfClientDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.query.Procedure;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public interface BillRepository extends JpaRepository<Bill, Integer> {
    @Procedure(procedureName = "sp_get_total_amout")
    public List<Object[]> getTotalAmountByElectricRecordingId(@Param("electricRecording") int electricRecordingId);
    @Query("SELECT b from Bill b  where b.electricRecording.powerMeter.id = (SELECT c.powerMeter.id from Contract c WHERE c.client.id = ?1 and c.powerMeter.status = true)")
    public List<Bill> findByClientId(Integer clientId, Pageable pageable);
    @Query("SELECT new org.example.dienluc.service.dto.bill.BillGetDto (" +
            "b.id, " +
            "b.invoiceDate, " +
            "b.paymentDueDate, " +
            "b.totalAmount, " +
            "b.paymentDate, " +
            "b.status, " +
            "concat(cast(b.invoiceCreator.id as string),'-', b.invoiceCreator.firstName,' ', b.invoiceCreator.lastName), " +
            "concat(cast(c.client.id as string ),'-', c.client.firstName,' ',c.client.lastName))" +
            "from Bill b join Contract c on c.powerMeter.id= b.electricRecording.powerMeter.id")
    public List<BillGetDto> findAllBills();
}
