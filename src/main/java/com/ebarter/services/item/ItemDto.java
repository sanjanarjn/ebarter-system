package com.ebarter.services.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemDto {

    private long id;
    private ItemCategoryDto category;
    private String title;
    private long ownerId;
    private int points;
    private JsonNode details;
}
