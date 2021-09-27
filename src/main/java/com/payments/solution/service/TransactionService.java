package com.payments.solution.service;

import com.payments.solution.api.model.response.GetTransactionsResponse;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.exception.DataValidationException;
import com.payments.solution.model.TransactionType;
import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.model.dto.transaction.TransactionDto;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import com.payments.solution.model.entity.User;
import com.payments.solution.repository.MerchantRepository;
import com.payments.solution.repository.TransactionRepository;
import com.payments.solution.repository.UserRepository;
import com.payments.solution.service.processor.TransactionProcessor;
import com.payments.solution.service.validator.TransactionValidator;
import com.payments.solution.service.validator.error.Error;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
public class TransactionService {

    private final Logger LOGGER = LoggerFactory.getLogger(TransactionService.class);

    private final TransactionRepository transactionRepository;
    private final TransactionValidatorFactory transactionValidatorFactory;
    private final TransactionProcessorFactory transactionProcessorFactory;
    private final MerchantRepository merchantRepository;

    public TransactionService(TransactionRepository transactionRepository,
            TransactionValidatorFactory transactionValidatorFactory,
            TransactionProcessorFactory transactionProcessorFactory,
            MerchantRepository merchantRepository) {
        this.transactionRepository = transactionRepository;
        this.transactionValidatorFactory = transactionValidatorFactory;
        this.transactionProcessorFactory = transactionProcessorFactory;
        this.merchantRepository = merchantRepository;
    }

    public void applyTransaction(Long merchantId, TransactionType type, TransactionData data) {

        LOGGER.debug(String.format("Applying transaction for merchant [%d] with uuid [%s]", merchantId,
                data.getUuid()));

        Merchant merchant = getTransactionMerchant(merchantId);

        TransactionValidator validator = transactionValidatorFactory.getValidator(type);

        List<Error> errors = validator.validate(data);

        if (!errors.isEmpty()) {

            throw new DataValidationException(errors);
        }

        TransactionProcessor processor = transactionProcessorFactory.getProcessor(type);

        processor.processTransaction(merchant, data);
    }

    public GetTransactionsResponse getTransactionsForMerchant(long merchantId, int page, int size) {

        if (page <0 || size <0) {

            throw new BadRequestException();
        }

        Pageable pageable = PageRequest.of(page, size);
        Page<Transaction> transactions =  transactionRepository.findAllByMerchantId(merchantId, pageable);

        return GetTransactionsResponse.builder()
                .transactions(transactions.getContent().stream()
                .map(this::transactionDto)
                .collect(Collectors.toUnmodifiableList()))
                .page(transactions.getNumber())
                .size(transactions.getSize())
                .totalPages(transactions.getTotalPages())
                .build();
    }

    private Merchant getTransactionMerchant(long merchantId) {

        Optional<Merchant> merchantOpt = merchantRepository.findById(merchantId);

        if (merchantOpt.isPresent()) {

            return merchantOpt.get();

        } else {

            LOGGER.warn(String.format("Merchant with id %d not found", merchantId));
            throw new BadRequestException();
        }
    }

    private TransactionDto transactionDto(Transaction transaction) {

        return TransactionDto.builder()
                .uuid(transaction.getUuid())
                .amount(transaction.getAmount())
                .status(transaction.getStatus())
                .customerEmail(transaction.getCustomerEmail())
                .customerPhone(transaction.getCustomerPhone())
                .referenceId(transaction.getReferenceId())
                .build();
    }
}
