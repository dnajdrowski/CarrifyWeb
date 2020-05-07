package com.carrify.web.carrifyweb.request;

import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CouponRequest {

    @NotNull
    private String value;

}
