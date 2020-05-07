package com.carrify.web.carrifyweb.repository;


import com.carrify.web.carrifyweb.model.Coupon.Coupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CouponRepository extends CrudRepository<Coupon, Integer> {

    Optional<Coupon> findByValue(String value);
}
