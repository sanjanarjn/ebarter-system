package com.ebarter.services.follow;

import com.ebarter.services.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

@Service
public class FollowService {

    @Autowired
    private  FollowRepository repository;

    public void followUser(User user, long followerId) {
        FollowDetail followDetail = new FollowDetail();
        followDetail.setFollower(followerId);
        followDetail.setFollowee(user.getId());
        repository.save(followDetail);
    }
}
