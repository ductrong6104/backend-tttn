package org.example.dienluc.service;

import org.example.dienluc.entity.Bill;
import org.example.dienluc.service.dto.bill.*;

import java.math.BigDecimal;
import java.util.List;

public interface BillService {
    public TotalAmountResponse getTotalAmountByElectricRecordingId(Integer electricRecordingId);

    public Bill createNewBill(BillCreateDto billCreateDto);

    public List<BillGetDto> getAllBill();

    public BillOfClientDto getBillByClientId(Integer clientId);

    public Bill updateStatusBill(Integer billId, Boolean status);

    public BillGeneratePdfDto getBillToGeneratePdf(Integer billId, Integer clientId);

    public List<BillOfClientDto> getUnpaidInvoicesByClientId(Integer clientId);

    public List<BillOfClientDto> getAllInvoicesByClientId(Integer clientId);
}
