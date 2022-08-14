package com.ebarter.services.user;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.notifications.EventPublisher;
import com.ebarter.services.profile.UserProfile;
import com.ebarter.services.profile.UserProfileDto;
import com.ebarter.services.profile.UserProfileRepository;
import com.ebarter.services.profile.UserProfileService;
import com.ebarter.services.user.iam.AuthResponse;
import com.ebarter.services.user.iam.JwtTokenService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenService tokenService;

    @Autowired
    private EventPublisher eventPublisher;

    @Autowired
    private UserProfileRepository profileRepository;

    @Transactional
    public boolean registerUser(UserDTO userDTO) throws ServiceException {

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ServiceException(ExceptionMessages.EMAIL_ALREADY_IN_USE);
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setCreatedTime(new Date());
        user.setModifiedTime(new Date());

        if(user.getRole() == null)
            user.setRole(UserRole.REGULAR);
        user = userRepository.save(user);

        UserProfile profile = new UserProfile();
        profile.setId(user.getId());
        profileRepository.save(profile);

        eventPublisher.publishEvent(new UserRegistrationSuccessEvent(user.getId(), user.getEmail()));
        return true;
    }

    public User getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User getUserById(long id) {
        return userRepository.findById(id).get();
    }

    public AuthResponse authenticateUsr(UserDTO userDTO) throws ServiceException {

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userDTO.getEmail(), userDTO.getPassword()));
            User user = getUserByEmail(userDTO.getEmail());
            String token = tokenService.generateToken(user);
            return new AuthResponse(token);
        }
        catch(BadCredentialsException e) {
            throw new ServiceException(ExceptionMessages.INCORRECT_USERNAME_OR_PASSWORD);
        }
    }
}
