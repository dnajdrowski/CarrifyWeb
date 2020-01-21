package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.Transaction.Transaction;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer> {

    Iterable<Transaction> findAllByWalletIdOrderByIdDesc(Integer id);
}
