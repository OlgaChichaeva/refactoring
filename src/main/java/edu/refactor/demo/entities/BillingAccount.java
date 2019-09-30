package edu.refactor.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
public class BillingAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MONEY_ID")
    private Money money;// todo класс money

    @Column
    private boolean isPrimary; // todo что значит isPrimary

    @ManyToOne
    @JoinColumn(name = "billing_account_id")
    private Customer customer;

    @Column
    private Instant createdDate;
}
