package com.ebarter.services.user;

import com.ebarter.services.notifications.Event;
import com.ebarter.services.notifications.EventListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationSuccessEventListener implements EventListener {

    private final static Logger logger = LoggerFactory.getLogger(UserRegistrationSuccessEventListener.class);

    @org.springframework.context.event.EventListener(UserRegistrationSuccessEvent.class)
    @Override
    public void handleEvent(Event event) {
        UserRegistrationSuccessEvent registrationSuccessEvent = (UserRegistrationSuccessEvent) event;
        logger.info("Received event : {}", registrationSuccessEvent);
    }
}
