package com.ebarter.services.user;

import com.ebarter.services.notifications.Event;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.ApplicationEvent;

@Data
@AllArgsConstructor
public class UserRegistrationSuccessEvent implements Event {

    private long userId;
    private String email;
}
