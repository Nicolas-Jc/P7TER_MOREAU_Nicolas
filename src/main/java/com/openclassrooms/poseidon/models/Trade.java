package com.openclassrooms.poseidon.models;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.sql.Timestamp;


@Entity
@Table(name = "trade")
public class Trade {
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
    @Positive(message = "Buy Quantity must be greater than zero")
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

    public Integer getTradeId() {
        return tradeId;
    }

    public void setTradeId(Integer tradeId) {
        this.tradeId = tradeId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getBuyQuantity() {
        return buyQuantity;
    }

    public void setBuyQuantity(Double buyQuantity) {
        this.buyQuantity = buyQuantity;
    }

    public Double getSellQuantity() {
        return sellQuantity;
    }

    public void setSellQuantity(Double sellQuantity) {
        this.sellQuantity = sellQuantity;
    }

    public Double getBuyPrice() {
        return buyPrice;
    }

    public void setBuyPrice(Double buyPrice) {
        this.buyPrice = buyPrice;
    }

    public Double getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(Double sellPrice) {
        this.sellPrice = sellPrice;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getTradeDate() {
        if (tradeDate != null) {
            return new Timestamp(tradeDate.getTime());
        } else {
            return null;
        }
    }

    public void setTradeDate(Timestamp tradeDate) {
        if (tradeDate != null) {
            this.tradeDate = new Timestamp(tradeDate.getTime());
        } else {
            this.tradeDate = null;
        }
    }

    public String getSecurity() {
        return security;
    }

    public void setSecurity(String security) {
        this.security = security;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTrader() {
        return trader;
    }

    public void setTrader(String trader) {
        this.trader = trader;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getCreationName() {
        return creationName;
    }

    public void setCreationName(String creationName) {
        this.creationName = creationName;
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

    public String getRevisionName() {
        return revisionName;
    }

    public void setRevisionName(String revisionName) {
        this.revisionName = revisionName;
    }

    public Timestamp getRevisionDate() {
        if (revisionDate != null) {
            return new Timestamp(revisionDate.getTime());
        } else {
            return null;
        }
    }

    public void setRevisionDate(Timestamp revisionDate) {
        if (revisionDate != null) {
            this.revisionDate = new Timestamp(revisionDate.getTime());
        } else {
            this.revisionDate = null;
        }
    }

    public String getDealName() {
        return dealName;
    }

    public void setDealName(String dealName) {
        this.dealName = dealName;
    }

    public String getDealType() {
        return dealType;
    }

    public void setDealType(String dealType) {
        this.dealType = dealType;
    }

    public String getSourceListId() {
        return sourceListId;
    }

    public void setSourceListId(String sourceListId) {
        this.sourceListId = sourceListId;
    }

    public String getSide() {
        return side;
    }

    public void setSide(String side) {
        this.side = side;
    }
}
