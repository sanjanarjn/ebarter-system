package com.ebarter.services.user;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.notifications.EventPublisher;
import com.ebarter.services.user.iam.AuthResponse;
import com.ebarter.services.user.iam.JwtTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private EventPublisher eventPublisher;

    @PostMapping(path = "/register")
    public ResponseEntity<Boolean> registerUser(@RequestBody UserDTO userDTO) throws ServiceException {
        return new ResponseEntity<>(userService.registerUser(userDTO), HttpStatus.CREATED);
    }

    @PostMapping(path = "/login")
    public ResponseEntity<?> loginUser(@RequestBody UserDTO userDTO) throws ServiceException {
        return new ResponseEntity<>(userService.authenticateUsr(userDTO), HttpStatus.OK);
    }
}
