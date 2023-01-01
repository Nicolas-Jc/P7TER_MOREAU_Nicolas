package com.openclassrooms.poseidon.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.sql.Timestamp;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "trade")
public class TradeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "trade_id")
    private Integer tradeId;

    @NotBlank(message = "Account is mandatory")
    @Size(max = 30)
    @Column(name = "account")
    private String account;

    @Size(max = 30)
    @NotBlank(message = "Type is mandatory")
    @Column(name = "type")
    private String type;

    @NotNull(message = "By Quantity is mandatory")
    @PositiveOrZero(message = "Buy Quantity must be greater than or equal to zero")
    @Column(name = "buy_quantity")
    private Double buyQuantity;

    @Column(name = "sell_quantity")
    private Double sellQuantity;

    @Column(name = "buy_price")
    private Double buyPrice;

    @Column(name = "sell_price")
    private Double sellPrice;

    @Size(max = 125)
    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "trade_date")
    private Timestamp tradeDate;

    @Size(max = 125)
    @Column(name = "security")
    private String security;

    @Size(max = 10)
    @Column(name = "status")
    private String status;

    @Size(max = 125)
    @Column(name = "trader")
    private String trader;

    @Size(max = 125)
    @Column(name = "book")
    private String book;

    @Size(max = 125)
    @Column(name = "creation_name")
    private String creationName;

    @Column(name = "creation_date")
    private Timestamp creationDate;

    @Size(max = 125)
    @Column(name = "revision_name")
    private String revisionName;

    @Column(name = "revision_date")
    private Timestamp revisionDate;

    @Size(max = 125)
    @Column(name = "deal_name")
    private String dealName;

    @Size(max = 125)
    @Column(name = "deal_type")
    private String dealType;

    @Size(max = 125)
    @Column(name = "source_list_id")
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side")
    private String side;

}
