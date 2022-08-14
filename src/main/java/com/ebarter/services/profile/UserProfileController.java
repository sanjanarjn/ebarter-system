package com.ebarter.services.profile;

import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user-profile")
public class UserProfileController {

    @Autowired
    private UserProfileService profileService;

    @PostMapping
    public ResponseEntity<UserProfileDto> registerUser(@RequestBody UserProfileDto profileDto,
                                                       @RequestParam("userId") long userId) throws ServiceException {
        return new ResponseEntity<>(profileService.addProfile(userId, profileDto), HttpStatus.CREATED);
    }
}
