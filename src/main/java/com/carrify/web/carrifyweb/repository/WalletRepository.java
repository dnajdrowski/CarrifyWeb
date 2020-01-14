package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.Wallet.Wallet;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WalletRepository extends CrudRepository<Wallet, Integer> {
    Optional<Wallet> getFirstByUserIdOrderByIdDesc(int userId);

    Iterable<Wallet> findAllByUserId(int userId);
}
