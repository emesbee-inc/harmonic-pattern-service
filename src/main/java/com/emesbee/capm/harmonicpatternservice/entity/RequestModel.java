package com.emesbee.capm.harmonicpatternservice.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@Generated
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "message")
public class RequestModel {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    private UUID id;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Datum> data;
}
