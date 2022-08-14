package com.ebarter.services.ratings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RatingRepository<T> extends JpaRepository<T, Long> {

}
