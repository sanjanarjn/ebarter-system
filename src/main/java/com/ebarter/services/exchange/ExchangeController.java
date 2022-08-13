package com.ebarter.services.exchange;

import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.Principal;
import com.ebarter.services.user.iam.IamService;
import com.ebarter.services.util.ServiceConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {


    @Autowired
    private IamService iamService;

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping(path="/init", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void initiateExchange(@RequestBody BorrowalRequestDto borrowalRequestDto) {
        Principal principal = iamService.getUserPrincipal();
        exchangeService.initiateExchange(principal, borrowalRequestDto);
    }

    @PatchMapping(path="/{exchange-id}/respond", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void approveExchange(@PathVariable("exchange-id") long exchangeId,
                                @RequestBody Optional<BorrowalRequestDto> borrowalRequestDto) throws ServiceException {
        Principal principal = iamService.getUserPrincipal();
        exchangeService.approveExchange(exchangeId, borrowalRequestDto, principal);
    }
}
