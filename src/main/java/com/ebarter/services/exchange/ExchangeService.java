package com.ebarter.services.exchange;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import com.ebarter.services.item.Item;
import com.ebarter.services.item.ItemAvailabilityStatus;
import com.ebarter.services.item.ItemService;
import com.ebarter.services.profile.UserProfileService;
import com.ebarter.services.user.User;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @Autowired
    private UserProfileService profileService;

    @Autowired
    private ModelMapper modelMapper;

    private final static Logger logger = LoggerFactory.getLogger(ExchangeService.class);

    @Transactional(isolation = Isolation.READ_UNCOMMITTED)
    public ExchangeDto initiateExchange(User user, BorrowalRequestDto borrowalRequestDto) {

        Exchange exchange = new Exchange();
        exchange.setStatus(ExchangeStatus.OPEN);
        exchange.setInitiatedUserId(user.getId());
        exchange.setFellowUserId(borrowalRequestDto.getRequestedItem().getOwnerId());
        exchange = exchangeRepository.save(exchange);

        ExchangeTransaction transaction = createExchangeTransaction(user, borrowalRequestDto);
        transaction.setExchange(exchange);

        logger.debug("Successfully created exchange - {}", exchange);
        logger.info("Created exchange with id - {} between users {} and {}", exchange.getId(), exchange.getInitiatedUserId(), exchange.getFellowUserId());
        transactionService.saveTransaction(transaction);
        return modelMapper.map(exchange, ExchangeDto.class);
    }


    private ExchangeTransaction createExchangeTransaction(User user, BorrowalRequestDto borrowalRequestDto) {
        ExchangeTransaction transaction = new ExchangeTransaction();
        transaction.setBorrower(user.getId());

        Item requestedItem = borrowalRequestDto.getRequestedItem();
        transaction.setLender(requestedItem.getOwnerId());
        transaction.setItemId(requestedItem.getId());
        transaction.setStatus(ExchangeTransactionStatus.REQUESTED);

        return transaction;
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public ExchangeDto approveExchange(User user, long exchangeId, Optional<BorrowalRequestDto> borrowalRequestDto) throws ServiceException {

        Optional<Exchange> exchangeOptional = exchangeRepository.findById(exchangeId);
        if(exchangeOptional.isPresent()) {
            Exchange exchange = exchangeOptional.get();

            long approverId = user.getId();
            ExchangeTransaction forwardTransaction = transactionService.getTransaction(exchangeId, exchange.getInitiatedUserId(), approverId);
            forwardTransaction.setStatus(ExchangeTransactionStatus.APPROVED);
            transactionService.saveTransaction(forwardTransaction);

            List<Long> itemIds = new ArrayList<>();
            itemIds.add(forwardTransaction.getItemId());

            if(borrowalRequestDto.isPresent()) {
                ExchangeTransaction reverseTransaction = createExchangeTransaction(user, borrowalRequestDto.get());
                reverseTransaction.setExchange(exchange);
                reverseTransaction.setStatus(ExchangeTransactionStatus.APPROVED);
                transactionService.saveTransaction(reverseTransaction);

                itemIds.add(reverseTransaction.getItemId());
            }
            exchange.setStatus(ExchangeStatus.APPROVED);
            exchange = exchangeRepository.save(exchange);
            itemService.updateItemStatus(itemIds, ItemAvailabilityStatus.NOT_AVAILABLE);

            logger.info("Exchange {} approved by {} and items {} marked unavailable", exchangeId, approverId, itemIds);
            return modelMapper.map(exchange, ExchangeDto.class);
        }
        else throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, exchangeId));
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void completeExchange(User user, long exchangeId) throws ServiceException {

        Exchange exchange = getExchange(exchangeId);
        exchange.setStatus(ExchangeStatus.SETTLED);

        long initiatedUserId = exchange.getInitiatedUserId();
        long fellowUserId = exchange.getFellowUserId();
        int initiatedUserPoints = 0;
        int fellowUserPoints = 0;

        List<Long> itemIds = new ArrayList<>();
        for(ExchangeTransaction transaction : exchange.getTransactions()) {
            transactionService.updateTransactionStatus(transaction.getId(), ExchangeTransactionStatus.COMPLETE);
            initiatedUserPoints += transaction.getLender() == initiatedUserId ? 1 : 0;
            fellowUserPoints += transaction.getLender() == fellowUserId ? 1 : 0;
            itemIds.add(transaction.getItemId());
        }
        creditRewardPoints(initiatedUserId, fellowUserId, initiatedUserPoints, fellowUserPoints);
        itemService.updateItemStatus(itemIds, ItemAvailabilityStatus.AVAILABLE);
        logger.debug("Items {} marked available", itemIds);
    }

    private void creditRewardPoints(long initiatedUserId, long fellowUserId, int initiatedUserPoints, int fellowUserPoints) {
        if(initiatedUserPoints == fellowUserPoints) {
            profileService.incrementUserRewardPoints(initiatedUserId, initiatedUserPoints);
            profileService.incrementUserRewardPoints(fellowUserId, fellowUserPoints);
        }
        else {
            long minUserId = initiatedUserPoints < fellowUserPoints ? initiatedUserId : fellowUserId;
            long maxUserId = initiatedUserPoints > fellowUserPoints ? initiatedUserId : fellowUserId;
            int minPoints = Math.min(initiatedUserPoints, fellowUserPoints);
            int difference = Math.abs(initiatedUserPoints - fellowUserPoints);
            profileService.incrementUserRewardPoints(minUserId, minPoints - difference);
            profileService.incrementUserRewardPoints(maxUserId, minPoints + difference);
        }
        logger.debug("Points {}, {} credited to users {}, {}", initiatedUserPoints, fellowUserPoints, initiatedUserId, fellowUserId);
    }

    public Exchange getExchange(long id) throws ServiceException {
        Optional<Exchange> exchangeOptional = exchangeRepository.findById(id);
        if(exchangeOptional.isPresent()) {
            Exchange exchange = exchangeOptional.get();
            return exchange;
        }
        throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, id));
    }

    public ExchangeDto getExchangeDto(long id) throws ServiceException {
        return modelMapper.map(getExchange(id), ExchangeDto.class);
    }
}
