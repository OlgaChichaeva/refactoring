package edu.refactor.demo.persist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.refactor.demo.projecttypes.CustomerCategory;
import edu.refactor.demo.projecttypes.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String login;

    @Column
    private String email;

    @Column
    private Instant registration;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status; //todo Enum

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleRental> rentals = new ArrayList<>();

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CustomerCategory category; //todo Enum or entity

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BillingAccount> billingAccounts;
}
