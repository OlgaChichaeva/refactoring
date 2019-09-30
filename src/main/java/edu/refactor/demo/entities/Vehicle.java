package edu.refactor.demo.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
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

    @NotNull
    @Size(min = 2, max = 30)
    @Column
    private String title;

    @NotNull
    @Column
    private double price; //todo Money

    @NotNull
    @Column
    private String status; //todo Enum

    @NotNull
    @Column
    private String type; //todo Enum

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VehicleRental> rentals = new ArrayList<>();

    @NotNull
    @Size(min = 2, max = 10)
    @Column
    private String serialNumber;
}
