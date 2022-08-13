package com.ebarter.services.user;

import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.notifications.EventPublisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventPublisher eventPublisher;

    @PostMapping(path = "/register-user")
    public ResponseEntity<Boolean> registerUser(@RequestBody UserDTO userDTO) throws ServiceException {
        boolean registrationSuccess = userService.registerUser(userDTO);
        if(registrationSuccess) {
            eventPublisher.publishEvent(new UserRegistrationSuccessEvent(1, userDTO.getEmail()));
        }
        return new ResponseEntity<>(registrationSuccess, HttpStatus.CREATED);
    }
}
