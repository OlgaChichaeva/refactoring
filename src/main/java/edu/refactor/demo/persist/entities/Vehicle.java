package edu.refactor.demo.persist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.refactor.demo.projecttypes.VehicleStatus;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity
public class Vehicle implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column
    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "PRICE_ID")
    private Money price; //todo Money

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private VehicleStatus status; //todo Enum

    @Column
    private String type;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleRental> rentals = new ArrayList<>();

    @Column
    private String serialNumber;
}
