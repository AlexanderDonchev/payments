package com.payments.solution.api.model.response;

import com.payments.solution.model.dto.transaction.TransactionDto;
import java.util.List;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GetTransactionsResponse {

    private List<TransactionDto> transactions;

    private int page;

    private int size;

    private int totalPages;
}
