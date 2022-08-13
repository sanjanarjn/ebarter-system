package com.ebarter.services.exchange;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.item.Item;
import com.ebarter.services.item.ItemAvailabilityStatus;
import com.ebarter.services.item.ItemService;
import com.ebarter.services.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.text.MessageFormat;
import java.util.*;

@Service
public class ExchangeService {

    @Autowired
    private ExchangeRepository exchangeRepository;

    @Autowired
    private ExchangeTransactionService transactionService;

    @Autowired
    private ItemService itemService;

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public void initiateExchange(User user, BorrowalRequestDto borrowalRequestDto) {

        Exchange exchange = createExchangeRequest(user, borrowalRequestDto);
        exchange = exchangeRepository.save(exchange);

        ExchangeTransaction transaction = createExchangeTransaction(borrowalRequestDto);
        transaction.setExchange(exchange);

        transactionService.saveTransaction(transaction);
    }

    private Exchange createExchangeRequest(User user, BorrowalRequestDto borrowalRequestDto) {

        Exchange exchange = new Exchange();
        exchange.setInitiatedUserId(borrowalRequestDto.getInitiatedUserId());
        return exchange;
    }

    private ExchangeTransaction createExchangeTransaction(BorrowalRequestDto borrowalRequestDto) {
        ExchangeTransaction transaction = new ExchangeTransaction();
        transaction.setBorrower(borrowalRequestDto.getInitiatedUserId());
        Item requestedItem = borrowalRequestDto.getRequestedItem();
        transaction.setLender(requestedItem.getOwnerId());
        transaction.setItemId(requestedItem.getId());
        transaction.setStatus(ExchangeTransactionStatus.REQUESTED);
        transaction.setCreatedTime(new Date());
        transaction.setModifiedTime(new Date());
        return transaction;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void approveExchange(User user, long exchangeId, Optional<BorrowalRequestDto> borrowalRequestDto) throws ServiceException {
        Optional<Exchange> exchangeOptional = exchangeRepository.findById(exchangeId);
        if(exchangeOptional.isPresent()) {
            Exchange exchange = exchangeOptional.get();

            long approverId = 1;
            ExchangeTransaction forwardTransaction = transactionService.getTransaction(exchangeId, exchange.getInitiatedUserId(), approverId);
            forwardTransaction.setStatus(ExchangeTransactionStatus.APPROVED);
            transactionService.saveTransaction(forwardTransaction);

            List<Long> itemIds = new ArrayList<>();
            itemIds.add(forwardTransaction.getItemId());

            if(borrowalRequestDto.isPresent()) {
                ExchangeTransaction reverseTransaction = createExchangeTransaction(borrowalRequestDto.get());
                reverseTransaction.setExchange(exchange);
                reverseTransaction.setStatus(ExchangeTransactionStatus.APPROVED);
                transactionService.saveTransaction(reverseTransaction);

                itemIds.add(reverseTransaction.getItemId());
            }
            itemService.updateItemStatus(itemIds, ItemAvailabilityStatus.NOT_AVAILABLE);
        }
        else throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, exchangeId));
    }
}
