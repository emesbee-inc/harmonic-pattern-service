package com.emesbee.capm.harmonicpatternservice.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;


import javax.persistence.Column;
import javax.persistence.TemporalType;
import javax.persistence.Temporal;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;
import java.util.UUID;

@Data
@Entity
public class PatternNotification {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id")
    private UUID id;
    @Column(name = "pattern_type")
    private String patterntype;
    @Column(name = "pattern_name")
    private String patternname;
    @Column(name = "profit1")
    private Double profit1;
    @Column(name = "display_symbol")
    private String displaySymbol;
    @Column(name = "symbol")
    private String symbol;
    @Column(name = "profit2")
    private Double profit2;
    @Column(name = "stop_loss")
    private Double stoploss;
    @Column(name = "url")
    private String url;
    @Column(name = "time_frame")
    private String timeframe;
    @Column(name = "status")
    private String status;
    @Column(name = "entry")
    private String entry;
    @Column(name = "pattern_class")
    private String patternclass;

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createdAt;
}
