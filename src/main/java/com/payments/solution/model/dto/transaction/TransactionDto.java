package com.payments.solution.model.dto.transaction;

import java.math.BigDecimal;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TransactionDto {

    private String uuid;

    private BigDecimal amount;

    private String status;

    private String customerEmail;

    private String customerPhone;

    private String referenceId;
}
