package com.carrify.web.carrifyweb.controller;

import com.carrify.web.carrifyweb.model.Rent.RentDTO;
import com.carrify.web.carrifyweb.model.UserCoupon.UserCouponDTO;
import com.carrify.web.carrifyweb.model.Wallet.WalletDTO;
import com.carrify.web.carrifyweb.request.CouponRequest;
import com.carrify.web.carrifyweb.service.CouponService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@Api(tags = "Coupon Controller")
@RequestMapping("api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) { this.couponService = couponService; }

    @PostMapping("/user/{id}/use")
    public ResponseEntity<WalletDTO> useCoupon(@PathVariable("id") String id, @Valid @RequestBody CouponRequest couponRequest) {
        return ResponseEntity.ok(couponService.useCoupon(id, couponRequest.getValue()));
    }

    @GetMapping("user/{id}")
    public ResponseEntity<List<UserCouponDTO>> showAllUserRents(@PathVariable("id") String userId) {
        return ResponseEntity.ok(couponService.getAllUserUsedCoupons(userId));
    }
}
