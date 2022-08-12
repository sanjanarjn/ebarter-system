package com.ebarter.services.exchange;

import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Entity
public class ExchangeRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long initiatedUserId;

    @OneToMany(mappedBy = "request")
    private List<ExchangeTransaction> transactions;

    private Date createdTime;
    private Date modifiedTime;

    public void addTransaction(ExchangeTransaction transaction) {
        if(this.transactions == null)
            this.transactions = new ArrayList<>();

        this.transactions.add(transaction);
    }
}
