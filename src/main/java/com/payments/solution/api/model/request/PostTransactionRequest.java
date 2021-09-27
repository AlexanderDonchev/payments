package com.payments.solution.api.model.request;

import com.payments.solution.model.TransactionType;
import com.payments.solution.model.dto.transaction.TransactionData;
import lombok.Data;

@Data
public class PostTransactionRequest {

    private TransactionType type;
    private TransactionData data;
}
