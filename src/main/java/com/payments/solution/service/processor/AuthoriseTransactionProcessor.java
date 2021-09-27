package com.payments.solution.service.processor;

import com.payments.solution.exception.BadRequestException;
import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import com.payments.solution.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.payments.solution.model.TransactionStatus.*;

@Component
public class AuthoriseTransactionProcessor extends TransactionProcessor{

    protected AuthoriseTransactionProcessor(TransactionRepository transactionRepository) {
        super(transactionRepository);
    }

    @Override
    public void processTransaction(Merchant merchant, TransactionData data) {

        Optional<Transaction> transactionOpt = transactionRepository.findByUuid(data.getUuid());

        if (transactionOpt.isPresent()) {

            validateAccess(merchant, transactionOpt.get());

            throw new BadRequestException();
        }

        Transaction transaction = new Transaction();
        transaction.setAmount(data.getAmount());
        transaction.setCustomerEmail(data.getCustomerEmail());
        transaction.setUuid(data.getUuid());
        transaction.setCustomerPhone(data.getCustomerPhone());
        transaction.setStatus(StringUtils.hasLength(data.getReferenceId()) ? ERROR.name() : PENDING.name());
        transaction.setMerchant(merchant);
        transaction.setCreatedOn(LocalDateTime.now());
        transaction.setUpdatedOn(LocalDateTime.now());

        transactionRepository.save(transaction);
    }
}
