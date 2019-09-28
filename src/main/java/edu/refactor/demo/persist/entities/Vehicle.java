package edu.refactor.demo.persist.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    @Column
    private double price; //todo Money

    @Column
    private String status; //todo Enum

    @Column
    private String type; //todo Enum

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleRental> rentals = new ArrayList<>();

    @Column
    private String serialNumber;
}
