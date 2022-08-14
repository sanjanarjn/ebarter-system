package com.ebarter.services.exchange;

import com.ebarter.services.BaseEntity;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Exchange extends BaseEntity {

    private long initiatedUserId;
    private long fellowUserId;

    @OneToMany(mappedBy = "exchange", fetch = FetchType.EAGER)
    private List<ExchangeTransaction> transactions;

    private ExchangeStatus status;

    public void addTransaction(ExchangeTransaction transaction) {
        if(this.transactions == null)
            this.transactions = new ArrayList<>();

        this.transactions.add(transaction);
    }
}
