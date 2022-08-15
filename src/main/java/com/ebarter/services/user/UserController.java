package com.ebarter.services.user;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.follow.FollowService;
import com.ebarter.services.notifications.EventPublisher;
import com.ebarter.services.user.iam.AuthResponse;
import com.ebarter.services.user.iam.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final static Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @Autowired
    private FollowService followService;

    @Autowired
    private EventPublisher eventPublisher;

    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody UserDTO userDTO) throws ServiceException {
        logger.info("Registering user : ", userDTO.getEmail());
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws ServiceException {
        logger.info("Authenticating user : ", userDTO.getEmail());
        return new ResponseEntity<>(userService.authenticateUsr(userDTO), HttpStatus.OK);
    }

    @PostMapping(path = "/follow/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void followUser(@AuthenticationPrincipal User user, @PathVariable("id") long followerId) {
        followService.followUser(user, followerId);
        logger.info("User {} followed -> User {} : ", user.getId(), followerId);
    }
}
