package com.payments.solution.service.processor;

import com.payments.solution.exception.AccessForbiddenException;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.model.TransactionStatus;
import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import com.payments.solution.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChargeTransactionProcessor extends TransactionProcessor {

    private final Logger LOGGER = LoggerFactory.getLogger(ChargeTransactionProcessor.class);

    protected ChargeTransactionProcessor(TransactionRepository transactionRepository) {
        super(transactionRepository);
    }

    @Override
    public void processTransaction(Merchant merchant, TransactionData data) {

        Optional<Transaction> transactionOpt = transactionRepository.findByUuid(data.getUuid());

        Transaction transactionToSave;
        if (transactionOpt.isPresent()) {

            Transaction transaction = transactionOpt.get();

            validateAccess(merchant, transaction);

            if (!transaction.getStatus().equals(TransactionStatus.PENDING.name())){

                throw new BadRequestException();
            }

            transaction.setStatus(TransactionStatus.APPROVED.name());
            transaction.setReferenceId(data.getReferenceId());
            transaction.setUpdatedOn(LocalDateTime.now());
            transactionToSave = transaction;
        } else {

            LOGGER.warn(String.format("Merchant [%d] tried to approve txn with uuid [%s] which does not exist",
                    merchant.getId(), data.getUuid()));
            throw new AccessForbiddenException();
        }

        transactionRepository.save(transactionToSave);
    }
}
