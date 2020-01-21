package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.Transaction.TransactionDTO;
import com.carrify.web.carrifyweb.model.Wallet.WalletDTO;
import com.carrify.web.carrifyweb.request.WalletTopUpRequest;
import com.carrify.web.carrifyweb.response.ApiErrorResponse;
import com.carrify.web.carrifyweb.service.WalletService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@Api(tags = "Wallets")
@RequestMapping("api/wallets")
public class WalletController {

    private final WalletService walletService;

    public WalletController(WalletService walletService) {
        this.walletService = walletService;
    }

    @ApiOperation(value = "Get user wallet", response = WalletDTO.class, produces = MediaType.APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY026_CODE + "\n" + "msg: " + CARRIFY026_MSG,
                    response = ApiErrorResponse.class)
    })

    @GetMapping("/{id}")
    public ResponseEntity<WalletDTO> getWalletByUserId(@PathVariable("id") String userId) {
        return ResponseEntity.ok(walletService.getWalletById(userId));
    }

    @ApiOperation(value = "Top up user wallet", response = WalletDTO.class, responseContainer = "List", produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY009_CODE + "\n" + "msg: " + CARRIFY009_MSG, response = ApiErrorResponse.class)
    })
    @PostMapping("/top-up")
    public ResponseEntity<WalletDTO> topUpWalletByUserId(@Valid @RequestBody WalletTopUpRequest walletTopUpRequest, BindingResult results) {
        walletService.validateWalletTopUpRequest(results);
        return ResponseEntity.ok(walletService.topUpWallet(walletTopUpRequest.getWalletId(), walletTopUpRequest.getAmount()));
    }

    @ApiOperation(value = "Get user wallet transactions", response = WalletDTO.class, responseContainer = "List", produces = APPLICATION_JSON_VALUE)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Errors:\ncode: " + CARRIFY009_CODE + "\n" + "msg: " + CARRIFY009_MSG, response = ApiErrorResponse.class)
    })
    @GetMapping("/{id}/history")
    public ResponseEntity<List<TransactionDTO>> getWalletOperationsByUserId(@PathVariable("id") String userId) {
        return ResponseEntity.ok(walletService.getWalletTransactionHistory(userId));
    }

}
