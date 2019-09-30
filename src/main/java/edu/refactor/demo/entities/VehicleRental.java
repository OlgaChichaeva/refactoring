package edu.refactor.demo.entities;

import edu.refactor.demo.projecttypes.VehicleRentalStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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
    public Long id;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    public Vehicle vehicle;

    @ManyToOne
    @JoinColumn(name = "customer_id")
    public Customer customer;

    @Column
    public Instant startRent;

    @Column
    public Instant endRent;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    public VehicleRentalStatus status; //todo Enum
}
