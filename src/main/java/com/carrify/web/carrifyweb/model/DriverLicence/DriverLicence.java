package com.carrify.web.carrifyweb.model.DriverLicence;

import com.carrify.web.carrifyweb.model.User.User;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "driver_licence")
public class DriverLicence {

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Integer id;

    @Column(name = "img_front", nullable = false)
    private byte[] imgFront;

    @Column(name = "img_revers", nullable = false)
    private byte[] imgRevers;

    @Column(name = "expire_date")
    private LocalDate expireDate;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    @JoinColumn(name = "id")
    private User user;

}
