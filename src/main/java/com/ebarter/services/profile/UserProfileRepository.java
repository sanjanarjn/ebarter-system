package com.ebarter.services.profile;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserProfileRepository extends JpaRepository<UserProfile, Long> {
    public UserProfile findByUserId(long userId);
}
