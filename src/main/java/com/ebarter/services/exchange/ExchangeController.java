package com.ebarter.services.exchange;

import com.ebarter.services.util.ServiceConstants;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {

    @PostMapping(path="init-exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE,
                 headers = {ServiceConstants.USER_ID_HEADER_KEY}
                )
    public void initiateExchange() {

    }
}
