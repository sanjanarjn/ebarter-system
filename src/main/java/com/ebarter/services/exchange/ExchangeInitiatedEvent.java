package com.ebarter.services.exchange;

import com.ebarter.services.notifications.Event;

public class ExchangeInitiatedEvent implements Event {

    private Exchange request;
    public ExchangeInitiatedEvent() {
        this.request = request;
    }
}
