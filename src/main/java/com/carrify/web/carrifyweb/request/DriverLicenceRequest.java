package com.carrify.web.carrifyweb.request;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY022_CODE;
import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.CARRIFY023_CODE;

@Getter
@Setter
public class DriverLicenceRequest {

    @JsonFormat(pattern="yyyy-MM-dd")
    @NotNull(message = CARRIFY022_CODE)
    @FutureOrPresent(message = CARRIFY023_CODE)
    private LocalDate expireDate;

}
