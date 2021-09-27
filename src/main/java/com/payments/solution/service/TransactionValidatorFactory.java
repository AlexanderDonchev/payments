package com.payments.solution.service;

import com.payments.solution.exception.BadRequestException;
import com.payments.solution.model.TransactionType;
import com.payments.solution.service.validator.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class TransactionValidatorFactory {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionValidatorFactory.class);

    public TransactionValidator getValidator(TransactionType type) {

        switch (type) {
            case AUTHORISE:
                return new AuthoriseTransactionValidator();
            case REVERSAL:
                return new ReverseTransactionValidator();
            case REFUND:
                return new RefundTransactionValidator();
            case CHARGE:
                return new ChargeTransactionValidator();
            default:

                LOGGER.error("Unknown transaction type submitted");
                throw new BadRequestException();
        }
    }
}
