package com.payments.solution.service.processor;

import com.payments.solution.exception.AccessForbiddenException;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import com.payments.solution.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.payments.solution.model.TransactionStatus.*;

@Component
public class ReverseTransactionProcessor extends TransactionProcessor{

    private final Logger LOGGER = LoggerFactory.getLogger(ReverseTransactionProcessor.class);

    protected ReverseTransactionProcessor(TransactionRepository transactionRepository) {
        super(transactionRepository);
    }

    @Override
    public void processTransaction(Merchant merchant, TransactionData data) {

        Optional<Transaction> transactionOpt = transactionRepository.findByUuid(data.getUuid());

        if (transactionOpt.isPresent()) {

            Transaction transaction = transactionOpt.get();
            validateAccess(merchant, transaction);

            if (!transaction.getStatus().equals(PENDING.name())){

                throw new BadRequestException();
            }

            transaction.setStatus(StringUtils.hasLength(data.getReferenceId()) ? ERROR.name() : REVERSED.name());
            transaction.setUpdatedOn(LocalDateTime.now());

            transactionRepository.save(transaction);
        } else {

            LOGGER.warn(String.format("Merchant [%d] tried to refund a txn with uuid [%s] which does not exist",
                    merchant.getId(), data.getUuid()));
            throw new AccessForbiddenException();
        }

    }
}
