package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.Bill;
import org.example.dienluc.entity.Client;
import org.example.dienluc.repository.BillRepository;
import org.example.dienluc.repository.ClientRepository;
import org.example.dienluc.service.BillService;
import org.example.dienluc.service.dto.bill.*;
import org.example.dienluc.service.dto.powerMeter.PowerMeterRecordableDto;
import org.example.dienluc.util.DateUtil;
import org.example.dienluc.util.MapperUtil;
import org.hibernate.validator.internal.constraintvalidators.hv.Mod11CheckValidator;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;


    public BillServiceImpl(BillRepository billRepository, ModelMapper modelMapper, ModelMapper modelMapper1, ClientRepository clientRepository) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper1;
        this.clientRepository = clientRepository;
    }
    @Transactional
    @Override
    public TotalAmountResponse getTotalAmountByElectricRecordingId(Integer electricRecordingId) {
        List<Object[]> results = billRepository.getTotalAmountByElectricRecordingId(electricRecordingId);
        String[] fields = {"totalAmount"};
        return MapperUtil.mapResults(results, TotalAmountResponse.class, fields).get(0);

    }

    @Override
    public Bill createNewBill(BillCreateDto billCreateDto) {
        Bill bill = modelMapper.map(billCreateDto, Bill.class);
        bill.setPaymentDate(null);
        bill.setId(null);
        return billRepository.save(bill);
    }

    @Override
    public List<BillGetDto> getAllBill() {
        return billRepository.findAllBills();
    }

    @Override
    public BillOfClientDto getBillByClientId(Integer clientId) {
        Pageable pageable = PageRequest.of(0,1);
        List<Bill> results = billRepository.findByClientId(clientId, pageable);
        return results.isEmpty() ? null : modelMapper.map(results.get(0), BillOfClientDto.class);
    }

    @Override
    public Bill updateStatusBill(Integer billId, Boolean status) {
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with id: " + billId));
        bill.setStatus(status);
        bill.setPaymentDate(DateUtil.formatDateForDatabase(LocalDate.now()));
        return billRepository.save(bill);
    }

    @Override
    public BillGeneratePdfDto getBillToGeneratePdf(Integer billId, Integer clientId) {
        BillGeneratePdfDto billGeneratePdfDto = new BillGeneratePdfDto();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with id: " + billId));
//        System.out.println("client: " + client.toString());
        billGeneratePdfDto = modelMapper.map(client, BillGeneratePdfDto.class);
        billGeneratePdfDto = modelMapper.map(bill, BillGeneratePdfDto.class);
        billGeneratePdfDto.setFullName(client.getFullName());
        billGeneratePdfDto.setEmail(client.getEmail());
        billGeneratePdfDto.setAddress(client.getAddress());
        billGeneratePdfDto.setPhone(client.getPhone());
        billGeneratePdfDto.setElectricitySupplyAddress(client.getContracts().get(0).getPowerMeter().getInstallationLocation());
        billGeneratePdfDto.setElectricTypeName(client.getContracts().get(0).getElectricType().getName());
        billGeneratePdfDto.setOldIndex(bill.getElectricRecording().getOldIndex());
        billGeneratePdfDto.setNewIndex(bill.getElectricRecording().getNewIndex());
        billGeneratePdfDto.setClientId(clientId.toString());
        billGeneratePdfDto.setTotalAmount(bill.getTotalAmount().toString());

        return billGeneratePdfDto;

    }
}
