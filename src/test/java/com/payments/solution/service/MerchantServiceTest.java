package com.payments.solution.service;

import com.payments.solution.api.model.response.GetMerchantsResponse;
import com.payments.solution.exception.BadRequestException;
import com.payments.solution.model.TransactionStatus;
import com.payments.solution.model.dto.MerchantDto;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import com.payments.solution.util.DbUtil;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@ActiveProfiles("test")
public class MerchantServiceTest {

    @Autowired
    private DbUtil dbUtil;

    @Autowired
    private MerchantService subject;

    @BeforeEach
    public void setup() {

        dbUtil.initialise();
    }

    @AfterEach
    public void teardown() {

        dbUtil.teardown();
    }

    @Nested
    public class GetMerchants{

        @Test
        public void getMerchants_validRequest_shouldReturnMerchants() {

            Merchant merchant1 = new Merchant();
            merchant1.setUsername("merchant1");
            merchant1.setPassword("secret");
            merchant1.setRole("MERCHANT");
            merchant1.setStatus("ACTIVE");
            merchant1.setName("merchant1");
            merchant1.setDescription("Sells junk");
            merchant1.setEmail("merchant1@junk.com");
            merchant1.setCreatedOn(LocalDateTime.now());
            merchant1.setUpdatedOn(LocalDateTime.now());
            dbUtil.persistEntity(merchant1);

            Transaction pendingTransaction = new Transaction();
            pendingTransaction.setMerchant(merchant1);
            pendingTransaction.setStatus(TransactionStatus.PENDING.name());
            pendingTransaction.setAmount(BigDecimal.ONE);
            pendingTransaction.setCustomerEmail("customer1@customer.com");
            pendingTransaction.setCustomerPhone("+4477123123123");
            pendingTransaction.setUuid(UUID.randomUUID().toString());
            pendingTransaction.setCreatedOn(LocalDateTime.now());
            pendingTransaction.setUpdatedOn(LocalDateTime.now());

            dbUtil.persistEntity(pendingTransaction);

            Transaction clearedTransaction1 = new Transaction();
            clearedTransaction1.setMerchant(merchant1);
            clearedTransaction1.setStatus(TransactionStatus.APPROVED.name());
            clearedTransaction1.setReferenceId(RandomStringUtils.randomAlphanumeric(6));
            clearedTransaction1.setAmount(BigDecimal.ONE);
            clearedTransaction1.setCustomerEmail("customer1@customer.com");
            clearedTransaction1.setCustomerPhone("+4477123123123");
            clearedTransaction1.setUuid(UUID.randomUUID().toString());
            clearedTransaction1.setCreatedOn(LocalDateTime.now());
            clearedTransaction1.setUpdatedOn(LocalDateTime.now());

            dbUtil.persistEntity(clearedTransaction1);

            Merchant merchant2 = new Merchant();
            merchant2.setUsername("merchant2");
            merchant2.setPassword("secret");
            merchant2.setRole("MERCHANT");
            merchant2.setStatus("ACTIVE");
            merchant2.setName("merchant2");
            merchant2.setDescription("Sells junk");
            merchant2.setEmail("merchant2@junk.com");
            merchant2.setCreatedOn(LocalDateTime.now());
            merchant2.setUpdatedOn(LocalDateTime.now());
            dbUtil.persistEntity(merchant2);

            GetMerchantsResponse response = subject.getMerchants(0, 20);

            assertEquals(3, response.getMerchants().size());
            assertEquals(0, response.getPage());
            assertEquals(20, response.getSize());
            assertEquals(1, response.getTotalPages());

            MerchantDto merchantDto = response.getMerchants().get(0);
            assertEquals("merchant", merchantDto.getName());
            assertEquals(0, BigDecimal.valueOf(10).compareTo(merchantDto.getTotalTransactionSum()));

            MerchantDto merchantDto1 = response.getMerchants().get(1);
            assertEquals("merchant1", merchantDto1.getName());
            assertEquals(0, BigDecimal.ONE.compareTo(merchantDto1.getTotalTransactionSum()));

            MerchantDto merchantDto2 = response.getMerchants().get(2);
            assertEquals("merchant2", merchantDto2.getName());
            assertEquals(0, BigDecimal.ZERO.compareTo(merchantDto2.getTotalTransactionSum()));
        }

        @Test
        public void getMerchants_invalidPage_shouldReturnTransactions() {

            assertThrows(BadRequestException.class, () ->subject.getMerchants(-1, 20));
        }

        @Test
        public void getMerchants_invalidSize_shouldReturnTransactions() {

            assertThrows(BadRequestException.class, () ->subject.getMerchants(0, -1));
        }
    }
}
