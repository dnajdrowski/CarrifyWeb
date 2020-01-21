package com.carrify.web.carrifyweb.model.Wallet;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDTO {

    private Integer id;
    private String lastTransaction;
    private Integer amount;
    private Integer user;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        if(wallet.getLastTransaction() != null)
            this.lastTransaction = CarrifyDateTimeFormatter.formatDate(wallet.getLastTransaction());
        this.amount = wallet.getAmount();
        this.user = wallet.getUser().getId();
    }
}
