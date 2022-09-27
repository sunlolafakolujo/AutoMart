package com.dapafol.userregistration.passwordtoken.repository;
import com.dapafol.userregistration.passwordtoken.entity.PasswordToken;
import org.springframework.data.jpa.repository.Query;


public interface PasswordTokenRepositoryCustom {
    @Query("From PasswordToken p Where p.token=?1")
    PasswordToken findByToken(String token);
}
