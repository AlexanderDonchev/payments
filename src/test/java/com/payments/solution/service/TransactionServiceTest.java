package com.payments.solution.service;

import com.payments.solution.api.model.response.GetTransactionsResponse;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.exception.DataValidationException;
import com.payments.solution.model.TransactionType;
import com.payments.solution.model.dto.transaction.TransactionData;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.repository.MerchantRepository;
import com.payments.solution.service.processor.TransactionProcessor;
import com.payments.solution.service.validator.TransactionValidator;
import com.payments.solution.service.validator.error.ZeroAmountError;
import com.payments.solution.util.DbUtil;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;

@SpringBootTest
@ActiveProfiles("test")
public class TransactionServiceTest {

    @Autowired
    private DbUtil dbUtil;

    @MockBean
    private TransactionValidatorFactory transactionValidatorFactory;

    @MockBean
    private TransactionProcessorFactory transactionProcessorFactory;

    @MockBean
    private MerchantRepository merchantRepository;

    @Autowired
    private TransactionService subject;

    private long merchantId;

    private Merchant merchant;

    private TransactionData transactionData;

    private TransactionValidator validator;

    private TransactionProcessor processor;


    @BeforeEach
    public void setup() {
        merchantId = Long.parseLong(RandomStringUtils.randomNumeric(6));
        merchant = new Merchant();
        merchant.setId(merchantId);
    }

    @Nested
    public class ApplyTransactionTests{

        @BeforeEach
        public void setup() {

            transactionData = TransactionData.builder()
                    .amount(BigDecimal.TEN)
                    .uuid(UUID.randomUUID().toString())
                    .customerEmail("email@customer.com")
                    .customerPhone("+44771231233")
                    .referenceId(RandomStringUtils.random(6))
                    .build();

            validator = mock(TransactionValidator.class);
            processor = mock(TransactionProcessor.class);
        }

        @Test
        public void applyTransaction_validRequest_shouldApply(){

            doReturn(Optional.of(merchant))
                    .when(merchantRepository)
                    .findById(merchantId);

            doReturn(validator)
                    .when(transactionValidatorFactory)
                    .getValidator(TransactionType.CHARGE);

            doReturn(new ArrayList<>())
                    .when(validator)
                    .validate(any());

            doReturn(processor)
                    .when(transactionProcessorFactory)
                    .getProcessor(TransactionType.CHARGE);

            assertDoesNotThrow(() -> subject.applyTransaction(merchantId, TransactionType.CHARGE, transactionData));
        }

        @Test
        public void applyTransaction_invalidData_shouldThrow(){

            doReturn(Optional.of(merchant))
                    .when(merchantRepository)
                    .findById(merchantId);

            doReturn(validator)
                    .when(transactionValidatorFactory)
                    .getValidator(TransactionType.CHARGE);

            doReturn(List.of(new ZeroAmountError()))
                    .when(validator)
                    .validate(any());

            doReturn(processor)
                    .when(transactionProcessorFactory)
                    .getProcessor(TransactionType.CHARGE);

            assertThrows(DataValidationException.class,
                    () -> subject.applyTransaction(merchantId, TransactionType.CHARGE, transactionData));
        }

        @Test
        public void applyTransaction_merchantDoesNotExist_shouldThrow(){

            doReturn(Optional.empty())
                    .when(merchantRepository)
                    .findById(merchantId);

            assertThrows(BadRequestException.class,
                    () -> subject.applyTransaction(merchantId, TransactionType.CHARGE, transactionData));
        }
    }

    @Nested
    public class GetTransactionsForMerchant{

        @BeforeEach
        public void setup() {

            dbUtil.initialise();
        }

        @AfterEach
        public void teardown() {

            dbUtil.teardown();
        }

        @Test
        public void getTransactionsForMerchant_validRequest_shouldReturnTransactions() {

            GetTransactionsResponse response = subject.getTransactionsForMerchant(dbUtil.getMerchantId(),
                    0, 20);

            assertEquals(7, response.getTransactions().size());
            assertEquals(0, response.getPage());
            assertEquals(20, response.getSize());
            assertEquals(1, response.getTotalPages());
        }

        @Test
        public void getTransactionsForMerchant_invalidPage_shouldReturnTransactions() {

            assertThrows(BadRequestException.class, () ->subject.getTransactionsForMerchant(dbUtil.getMerchantId(),
                    -1, 20));
        }

        @Test
        public void getTransactionsForMerchant_invalidSize_shouldReturnTransactions() {

            assertThrows(BadRequestException.class, () ->subject.getTransactionsForMerchant(dbUtil.getMerchantId(),
                    0, -1));
        }
    }
}
