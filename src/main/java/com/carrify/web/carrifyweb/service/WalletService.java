package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.Transaction.Transaction;
import com.carrify.web.carrifyweb.model.Transaction.TransactionDTO;
import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import com.carrify.web.carrifyweb.model.Wallet.WalletDTO;
import com.carrify.web.carrifyweb.repository.TransactionRepository;
import com.carrify.web.carrifyweb.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;

    public WalletService(WalletRepository walletRepository, TransactionRepository transactionRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
    }

    public WalletDTO getWalletById(String walletId) {
        int id;
        try {
            id = Integer.parseInt(walletId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY026_MSG, CARRIFY026_CODE);
        }
        Optional<Wallet> optionalWallet = walletRepository.findById(id);
        if (optionalWallet.isEmpty())
            throw new ApiNotFoundException(CARRIFY026_MSG, CARRIFY026_CODE);

        return new WalletDTO(optionalWallet.get());
    }

    public WalletDTO topUpWallet(int walletId, int amount) {
        Optional<Wallet> walletOptional = walletRepository.findById(walletId);
        if (walletOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY026_MSG, CARRIFY026_CODE);
        }

        Wallet wallet = walletOptional.get();

        int balance = wallet.getAmount() + amount;
        wallet.setAmount(balance);

        Transaction transaction = Transaction.builder()
                .amount(amount)
                .balance(balance)
                .operationType(0)
                .createdAt(LocalDateTime.now())
                .wallet(wallet)
                .build();

        Wallet savedWallet = walletRepository.save(wallet);
        Transaction savedTransaction = transactionRepository.save(transaction);

        if (savedWallet == null || savedTransaction == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        return new WalletDTO(wallet);
    }

    public List<TransactionDTO> getWalletTransactionHistory(String walletId) {
        int id;
        try {
            id = Integer.parseInt(walletId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY026_MSG, CARRIFY026_CODE);
        }

        Iterable<Transaction> walletTransactions = transactionRepository.findAllByWalletId(id);

        return StreamSupport.stream(walletTransactions.spliterator(), false)
                .sorted(Comparator.comparingInt(Transaction::getId))
                .map(TransactionDTO::new)
                .collect(toList());
    }


    public void validateWalletTopUpRequest(BindingResult results) {
        if (results.hasErrors()) {
            for (ObjectError error : results.getAllErrors()) {
                if (CARRIFY909_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY909_MSG, CARRIFY909_CODE);
                } else if (CARRIFY910_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY910_MSG, CARRIFY910_CODE);
                } else if (CARRIFY911_CODE.equalsIgnoreCase(error.getDefaultMessage())) {
                    throw new ApiBadRequestException(CARRIFY911_MSG, CARRIFY911_CODE);
                }
            }
        }
    }
}
