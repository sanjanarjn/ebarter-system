package com.ebarter.services.exchange;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
