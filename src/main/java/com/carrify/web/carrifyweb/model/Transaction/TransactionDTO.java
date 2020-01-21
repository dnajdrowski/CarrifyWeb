package com.carrify.web.carrifyweb.model.Transaction;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDTO {

    private Integer id;
    private Integer amount;
    private Integer operationType;
    private Integer balance;
    private String createdAt;
    private Integer rent;
    private Integer wallet;

    public TransactionDTO(Transaction transaction) {
        this.id = transaction.getId();
        this.amount = transaction.getAmount();
        this.balance = transaction.getBalance();
        this.operationType = transaction.getOperationType();
        this.createdAt = CarrifyDateTimeFormatter.formatDate(transaction.getCreatedAt());
        if(transaction.getRent() != null)
            this.rent = transaction.getRent().getId();
        this.wallet = transaction.getWallet().getId();
    }
}
