package com.payments.solution.service;

import com.payments.solution.exception.BadRequestException;
import com.payments.solution.model.TransactionType;
import com.payments.solution.service.processor.*;
import org.springframework.stereotype.Component;

@Component
public class TransactionProcessorFactory {

    private final AuthoriseTransactionProcessor authoriseTransactionProcessor;
    private final ChargeTransactionProcessor chargeTransactionProcessor;
    private final ReverseTransactionProcessor reverseTransactionProcessor;
    private final RefundTransactionProcessor refundTransactionProcessor;

    public TransactionProcessorFactory(
            AuthoriseTransactionProcessor authoriseTransactionProcessor,
            ChargeTransactionProcessor chargeTransactionProcessor,
            ReverseTransactionProcessor reverseTransactionProcessor,
            RefundTransactionProcessor refundTransactionProcessor) {
        this.authoriseTransactionProcessor = authoriseTransactionProcessor;
        this.chargeTransactionProcessor = chargeTransactionProcessor;
        this.reverseTransactionProcessor = reverseTransactionProcessor;
        this.refundTransactionProcessor = refundTransactionProcessor;
    }

    public TransactionProcessor getProcessor(TransactionType type) {

        switch (type) {
            case CHARGE:
                return chargeTransactionProcessor;
            case REFUND:
                return refundTransactionProcessor;
            case REVERSAL:
                return reverseTransactionProcessor;
            case AUTHORISE:
                return authoriseTransactionProcessor;
            default:
                throw new BadRequestException();
        }
    }
}
