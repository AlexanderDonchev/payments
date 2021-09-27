package com.payments.solution.model.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(indexes = {@Index(columnList = "uuid", unique = true),
        @Index(columnList = "status"),
        @Index(columnList = "referenceId", unique = true)})
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="merchant_id", nullable=false)
    private Merchant merchant;

    @Column(nullable = false)
    private String uuid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Column(nullable = false)
    private String status;

    @Column(nullable = false)
    private String customerEmail;

    @Column
    private String customerPhone;

    @Column
    private String referenceId;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @Column(nullable = false)
    private LocalDateTime updatedOn;
}
