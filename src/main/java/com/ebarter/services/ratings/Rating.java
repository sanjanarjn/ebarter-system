package com.ebarter.services.ratings;

import com.ebarter.services.BaseEntity;
import lombok.Data;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
@Data
public class Rating extends BaseEntity {

    private long entityId;

    private long userId;

    private int rating;
}
