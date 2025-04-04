package org.example.dienluc.service.serviceImpl;

import jakarta.persistence.EntityNotFoundException;
import org.example.dienluc.entity.*;
import org.example.dienluc.repository.*;
import org.example.dienluc.service.BillService;
import org.example.dienluc.service.dto.bill.*;
import org.example.dienluc.service.dto.electricRecording.ElectricRecordingFromDateGetDto;
import org.example.dienluc.service.dto.electricityPrice.ElectricPriceGetPriceDto;
import org.example.dienluc.service.dto.level.LevelGetByElectricRecordingDto;
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
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BillServiceImpl implements BillService {
    private final BillRepository billRepository;
    private final ModelMapper modelMapper;
    private final ClientRepository clientRepository;
    private final ElectricityPriceRepository electricityPriceRepository;
    private final ElectricRecordingRepository electricRecordingRepository;
    private final ContractRepository contractRepository;
    private final PowerMeterRepository powerMeterRepository;
    private final LevelRepository levelRepository;

    public BillServiceImpl(BillRepository billRepository, ModelMapper modelMapper, ModelMapper modelMapper1, ClientRepository clientRepository, ElectricityPriceRepository electricityPriceRepository, ElectricRecordingRepository electricRecordingRepository, ContractRepository contractRepository, PowerMeterRepository powerMeterRepository, LevelRepository levelRepository) {
        this.billRepository = billRepository;
        this.modelMapper = modelMapper1;
        this.clientRepository = clientRepository;
        this.electricityPriceRepository = electricityPriceRepository;
        this.electricRecordingRepository = electricRecordingRepository;
        this.contractRepository = contractRepository;
        this.powerMeterRepository = powerMeterRepository;
        this.levelRepository = levelRepository;
    }
    @Transactional
    @Override
    public TotalAmountResponse getTotalAmountByElectricRecordingId(Integer electricRecordingId) {
//        List<Object[]> results = billRepository.getTotalAmountByElectricRecordingId(electricRecordingId);
//        String[] fields = {"totalAmount"};
        List<Object[]> results = levelRepository.findByElectricRecordingId(electricRecordingId);
        String[] fields = {"oldIndex", "newIndex", "price"};
        List<LevelGetByElectricRecordingDto> levelGetByElectricRecordingDtos = MapperUtil.mapResults(results, LevelGetByElectricRecordingDto.class, fields);
        ElectricRecording electricRecording = electricRecordingRepository.findById(electricRecordingId)
                .orElseThrow(() -> new EntityNotFoundException("Electric Recording not found with id: " + electricRecordingId));

        BigDecimal totalAmount = BigDecimal.ZERO; // Khởi tạo totalAmount
        BigDecimal powerConsumption = BigDecimal.valueOf(electricRecording.getPowerConsumption()); // Khởi tạo từ chỉ số mới
        System.out.println("powerConsumption" + powerConsumption);
        for (LevelGetByElectricRecordingDto levelGetByElectricRecordingDto : levelGetByElectricRecordingDtos) {

            if (levelGetByElectricRecordingDto.getNewIndex() != null) {
                BigDecimal newIndex = BigDecimal.valueOf(levelGetByElectricRecordingDto.getNewIndex());
                BigDecimal oldIndex = BigDecimal.valueOf(levelGetByElectricRecordingDto.getOldIndex());
                BigDecimal price = levelGetByElectricRecordingDto.getPrice();

                // Tính mức tiêu thụ
                BigDecimal powerConsumptionChange = powerConsumption.subtract(newIndex.subtract(oldIndex).add(BigDecimal.valueOf(1)));
                System.out.println("powerConsumptionChange " + powerConsumptionChange);
                if (powerConsumptionChange.compareTo(BigDecimal.ZERO) > 0) {
                    // Nếu còn dư mức tiêu thụ
                    totalAmount = totalAmount.add(
                            newIndex.subtract(oldIndex).add(BigDecimal.valueOf(1)).multiply(price)
                    );
                }
                else if (powerConsumptionChange.compareTo(BigDecimal.ZERO) == 0){
                    totalAmount = totalAmount.add(
                            newIndex.subtract(oldIndex).add(BigDecimal.valueOf(1)).multiply(price)
                    );
                    break;
                } else {
                    // Nếu vượt ngưỡng tiêu thụ
                    totalAmount = totalAmount.add(
                        powerConsumption.multiply(price)
                    );
                    break;
                }
                powerConsumption = powerConsumptionChange;
            } else {
                // Trường hợp không có newIndex
                BigDecimal price = levelGetByElectricRecordingDto.getPrice();
                System.out.println("khonh co new index" + powerConsumption);
                // Tính mức tiêu thụ
                totalAmount = totalAmount.add(
                    powerConsumption.multiply(price)
                );
                // Trả về kết quả
                break;
            }
            System.out.println("total amout" + totalAmount);
        }

// Trả về kết quả
        return new TotalAmountResponse(totalAmount);


    }

    @Override
    public Bill createNewBill(BillCreateDto billCreateDto) {
        Bill bill = modelMapper.map(billCreateDto, Bill.class);
        System.out.println(bill);
        bill.setPaymentDate(null);
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
        billGeneratePdfDto.setElectricitySupplyAddress(bill.getElectricRecording().getPowerMeter().getInstallationLocation());
        billGeneratePdfDto.setElectricTypeName(client.getContracts().get(0).getElectricType().getName());
        billGeneratePdfDto.setOldIndex(bill.getElectricRecording().getOldIndex());
        billGeneratePdfDto.setNewIndex(bill.getElectricRecording().getNewIndex());
        billGeneratePdfDto.setClientId(clientId.toString());
        billGeneratePdfDto.setTotalAmount(bill.getTotalAmount().toString());

        return billGeneratePdfDto;

    }

    @Override
    public List<BillOfClientDto> getUnpaidInvoicesByClientId(Integer clientId) {
        return billRepository.findByClientIdAndStatus(clientId, false).stream()
                .map(bill ->{
                   BillOfClientDto billOfClientDto = modelMapper.map(bill, BillOfClientDto.class);
                   billOfClientDto.setPaymentStatus(bill.getStatus());
                   return billOfClientDto;
                } )
                .collect(Collectors.toList());

    }

    @Override
    public List<BillOfClientDto> getAllInvoicesByClientId(Integer clientId) {
        return billRepository.getAllInvoicesByClientId(clientId).stream()
                .map(bill -> {
                    BillOfClientDto billOfClientDto = modelMapper.map(bill, BillOfClientDto.class);
                    billOfClientDto.setPaymentStatus(bill.getStatus());
                    return billOfClientDto;
                })
                .collect(Collectors.toList());
    }

    @Override
    public BillGeneratePdfPaymentDto getBillToGeneratePdfPayment(Integer billId, Integer clientId) {
        BillGeneratePdfPaymentDto billGeneratePdfPaymentDto = new BillGeneratePdfPaymentDto();
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new EntityNotFoundException("Client not found with id: " + clientId));
        Bill bill = billRepository.findById(billId)
                .orElseThrow(() -> new EntityNotFoundException("Bill not found with id: " + billId));
        Double electricUsed = bill.getElectricRecording().getNewIndex() - bill.getElectricRecording().getOldIndex();
        // chú ý: để lấy giá điện dựa theo hóa đơn thì phải truy vấn theo loại điện,
        // để lấy được loại điện thì phải truy  vấn từ mã đồng hồ trong hợp đồng chứ không
        // phải mã khách hàng trong hợp đồng vì một khách hàng có thể đăng ký nhiều hợp đồng
        // trong khi mỗi đồng hồ duy nhất cho mỗi hợp đồng
        String fromDate = electricRecordingRepository.getRecordingDateBefore(bill.getElectricRecording().getRecordingDate(), bill.getElectricRecording().getPowerMeter().getId());
        if (Objects.isNull(fromDate)){
//            String[] fields = {"fromDate"};
//            System.out.println("from date is not empty");
//            billGeneratePdfPaymentDto.setFromDate(MapperUtil.mapResults(fromDates, ElectricRecordingFromDateGetDto.class, fields).get(0).getFromDate());

            System.out.println("from date is empty");
            billGeneratePdfPaymentDto.setFromDate(contractRepository.getStartDateByMeterId(bill.getElectricRecording().getPowerMeter().getId()));
        }
        else{
            billGeneratePdfPaymentDto.setFromDate(fromDate);
        }
        List<Object[]> results = electricityPriceRepository.findPriceByElectricTypeAndElectricUsed(electricUsed, bill.getElectricRecording().getPowerMeter().getContract().getElectricType().getId());
        String[] fields = {"price"};
        List<ElectricPriceGetPriceDto> electricPriceGetPriceDtos = MapperUtil.mapResults(results, ElectricPriceGetPriceDto.class, fields);
        if (!electricPriceGetPriceDtos.isEmpty())
            System.out.println("hihihi");
            billGeneratePdfPaymentDto.setPrice(electricPriceGetPriceDtos.get(0).getPrice().toString());
//        System.out.println("client: " + client.toString());
        billGeneratePdfPaymentDto.setFullName(client.getFullName());
        billGeneratePdfPaymentDto.setAddress(bill.getElectricRecording().getPowerMeter().getInstallationLocation());
        billGeneratePdfPaymentDto.setConsumption(electricUsed.toString());
        billGeneratePdfPaymentDto.setToDate(bill.getElectricRecording().getRecordingDate());

        billGeneratePdfPaymentDto.setTotalAmount(bill.getTotalAmount().toString());
        System.out.println(billGeneratePdfPaymentDto);
        return billGeneratePdfPaymentDto;
    }
}
