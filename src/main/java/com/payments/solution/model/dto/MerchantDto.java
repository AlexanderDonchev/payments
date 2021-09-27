package com.payments.solution.model.dto;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class MerchantDto {

    private long id;

    private String name;

    private String description;

    private String email;

    private String status;

    private BigDecimal totalTransactionSum;
}
