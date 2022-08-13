package com.ebarter.services.item;

import com.ebarter.services.BaseEntity;
import lombok.Data;

import javax.persistence.Entity;

@Entity
@Data
public class ItemCategory extends BaseEntity {

    private String name;
}
