package com.ebarter.services.profile;

import com.ebarter.services.user.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserProfileService {

    @Autowired
    private UserService userService;

    @Autowired
    private UserProfileRepository userProfileRepository;

    @Autowired
    private ModelMapper mapper;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void incrementUserRewardPoints(long userId, int points) {
        UserProfile userProfile = userProfileRepository.findByUserId(userId);
        userProfile.setPoints(userProfile.getPoints() + points);
        userProfileRepository.save(userProfile);
    }

    public UserProfileDto addProfile(long userId, UserProfileDto profileDto) {
        UserProfile userProfile = mapper.map(profileDto, UserProfile.class);
        userProfile.setUser(userService.getUserById(userId));
        userProfile = saveProfile(userProfile);
        return mapper.map(userProfile, UserProfileDto.class);
    }

    public UserProfile saveProfile(UserProfile userProfile) {
        userProfile = userProfileRepository.save(userProfile);
        return userProfile;
    }
}
