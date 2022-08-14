package com.ebarter.services.exchange;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExchangeDto {

    private long id;
    private long initiatedUserId;
    private long fellowUserId;
    private ExchangeStatus status;
    private List<ExchangeTransactionDto> transactions;
    private Date createdTime;
    private Date modifiedTime;
}
