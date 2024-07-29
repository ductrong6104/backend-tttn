package org.example.dienluc.service.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillGeneratePdfDto {
    private String fullName;
    private String address;
    private String phone;
    private String email;
    private String electricitySupplyAddress;
    private String electricTypeName;
    private String invoiceDate;
    private Double oldIndex;
    private Double newIndex;
    private String clientId;
    private String totalAmount;
    private String paymentDueDate;
}
