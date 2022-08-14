package com.ebarter.services.user;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component("verifiedAccess")
public class VerifiedAccess {

    public boolean isVerifiedUser(Object principal) {
        User user = (User) principal;
        return user.isVerified();
    }
}
