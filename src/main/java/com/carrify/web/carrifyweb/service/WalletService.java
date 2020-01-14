package com.carrify.web.carrifyweb.service;

import com.carrify.web.carrifyweb.exception.ApiNotFoundException;
import com.carrify.web.carrifyweb.model.User.User;
import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import com.carrify.web.carrifyweb.model.Wallet.WalletDTO;
import com.carrify.web.carrifyweb.repository.UserRepository;
import com.carrify.web.carrifyweb.repository.WalletRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

import static com.carrify.web.carrifyweb.exception.ApiErrorConstants.*;
import static java.util.stream.Collectors.toList;

@Service
@Slf4j
public class WalletService {

    private final WalletRepository walletRepository;
    private final UserRepository userRepository;

    public WalletService(WalletRepository walletRepository, UserRepository userRepository) {
        this.walletRepository = walletRepository;
        this.userRepository = userRepository;
    }

    public WalletDTO getWalletByUserId(String userId) {
        Optional<Wallet> optionalWallet = walletRepository.getFirstByUserIdOrderByIdDesc(Integer.parseInt(userId));
        if (optionalWallet.isPresent())
            return new WalletDTO(optionalWallet.get());
        else
            throw new ApiNotFoundException(CARRIFY026_MSG, CARRIFY026_CODE);
    }

    public WalletDTO topUpWallet(int userId, int amount) {
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY009_MSG, CARRIFY009_CODE);
        }



        User user = optionalUser.get();
        Wallet wallet = Wallet.builder()
                .amount(amount + walletRepository.getFirstByUserIdOrderByIdDesc(userId).get().getAmount())
                .lastUpdate(LocalDateTime.now())
                .operationType(1)
                .user(user)
                .build();
        walletRepository.save(wallet);
        return new WalletDTO(wallet);
    }

    public List<WalletDTO> getWalletOperationsByUserId(String userId) {
        Iterable<Wallet> wallets = walletRepository.findAllByUserId(Integer.parseInt(userId));
        List<WalletDTO> walletCollected = StreamSupport.stream(wallets.spliterator(), false)
                .map(WalletDTO::new)
                .collect(toList());

        if (walletCollected.isEmpty()) {
            throw new ApiNotFoundException(CARRIFY027_MSG, CARRIFY027_CODE);
        }
        return walletCollected;
    }
}
