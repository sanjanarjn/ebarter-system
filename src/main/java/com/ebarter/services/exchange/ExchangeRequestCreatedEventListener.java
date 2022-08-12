package com.ebarter.services.exchange;

import com.ebarter.services.notifications.Event;
import com.ebarter.services.notifications.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ExchangeRequestCreatedEventListener implements EventListener {


    @org.springframework.context.event.EventListener(ExchangeRequestCreatedEvent.class)
    @Override
    public void handleEvent(Event event) {
        ExchangeRequestCreatedEvent newRequestEvent = (ExchangeRequestCreatedEvent) event;
        sendNotifications();
    }

    private void sendNotifications() {

    }
}
