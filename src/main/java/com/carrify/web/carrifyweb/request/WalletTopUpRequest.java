package com.carrify.web.carrifyweb.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY909_MSG;

@Getter
@Setter
public class WalletTopUpRequest {

    @NotNull
    @Min(value = 5, message = CARRIFY909_MSG)
    private Integer amount;

    @NotNull
    private Integer walletId;
}
