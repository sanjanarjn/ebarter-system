package com.ebarter.services.exchange;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangeTransactionDto {

    private long id;
    private long lender;
    private long borrower;
    private long itemId;

    private ExchangeTransactionStatus status;
    private Date createdTime;
    private Date modifiedTime;
}
