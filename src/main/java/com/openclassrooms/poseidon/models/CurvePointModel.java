package com.openclassrooms.poseidon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.sql.Timestamp;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "curvepoint")
public class CurvePointModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @Positive(message = "Curve point Id must be greater than zero")
    @NotNull(message = "Curve point Id is mandatory")
    @Column(name = "curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @PositiveOrZero(message = "Term should be a decimal number and greater than zero")
    @Column(name = "term")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @PositiveOrZero(message = "Value should be a decimal number and greater than zero")
    @Column(name = "value")
    private Double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

}
