package com.ebarter.services.ratings;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.User;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.Optional;

public class RatingService<E extends Rating, R extends RatingRepository<E>> {

    @Autowired
    private R repository;

    @Autowired
    private ModelMapper modelMapper;

    protected Class<E> entityClass;

    public RatingDto store(User user, long entityId, int value) {
        RatingDto rating = new RatingDto();
        rating.setRating(value);
        rating.setUserId(user.getId());
        rating.setEntityId(entityId);
        E savedRating = repository.save(modelMapper.map(rating, entityClass));
        return modelMapper.map(savedRating, RatingDto.class);
    }

    @Transactional
    public void update(User user, long ratingId, int newRating) throws ServiceException {
        Optional<E> ratingOptional = repository.findById(ratingId);
        if (ratingOptional.isPresent()) {
            E rating = ratingOptional.get();
            if(rating.getUserId() != user.getId())
                throw new ServiceException(MessageFormat.format(ExceptionMessages.CANNOT_DELETE_OTHER_RATING, ratingId));

            rating.setRating(newRating);
            repository.save(rating);
        }
        else throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, ratingId));
    }

    public void delete(User user, long ratingId) throws ServiceException {
        Optional<E> ratingOptional = repository.findById(ratingId);
        if (ratingOptional.isPresent()) {
            E rating = ratingOptional.get();
            if(rating.getUserId() != user.getId())
                throw new ServiceException(MessageFormat.format(ExceptionMessages.CANNOT_DELETE_OTHER_RATING, ratingId));

            repository.deleteById(ratingId);
        }
        else throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, ratingId));

    }
}
