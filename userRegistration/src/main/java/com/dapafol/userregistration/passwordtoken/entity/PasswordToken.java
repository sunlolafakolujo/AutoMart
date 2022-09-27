package com.dapafol.userregistration.passwordtoken.entity;

import com.dapafol.userregistration.appuser.entity.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
public class PasswordToken {
    private static final Integer EXPIRATION_TIME=10;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String token;

    private Date expirationTime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id", nullable = false, referencedColumnName = "id")
    private AppUser appUser;

    public PasswordToken(String token, AppUser appUser) {
        super();
        this.token = token;
        this.appUser = appUser;
        this.expirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }

    public PasswordToken(String token) {
        super();
        this.token = token;
        this.expirationTime=calculateExpirationTime(EXPIRATION_TIME);
    }
    private Date calculateExpirationTime(Integer expirationTime) {
        Calendar calendar=Calendar.getInstance();
        calendar.setTimeInMillis(new Date().getTime());
        calendar.add(Calendar.MINUTE, expirationTime);
        return new Date(calendar.getTime().getTime());
    }
}
