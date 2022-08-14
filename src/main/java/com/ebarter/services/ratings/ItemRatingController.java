package com.ebarter.services.ratings;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/item-rating")
public class ItemRatingController extends RatingController<ItemRating, ItemRatingService> {
}
