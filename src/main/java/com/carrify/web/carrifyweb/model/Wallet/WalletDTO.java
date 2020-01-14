package com.carrify.web.carrifyweb.model.Wallet;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WalletDTO {

    private Integer id;
    private String lastUpdate;
    private Integer amount;
    private Integer operationType;
    private Integer user;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.lastUpdate = CarrifyDateTimeFormatter.formatDate(wallet.getLastUpdate());
        this.amount = wallet.getAmount();
        this.operationType = wallet.getOperationType();
        this.user = wallet.getUser().getId();
    }
}
