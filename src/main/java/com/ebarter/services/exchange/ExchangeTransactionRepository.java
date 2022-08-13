package com.ebarter.services.exchange;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeTransactionRepository extends JpaRepository<ExchangeTransaction, Long> {
    public ExchangeTransaction findByExchangeIdAndBorrowerAndLender(long exchangeId, long borrowerId, long lenderId);


}
