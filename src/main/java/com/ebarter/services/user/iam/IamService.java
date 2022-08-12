package com.ebarter.services.user.iam;

import com.ebarter.services.user.Principal;
import org.springframework.stereotype.Service;

@Service
public class IamService {

    public Principal getUserPrincipal() {
        Principal principal = new Principal();
        return principal;
    }
}
