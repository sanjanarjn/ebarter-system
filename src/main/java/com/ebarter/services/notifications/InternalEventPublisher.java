package com.ebarter.services.notifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class InternalEventPublisher implements EventPublisher {

    @Autowired
    private ApplicationEventPublisher publisher;

    @Override
    public void publishEvent(Event event) {
        publisher.publishEvent(event);
    }
}
