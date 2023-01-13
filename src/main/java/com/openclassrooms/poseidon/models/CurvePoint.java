package com.openclassrooms.poseidon.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.sql.Timestamp;

@Entity
@Table(name = "curvepoint")
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Integer id;

    @NotNull(message = "Curve point Id is mandatory")
    @Positive(message = "Curve point Id must be greater than zero")
    @Column(name = "curve_id")
    private Integer curveId;

    @Column(name = "as_of_date")
    private Timestamp asOfDate;

    @NotNull(message = "Term is mandatory")
    @Positive(message = "Term must be greater than zero")
    @Column(name = "term")
    private Double term;

    @NotNull(message = "Value is mandatory")
    @Positive(message = "Value must be greater than zero")
    @Column(name = "value")
    private Double value;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        if (asOfDate != null) {
            return new Timestamp(asOfDate.getTime());
        } else {
            return null;
        }
    }

    public void setAsOfDate(Timestamp asOfDate) {
        if (asOfDate != null) {
            this.asOfDate = new Timestamp(asOfDate.getTime());
        } else {
            this.asOfDate = null;
        }

    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        if (creationDate != null) {
            return new Timestamp(creationDate.getTime());
        } else {
            return null;
        }
    }

    public void setCreationDate(Timestamp creationDate) {
        if (creationDate != null) {
            this.creationDate = new Timestamp(creationDate.getTime());
        } else {
            this.creationDate = null;
        }
    }
}
