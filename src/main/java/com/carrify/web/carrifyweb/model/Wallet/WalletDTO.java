package com.carrify.web.carrifyweb.model.Wallet;

import com.carrify.web.carrifyweb.model.User.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class WalletDTO {

    private Integer id;
    private LocalDateTime lastUpdate;
    private Integer amount;
    private Integer operationType;
    private User user;

    public WalletDTO(Wallet wallet) {
        this.id = wallet.getId();
        this.lastUpdate = wallet.getLastUpdate();
        this.amount = wallet.getAmount();
        this.operationType = wallet.getOperationType();
        this.user = wallet.getUser();
    }
}
