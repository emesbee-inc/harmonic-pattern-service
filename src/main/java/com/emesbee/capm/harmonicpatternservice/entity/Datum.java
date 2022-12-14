package com.emesbee.capm.harmonicpatternservice.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Datum {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    private String patterntype;
    private String patternname;
    private Double profit1;
    private String displaySymbol;
    private String symbol;
    private Double profit2;
    private Double stoploss;
    private String url;
    private String timeframe;
    private String status;
    private String entry;
    private String patternclass;
}
