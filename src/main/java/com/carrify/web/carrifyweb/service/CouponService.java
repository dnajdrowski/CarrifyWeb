package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiInternalServerError;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.Coupon.Coupon;
import com.carrify.web.carrifyweb.model.Transaction.Transaction;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import com.carrify.web.carrifyweb.model.Wallet.WalletDTO;
import com.carrify.web.carrifyweb.repository.CouponRepository;
import com.carrify.web.carrifyweb.repository.TransactionRepository;
import com.carrify.web.carrifyweb.repository.UserRepository;
import com.carrify.web.carrifyweb.repository.WalletRepository;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.Optional;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;

@Service
public class CouponService {

    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    public CouponService(WalletRepository walletRepository, TransactionRepository transactionRepository,
                         CouponRepository couponRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    public String useCoupon(String userId, String couponValue) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<User> userOptional = userRepository.findById(id);

        if(userOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<Coupon> couponOptional = couponRepository.findByValue(couponValue);

        if (couponOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY029_MSG, CARRIFY029_CODE);
        }

        Coupon coupon = couponOptional.get();

        Optional<Wallet> walletOptional = walletRepository.findById(id);
        if (walletOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY026_MSG, CARRIFY026_CODE);
        }

        Wallet wallet = walletOptional.get();

        int balance = wallet.getAmount() + coupon.getAmount();
        wallet.setAmount(balance);

        Transaction transaction = Transaction.builder()
                .amount(coupon.getAmount())
                .balance(balance)
                .operationType(2)
                .createdAt(LocalDateTime.now())
                .wallet(wallet)
                .build();

        Wallet savedWallet = walletRepository.save(wallet);
        Transaction savedTransaction = transactionRepository.save(transaction);

        if (savedWallet == null || savedTransaction == null) {
            throw new ApiInternalServerError(CARRIFY_INTERNAL_MSG, CARRIFY_INTERNAL_CODE);
        }

        return "Coupon has been used successfully!";

    }
}
