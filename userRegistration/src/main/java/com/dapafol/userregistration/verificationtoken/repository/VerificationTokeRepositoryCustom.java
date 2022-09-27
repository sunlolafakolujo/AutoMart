package com.dapafol.userregistration.verificationtoken.repository;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.verificationtoken.entity.VerificationToken;
import org.springframework.data.jpa.repository.Query;

public interface VerificationTokeRepositoryCustom {
    @Query("From VerificationToken v Where v.token=?1")
    VerificationToken findByToken(String token);

    @Query("From VerificationToken v Where v.appUser=?1")
    VerificationToken findByUser(AppUser appUser);
}
