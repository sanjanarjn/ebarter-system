package com.ebarter.services.exchange;

import com.ebarter.services.exceptions.ExceptionMessages;
import com.ebarter.services.exceptions.ServiceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;

@Service
public class ExchangeTransactionService {

    @Autowired
    private ExchangeTransactionRepository transactionRepository;

    public boolean saveTransaction(ExchangeTransaction transaction) {
        transactionRepository.save(transaction);
        return true;
    }

    public boolean saveTransactions(List<ExchangeTransaction> transactions) {
        transactionRepository.saveAll(transactions);
        return true;
    }

    ExchangeTransaction getTransaction(long exchangeId, long borrowerId, long lenderId) {
        return transactionRepository.findByExchangeIdAndBorrowerAndLender(exchangeId, borrowerId, lenderId);
    }

    ExchangeTransaction updateTransactionStatus(long transactionId, ExchangeTransactionStatus complete) throws ServiceException {
        Optional<ExchangeTransaction> transactionOptional = transactionRepository.findById(transactionId);
        if(transactionOptional.isPresent()) {
            ExchangeTransaction transaction = transactionOptional.get();
            transaction.setStatus(ExchangeTransactionStatus.COMPLETE);
            transactionRepository.save(transaction);
            return transaction;
        }
        else throw new ServiceException(MessageFormat.format(ExceptionMessages.ENTITY_ID_NOT_FOUND, transactionId));
    }
}
