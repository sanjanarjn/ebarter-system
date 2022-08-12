package com.ebarter.services.user;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.notifications.EventPublisher;
import com.ebarter.services.user.models.User;
import com.ebarter.services.user.models.UserDTO;
import com.ebarter.services.user.models.UserRole;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private  UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private EventPublisher eventPublisher;

    public boolean registerUser(UserDTO userDTO) throws ServiceException {

        if(userRepository.existsByEmail(userDTO.getEmail())) {
            throw new ServiceException(ExceptionMessages.EMAIL_ALREADY_IN_USE);
        }
        User user = modelMapper.map(userDTO, User.class);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        if(user.getRole() == null)
            user.setRole(UserRole.REGULAR);
        user = userRepository.save(user);

        eventPublisher.publishEvent(new UserRegistrationSuccessEvent(user.getId(), user.getEmail()));
        return true;
    }
}
