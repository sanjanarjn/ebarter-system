package com.ebarter.services.exchange;

import com.ebarter.services.BaseEntity;
import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ExchangeTransaction extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="exchange_id", nullable=false)
    private Exchange exchange;
    private long lender;
    private long borrower;
    private long itemId;

    private ExchangeTransactionStatus status;
}
