package com.carrify.web.carrifyweb.model.UserCoupon;
import com.carrify.web.carrifyweb.model.Coupon.Coupon;
import com.carrify.web.carrifyweb.model.User.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "user_coupon")
public class UserCoupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
