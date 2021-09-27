package com.payments.solution.api;

import com.payments.solution.api.model.request.PostTransactionRequest;
import com.payments.solution.api.model.response.GetTransactionsResponse;
import com.payments.solution.model.dto.transaction.TransactionDto;
import com.payments.solution.model.dto.UserDto;
import com.payments.solution.service.TransactionService;
import java.util.List;
import javax.annotation.security.RolesAllowed;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @RolesAllowed("MERCHANT")
    @PostMapping
    public ResponseEntity<Void> postTransaction(@RequestBody PostTransactionRequest postTransactionRequest,
            @AuthenticationPrincipal(errorOnInvalidType = true)
            UserDto userDto) {

        transactionService.applyTransaction(userDto.getId(), postTransactionRequest.getType(),
                postTransactionRequest.getData());
        return null;
    }

    @RolesAllowed("MERCHANT")
    @GetMapping
    public ResponseEntity<GetTransactionsResponse> getTransactions(@RequestParam int page, @RequestParam int size,
            @AuthenticationPrincipal(errorOnInvalidType = true) UserDto userDto) {

        GetTransactionsResponse transactions = transactionService.getTransactionsForMerchant(userDto.getId(), page,
                size);

        return ResponseEntity.ok(transactions);
    }

    @RolesAllowed("ADMIN")
    @GetMapping("/admin")
    public ResponseEntity<GetTransactionsResponse> getTransactions(@RequestParam("merchantId") Long merchantId,
            @RequestParam int page, @RequestParam int size) {

        GetTransactionsResponse transactions = transactionService.getTransactionsForMerchant(merchantId, page, size);

        return ResponseEntity.ok(transactions);
    }
}
