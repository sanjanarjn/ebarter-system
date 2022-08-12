package com.ebarter.services.exchange;

import com.ebarter.services.item.Item;
import com.ebarter.services.user.Principal;
import com.ebarter.services.user.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class ExchangeRequestService {

    @Autowired
    private ExchangeRequestRepository exchangeRequestRepository;

    @Autowired
    private ExchangeTransactionService transactionService;

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void initiateExchange(Principal userPrincipal, ExchangeRequestDto exchangeRequestDto) {

        User user = userPrincipal.getUser();
        ExchangeRequest exchangeRequest = createExchangeRequest(user, exchangeRequestDto);
        exchangeRequest = exchangeRequestRepository.save(exchangeRequest);

        ExchangeTransaction transaction = createExchangeTransaction(exchangeRequestDto, exchangeRequestDto.getRequestedItem());
        transaction.setRequest(exchangeRequest);

        transactionService.saveTransaction(transaction);


    }

    private ExchangeRequest createExchangeRequest(User user, ExchangeRequestDto exchangeRequestDto) {

        ExchangeRequest request = new ExchangeRequest();
        Item item = exchangeRequestDto.getRequestedItem();
        request.setCreatedTime(new Date());
        request.setModifiedTime(new Date());

        return request;
    }

    private ExchangeTransaction createExchangeTransaction(ExchangeRequestDto exchangeRequestDto, Item item) {
        ExchangeTransaction transaction = new ExchangeTransaction();
        transaction.setBorrower(exchangeRequestDto.getInitiatedUserId());
        transaction.setLender(item.getOwnerId());
        transaction.setStatus(ExchangeTransactionStatus.REQUESTED);
        transaction.setCreatedTime(new Date());
        transaction.setModifiedTime(new Date());
        return transaction;
    }
}
