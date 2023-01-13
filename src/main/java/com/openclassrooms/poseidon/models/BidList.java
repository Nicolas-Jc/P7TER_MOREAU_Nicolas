package com.openclassrooms.poseidon.models;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;
import java.sql.Timestamp;

@Entity
@Table(name = "bidlist")
public class BidList {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "bid_list_id")
    private Integer bidListId;

    @Column(name = "account")
    @NotBlank(message = "Account is mandatory")
    @Size(max = 30)
    private String account;

    @Column(name = "type")
    @NotBlank(message = "Type is mandatory")
    @Size(max = 30)
    private String type;

    @Column(name = "bid_quantity")
    @NotNull(message = "Bid Quantity is mandatory")
    @Positive(message = "Bid Quantity must be greater than zero")
    private Double bidQuantity;

    @Column(name = "ask_quantity")
    private Double askQuantity;

    @Column(name = "bid")
    private Double bid;

    @Column(name = "ask")
    private Double ask;

    @Size(max = 125)
    @Column(name = "benchmark")
    private String benchmark;

    @Column(name = "bid_list_date")
    private Timestamp bidListDate;

    @Size(max = 125)
    @Column(name = "commentary")
    private String commentary;

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
    @Column(name = "source_listid")
    private String sourceListId;

    @Size(max = 125)
    @Column(name = "side")
    private String side;

    public Integer getBidListId() {
        return bidListId;
    }

    public void setBidListId(Integer bidListId) {
        this.bidListId = bidListId;
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

    public Double getBidQuantity() {
        return bidQuantity;
    }

    public void setBidQuantity(Double bidQuantity) {
        this.bidQuantity = bidQuantity;
    }

    public Double getAskQuantity() {
        return askQuantity;
    }

    public void setAskQuantity(Double askQuantity) {
        this.askQuantity = askQuantity;
    }

    public Double getBid() {
        return bid;
    }

    public void setBid(Double bid) {
        this.bid = bid;
    }

    public Double getAsk() {
        return ask;
    }

    public void setAsk(Double ask) {
        this.ask = ask;
    }

    public String getBenchmark() {
        return benchmark;
    }

    public void setBenchmark(String benchmark) {
        this.benchmark = benchmark;
    }

    public Timestamp getBidListDate() {
        if (bidListDate != null) {
            return new Timestamp(bidListDate.getTime());
        } else {
            return null;
        }
    }

    public void setBidListDate(Timestamp bidListDate) {
        if (bidListDate != null) {
            this.bidListDate = new Timestamp(bidListDate.getTime());
        } else {
            this.bidListDate = null;
        }
    }

    public String getCommentary() {
        return commentary;
    }

    public void setCommentary(String commentary) {
        this.commentary = commentary;
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
