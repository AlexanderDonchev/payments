package com.payments.solution.model.entity;

import java.util.List;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import lombok.Data;

@Data
@Entity
@DiscriminatorValue("MERCHANT")
public class Merchant extends User {

    private String name;

    private String description;

    private String email;

    private String status;

    @OneToMany
    private List<Transaction> transactions;
}
