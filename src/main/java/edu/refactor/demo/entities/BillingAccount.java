package edu.refactor.demo.entities;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
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
    private Money money;

    @Column
    private boolean isPrimary = true;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "billing_account_id")
    private Customer customer;

    @NotNull
    @Column
    private Instant createdDate;
}
