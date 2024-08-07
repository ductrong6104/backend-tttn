package org.example.dienluc.service.dto.bill;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BillGeneratePdfPaymentDto {
    private String fullName;
    private String address;
    private String fromDate;
    private String toDate;
    private String price;
    private String totalAmount;
}
