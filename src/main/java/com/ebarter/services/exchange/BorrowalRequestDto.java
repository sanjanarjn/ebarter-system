package com.ebarter.services.exchange;

import com.ebarter.services.item.Item;
import lombok.Data;

import java.util.Date;

@Data
public class BorrowalRequestDto {

    private Item requestedItem;
    private long initiatedUserId;
}
