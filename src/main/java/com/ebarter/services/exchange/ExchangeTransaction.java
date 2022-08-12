package com.ebarter.services.exchange;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ExchangeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name="request_id", nullable=false)
    private ExchangeRequest request;
    private long lender;
    private long borrower;
    private long itemId;

    private ExchangeTransactionStatus status;

    private Date createdTime;
    private Date modifiedTime;
}
