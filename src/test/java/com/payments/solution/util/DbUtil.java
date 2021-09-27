package com.payments.solution.util;

import com.payments.solution.model.TransactionStatus;
import com.payments.solution.model.entity.Merchant;
import com.payments.solution.model.entity.Transaction;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import javax.persistence.EntityManager;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class DbUtil {

    private long merchantId;

    private Merchant merchant;

    private Transaction pendingTransaction;

    private Transaction clearedTransaction1;

    private Transaction clearedTransaction2;

    private Transaction clearedTransaction3;

    private Transaction clearedTransaction4;

    private Transaction reversedTransaction;

    private Transaction refundedTransaction;


    @Autowired
    private EntityManager entityManager;

    @Transactional
    public void persistEntity(Object entity) {

        entityManager.persist(entity);
    }

    @Transactional
    public void teardown() {

        entityManager.createQuery("Delete from Transaction ").executeUpdate();
        entityManager.createQuery("Delete from User").executeUpdate();
    }

    @Transactional
    public void initialise() {

        merchant = new Merchant();
        merchant.setUsername("merchant");
        merchant.setPassword("secret");
        merchant.setRole("MERCHANT");
        merchant.setStatus("ACTIVE");
        merchant.setName("merchant");
        merchant.setDescription("Sells junk");
        merchant.setEmail("merchant@junk.com");
        merchant.setCreatedOn(LocalDateTime.now());
        merchant.setUpdatedOn(LocalDateTime.now());
        entityManager.persist(merchant);

        merchantId = merchant.getId();


        pendingTransaction = new Transaction();
        pendingTransaction.setMerchant(merchant);
        pendingTransaction.setStatus(TransactionStatus.PENDING.name());
        pendingTransaction.setAmount(BigDecimal.ONE);
        pendingTransaction.setCustomerEmail("customer1@customer.com");
        pendingTransaction.setCustomerPhone("+4477123123123");
        pendingTransaction.setUuid(UUID.randomUUID().toString());
        pendingTransaction.setCreatedOn(LocalDateTime.now());
        pendingTransaction.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(pendingTransaction);

        clearedTransaction1 = new Transaction();
        clearedTransaction1.setMerchant(merchant);
        clearedTransaction1.setStatus(TransactionStatus.APPROVED.name());
        clearedTransaction1.setReferenceId(RandomStringUtils.randomAlphanumeric(6));
        clearedTransaction1.setAmount(BigDecimal.ONE);
        clearedTransaction1.setCustomerEmail("customer1@customer.com");
        clearedTransaction1.setCustomerPhone("+4477123123123");
        clearedTransaction1.setUuid(UUID.randomUUID().toString());
        clearedTransaction1.setCreatedOn(LocalDateTime.now());
        clearedTransaction1.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(clearedTransaction1);

        clearedTransaction2 = new Transaction();
        clearedTransaction2.setMerchant(merchant);
        clearedTransaction2.setStatus(TransactionStatus.APPROVED.name());
        clearedTransaction2.setReferenceId(RandomStringUtils.randomAlphanumeric(6));
        clearedTransaction2.setAmount(BigDecimal.valueOf(2));
        clearedTransaction2.setCustomerEmail("customer1@customer.com");
        clearedTransaction2.setCustomerPhone("+4477123123123");
        clearedTransaction2.setUuid(UUID.randomUUID().toString());
        clearedTransaction2.setCreatedOn(LocalDateTime.now());
        clearedTransaction2.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(clearedTransaction2);

        clearedTransaction3 = new Transaction();
        clearedTransaction3.setMerchant(merchant);
        clearedTransaction3.setStatus(TransactionStatus.APPROVED.name());
        clearedTransaction3.setReferenceId(RandomStringUtils.randomAlphanumeric(6));
        clearedTransaction3.setAmount(BigDecimal.valueOf(3));
        clearedTransaction3.setCustomerEmail("customer1@customer.com");
        clearedTransaction3.setCustomerPhone("+4477123123123");
        clearedTransaction3.setUuid(UUID.randomUUID().toString());
        clearedTransaction3.setCreatedOn(LocalDateTime.now());
        clearedTransaction3.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(clearedTransaction3);

        clearedTransaction4 = new Transaction();
        clearedTransaction4.setMerchant(merchant);
        clearedTransaction4.setStatus(TransactionStatus.APPROVED.name());
        clearedTransaction4.setReferenceId(RandomStringUtils.randomAlphanumeric(6));
        clearedTransaction4.setAmount(BigDecimal.valueOf(4));
        clearedTransaction4.setCustomerEmail("customer1@customer.com");
        clearedTransaction4.setCustomerPhone("+4477123123123");
        clearedTransaction4.setUuid(UUID.randomUUID().toString());
        clearedTransaction4.setCreatedOn(LocalDateTime.now());
        clearedTransaction4.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(clearedTransaction4);

        refundedTransaction = new Transaction();
        refundedTransaction.setMerchant(merchant);
        refundedTransaction.setStatus(TransactionStatus.REFUNDED.name());
        refundedTransaction.setReferenceId(RandomStringUtils.randomAlphanumeric(6));
        refundedTransaction.setAmount(BigDecimal.valueOf(5));
        refundedTransaction.setCustomerEmail("customer1@customer.com");
        refundedTransaction.setCustomerPhone("+4477123123123");
        refundedTransaction.setUuid(UUID.randomUUID().toString());
        refundedTransaction.setCreatedOn(LocalDateTime.now());
        refundedTransaction.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(refundedTransaction);

        reversedTransaction = new Transaction();
        reversedTransaction.setMerchant(merchant);
        reversedTransaction.setStatus(TransactionStatus.REVERSED.name());
        reversedTransaction.setAmount(BigDecimal.ONE);
        reversedTransaction.setCustomerEmail("customer1@customer.com");
        reversedTransaction.setCustomerPhone("+4477123123123");
        reversedTransaction.setUuid(UUID.randomUUID().toString());
        reversedTransaction.setCreatedOn(LocalDateTime.now());
        reversedTransaction.setUpdatedOn(LocalDateTime.now());

        entityManager.persist(reversedTransaction);
    }

    public long getMerchantId() {
        return merchantId;
    }

    public Merchant getMerchant() {
        return merchant;
    }

    public Transaction getPendingTransaction() {
        return pendingTransaction;
    }

    public Transaction getClearedTransaction1() {
        return clearedTransaction1;
    }

    public Transaction getClearedTransaction2() {
        return clearedTransaction2;
    }

    public Transaction getClearedTransaction3() {
        return clearedTransaction3;
    }

    public Transaction getClearedTransaction4() {
        return clearedTransaction4;
    }

    public Transaction getReversedTransaction() {
        return reversedTransaction;
    }

    public Transaction getRefundedTransaction() {
        return refundedTransaction;
    }
}
