package com.ebarter.services.ratings;

import lombok.Data;

import java.util.Date;

@Data
public class RatingDto {

    private long id;
    private long entityId;
    private long userId;
    private int rating;

    private Date createdTime;
    private Date modifiedTime;

}
