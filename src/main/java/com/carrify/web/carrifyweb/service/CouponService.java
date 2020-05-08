package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiBadRequestException;
import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.Coupon.Coupon;
import com.carrify.web.carrifyweb.model.Transaction.Transaction;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.model.UserCoupon.UserCoupon;
import com.carrify.web.carrifyweb.model.UserCoupon.UserCouponDTO;
import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import com.carrify.web.carrifyweb.model.Wallet.WalletDTO;
import com.carrify.web.carrifyweb.repository.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static com.carrify.web.carrifyweb.model.Transaction.TransactionDTO.TYPE_COUPON;
import static java.util.stream.Collectors.toList;

@Service
public class CouponService {

    private final TransactionRepository transactionRepository;
    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public CouponService(WalletRepository walletRepository, TransactionRepository transactionRepository,
                         CouponRepository couponRepository, UserRepository userRepository, UserCouponRepository userCouponRepository) {
        this.walletRepository = walletRepository;
        this.transactionRepository = transactionRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.userCouponRepository = userCouponRepository;
    }

    public List<UserCouponDTO> getAllUserUsedCoupons(String userId) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Iterable<UserCoupon> userCoupons = userCouponRepository.findAllByUserId(id);
        List<UserCouponDTO> userCouponCollected = StreamSupport.stream(userCoupons.spliterator(), false)
                .map(UserCouponDTO::new)
                .collect(toList());

        if (userCouponCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY031_MSG, CARRIFY031_CODE);
        }
        return userCouponCollected;

    }

    public WalletDTO useCoupon(String userId, String couponValue) {
        int id;
        try {
            id = Integer.parseInt(userId);
        } catch (NumberFormatException e) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<User> userOptional = userRepository.findById(id);

        if (userOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }

        Optional<Coupon> couponOptional = couponRepository.findByValue(couponValue);

        if (couponOptional.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY029_MSG, CARRIFY029_CODE);
        }

        Coupon coupon = couponOptional.get();

        LocalDateTime now = LocalDateTime.now();

        if (now.isBefore(coupon.getCreatedAt())) {
            throw new ApiBadRequestException(CARRIFY032_MSG, CARRIFY032_CODE);
        }

        if (now.isAfter(coupon.getEndAt())) {
            throw new ApiBadRequestException(CARRIFY033_MSG, CARRIFY033_CODE);
        }

        Optional<UserCoupon> userCouponOptional = userCouponRepository.findByUserIdAndCouponId(id, coupon.getId());

        if (userCouponOptional.isPresent()) {
            throw new ApiBadRequestException(CARRIFY030_MSG, CARRIFY030_CODE);
        }

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
                .operationType(TYPE_COUPON)
                .createdAt(now)
                .wallet(wallet)
                .build();

        UserCoupon userCoupon = UserCoupon.builder()
                .coupon(coupon)
                .user(userOptional.get())
                .createdAt(now)
                .build();

        userCouponRepository.save(userCoupon);
        walletRepository.save(wallet);
        transactionRepository.save(transaction);

        return new WalletDTO(wallet);
    }
}
