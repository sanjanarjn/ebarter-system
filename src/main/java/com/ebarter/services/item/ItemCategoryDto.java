package com.ebarter.services.item;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ItemCategoryDto {

    private long id;
    private String name;
}
