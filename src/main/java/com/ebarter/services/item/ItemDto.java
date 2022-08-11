package com.ebarter.services.item;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
public class ItemDto {

    private long id;
    private String category;
    private String title;
    private long ownerId;

    private JsonNode details;
}
