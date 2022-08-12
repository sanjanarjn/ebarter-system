package com.ebarter.services.user;

import com.ebarter.services.notifications.Event;
import com.ebarter.services.notifications.EventListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserRegistrationSuccessEventListener implements EventListener {

    @org.springframework.context.event.EventListener(UserRegistrationSuccessEvent.class)
    @Override
    public void handleEvent(Event event) {
        UserRegistrationSuccessEvent registrationSuccessEvent = (UserRegistrationSuccessEvent) event;

    }
}
