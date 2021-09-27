package com.payments.solution.repository;

import com.payments.solution.model.entity.Merchant;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MerchantRepository extends JpaRepository<Merchant, Long> {

    @Query(nativeQuery = true,value = "SELECT m.id, m.name, m.description, m.email ,m.status, COALESCE(sum(t.amount),0) "
            + "FROM user m LEFT OUTER JOIN transaction t on t.merchant_id = m.id "
            + "WHERE m.role = 'MERCHANT' AND (t.status = 'APPROVED' OR t.amount is null) "
            + "GROUP BY m.id "
            + "ORDER BY m.id")
    Page<Object[]> findAllOrderById(Pageable pageable);
}
