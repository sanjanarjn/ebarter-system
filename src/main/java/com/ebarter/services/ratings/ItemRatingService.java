package com.ebarter.services.ratings;

import org.springframework.stereotype.Service;

@Service
public class ItemRatingService extends RatingService<ItemRating, ItemRatingRepository> {

    public ItemRatingService() {
        this.entityClass = ItemRating.class;
    }
}
