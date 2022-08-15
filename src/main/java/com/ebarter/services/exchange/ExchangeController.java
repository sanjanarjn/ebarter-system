package com.ebarter.services.exchange;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.user.User;
import com.ebarter.services.user.UserService;
import com.ebarter.services.user.VerifiedAccess;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
@RestController
@RequestMapping("/exchange")
public class ExchangeController {

    @Autowired
    private ExchangeService exchangeService;

    @Autowired
    private VerifiedAccess verifiedAccess;

    private final static Logger logger = LoggerFactory.getLogger(ExchangeController.class);


    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getExchange(@AuthenticationPrincipal User user, @PathVariable("id") long id) throws ServiceException {
        if(!user.isVerified())
            throw new ServiceException(ExceptionMessages.USER_NOT_VERIFIED);
        return new ResponseEntity<>(exchangeService.getExchangeDto(id), HttpStatus.OK);
    }

    @PostMapping(path="/init", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> initiateExchange(@AuthenticationPrincipal User user, @RequestBody BorrowalRequestDto borrowalRequestDto) throws ServiceException {
        if(!user.isVerified())
            throw new ServiceException(ExceptionMessages.USER_NOT_VERIFIED);
        logger.info("User - {} initiated exchange request - {}", user.getEmail(), borrowalRequestDto);
        return new ResponseEntity<>(exchangeService.initiateExchange(user, borrowalRequestDto), HttpStatus.CREATED);
    }

    @PatchMapping(path="/{exchange-id}/approve", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void approveExchange(@AuthenticationPrincipal User user,
                                @PathVariable("exchange-id") long exchangeId,
                                @RequestBody Optional<BorrowalRequestDto> borrowalRequestDto) throws ServiceException {
        if(!user.isVerified())
            throw new ServiceException(ExceptionMessages.USER_NOT_VERIFIED);
        exchangeService.approveExchange(user, exchangeId, borrowalRequestDto);
    }

    @PatchMapping(path="/{exchange-id}/settle", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void completeTransaction(@AuthenticationPrincipal User user,
                                    @PathVariable("exchange-id") long exchangeId) throws ServiceException {
        if(!user.isVerified())
            throw new ServiceException(ExceptionMessages.USER_NOT_VERIFIED);
        exchangeService.completeExchange(user, exchangeId);
    }
}
