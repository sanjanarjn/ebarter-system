package com.ebarter.services.ratings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user-rating")
public class UserRatingController extends RatingController<UserRating, UserRatingService> {
}
