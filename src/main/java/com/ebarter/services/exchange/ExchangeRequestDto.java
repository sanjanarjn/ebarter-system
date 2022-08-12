package com.ebarter.services.exchange;

import lombok.Data;

import java.util.Date;

@Data
public class ExchangeRequestDto {

    private long id;

    private long initiatedUserId;
    private Date createdTimestamp;
    private Date modifiedTimestamp;
}
