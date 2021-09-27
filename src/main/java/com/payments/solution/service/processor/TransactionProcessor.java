package com.payments.solution.service.processor;

import com.payments.solution.exception.AccessForbiddenException;
import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import com.payments.solution.repository.TransactionRepository;

public abstract class TransactionProcessor {

    protected final TransactionRepository transactionRepository;

    protected TransactionProcessor(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    protected void validateAccess(Merchant merchant, Transaction transaction) {
        if (merchant.getId() != transaction.getMerchant().getId()) {

            throw new AccessForbiddenException();
        }
    }

    public abstract void processTransaction(Merchant merchant, TransactionData data);
}
