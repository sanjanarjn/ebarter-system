package com.ebarter.services.exchange;

import com.ebarter.services.item.Item;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ExchangeRequestDto {

    private long id;

    private Item requestedItem;
    private long initiatedUserId;
    private Date createdTimestamp;
    private Date modifiedTimestamp;
}
