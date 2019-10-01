package edu.refactor.demo.entities;

import edu.refactor.demo.projecttypes.Currency;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@Entity
public class Money implements Serializable {

    @Id
    @GeneratedValue
    @Column
    private Long id;

    @Column(name = "count")
    private BigDecimal count;

    @Column(name = "currency")
    @Enumerated(EnumType.STRING)
    private Currency currency;
}
