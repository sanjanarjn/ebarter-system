package com.ebarter.services.ratings;

import org.springframework.stereotype.Service;

@Service
public class UserRatingService extends RatingService<UserRating, UserRatingRepository> {

    public UserRatingService() {
        this.entityClass = UserRating.class;
    }
}
