package com.carrify.web.carrifyweb.repository;

import com.carrify.web.carrifyweb.model.UserCoupon.UserCoupon;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserCouponRepository extends CrudRepository<UserCoupon, Integer> {

    Optional<UserCoupon> findByUserIdAndCouponId(int userId, int couponId);
}
