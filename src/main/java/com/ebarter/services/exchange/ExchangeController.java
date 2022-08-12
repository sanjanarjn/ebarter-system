package com.ebarter.services.exchange;

import com.ebarter.services.user.Principal;
import com.ebarter.services.user.iam.IamService;
import com.ebarter.services.util.ServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ExchangeController {


    @Autowired
    private IamService iamService;

    @Autowired
    private ExchangeRequestService exchangeService;

    @PostMapping(path="/init-exchange", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void initiateExchange(@RequestBody ExchangeRequestDto exchangeRequestDto) {
        Principal principal = iamService.getUserPrincipal();
        exchangeService.initiateExchange(principal, exchangeRequestDto);
    }
}
