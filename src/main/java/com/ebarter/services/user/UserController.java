package com.ebarter.services.user;

import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.models.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(path = "/register-user")
    public ResponseEntity<Boolean> registerUser(@RequestBody UserDTO userDTO) throws ServiceException {
        boolean registrationSuccess = userService.registerUser(userDTO);
        return new ResponseEntity<>(registrationSuccess, HttpStatus.CREATED);
    }
}
