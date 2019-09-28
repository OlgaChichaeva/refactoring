package edu.refactor.demo.persist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private String status; //todo Enum

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleRental> rentals = new ArrayList<>();

    @Column
    private String category; //todo Enum or entity

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    public List<BillingAccount> billingAccounts;
}
