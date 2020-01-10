package com.carrify.web.carrifyweb.request;


import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
public class RentRequest {

    @NotBlank
    private Integer userId;

    @NotBlank
    private Integer carId;
}
