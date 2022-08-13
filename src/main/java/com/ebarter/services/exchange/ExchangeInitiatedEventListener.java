package com.ebarter.services.exchange;

import com.ebarter.services.notifications.Event;
import com.ebarter.services.notifications.EventListener;
import org.springframework.stereotype.Component;

@Component
public class ExchangeInitiatedEventListener implements EventListener {


    @org.springframework.context.event.EventListener(ExchangeInitiatedEvent.class)
    @Override
    public void handleEvent(Event event) {
        ExchangeInitiatedEvent newRequestEvent = (ExchangeInitiatedEvent) event;
        sendNotifications();
    }

    private void sendNotifications() {

    }
}
