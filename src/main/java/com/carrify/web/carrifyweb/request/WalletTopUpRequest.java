package com.carrify.web.carrifyweb.request;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class WalletTopUpRequest {

    @NotBlank
    private Integer amount;

    @NotBlank
    private Integer userId;
}
