package com.payments.solution.repository;

import com.payments.solution.model.entity.Transaction;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import javax.persistence.LockModeType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {

    Page<Transaction> findAllByMerchantId(long merchantId, Pageable pageable);

    List<Transaction> findAllByMerchantIdAndStatus(long merchantId, String status);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Transaction> findByUuid(String uuid);
}
