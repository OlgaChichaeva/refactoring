package edu.refactor.demo.entities;

import edu.refactor.demo.projecttypes.VehicleRentalStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import java.io.Serializable;
import java.time.Instant;

@Getter
@Setter
@Entity
public class VehicleRental implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @NotNull
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @Column
    private Instant startRent;

    @Column
    private Instant endRent;

    @NotNull
    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public VehicleRentalStatus status;
}
