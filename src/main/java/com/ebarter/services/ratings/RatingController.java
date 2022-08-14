package com.ebarter.services.ratings;

import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

public class RatingController<E extends Rating, S extends RatingService> {

    @Autowired
    S ratingService;

    @PostMapping
    public ResponseEntity<RatingDto> addRating(@AuthenticationPrincipal User user,
                                               @RequestParam long itemId,
                                               @RequestParam int rating) {
        return new ResponseEntity<>(ratingService.store(user, itemId, rating), HttpStatus.CREATED);
    }

    @PatchMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateRating(@AuthenticationPrincipal User user, @PathVariable("id") long id, @RequestParam int rating) throws ServiceException {
        ratingService.update(user, id, rating);
    }

    @DeleteMapping(path = "/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void addRating(@AuthenticationPrincipal User user, @PathVariable("id") long id) throws ServiceException {
        ratingService.delete(user, id);
    }
}
