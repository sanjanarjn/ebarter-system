package com.ebarter.services.exchange;

import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @PostMapping(path="/init", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void initiateExchange(@AuthenticationPrincipal User user, @RequestBody BorrowalRequestDto borrowalRequestDto) {
        exchangeService.initiateExchange(user, borrowalRequestDto);
    }

    @PatchMapping(path="/{exchange-id}/respond", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public void approveExchange(@AuthenticationPrincipal User user,
                                @PathVariable("exchange-id") long exchangeId,
                                @RequestBody Optional<BorrowalRequestDto> borrowalRequestDto) throws ServiceException {

        exchangeService.approveExchange(user, exchangeId, borrowalRequestDto);
    }
}
