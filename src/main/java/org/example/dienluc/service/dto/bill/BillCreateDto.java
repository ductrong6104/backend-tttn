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
public class BillCreateDto {
    private Integer id;
    private String invoiceDate;
    private String paymentDueDate;
    private BigDecimal totalAmount;
    private String paymentDate;
    private Boolean status;

}
