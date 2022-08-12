package com.ebarter.services.notifications;


import org.springframework.context.ApplicationEventPublisher;

public interface EventPublisher {

    public void publishEvent(Event event);
}
