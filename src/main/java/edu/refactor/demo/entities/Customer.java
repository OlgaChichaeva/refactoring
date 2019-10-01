package edu.refactor.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.refactor.demo.projecttypes.CustomerCategory;
import edu.refactor.demo.projecttypes.CustomerStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min = 2, max = 20)
    @Column
    private String login;

    @NotNull
    @Size(min = 2, max = 20)
    @Column
    private String email;

    @NotNull
    @Column
    private Instant registration;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CustomerStatus status;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleRental> rentals = new ArrayList<>();

    @NotNull
    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    private CustomerCategory category;


    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BillingAccount> billingAccounts;
}
