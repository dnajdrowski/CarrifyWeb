package com.carrify.web.carrifyweb.model.UserCoupon;

import com.carrify.web.carrifyweb.formatter.CarrifyDateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserCouponDTO {

    String value;
    String name;
    String usedAt;

    public UserCouponDTO(UserCoupon userCoupon) {
        if (userCoupon.getCoupon() != null) {
            this.value = userCoupon.getCoupon().getValue();
            this.name = userCoupon.getCoupon().getName();
            this.usedAt = CarrifyDateTimeFormatter.formatDate(LocalDateTime.now());
        }
    }
}
