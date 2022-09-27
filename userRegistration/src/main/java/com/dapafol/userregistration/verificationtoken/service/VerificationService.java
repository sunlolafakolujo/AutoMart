package com.dapafol.userregistration.verificationtoken.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.verificationtoken.entity.VerificationToken;
import com.dapafol.userregistration.verificationtoken.exception.VerificationTokenNotFoundException;

import java.util.Optional;

public interface VerificationService {

    Optional<AppUser> userByFindVerificationToken(String token);
    void saveVerificationTokenForUser(AppUser appUser, String token);
    String validateVerificationToken(String token) throws VerificationTokenNotFoundException;
    VerificationToken generateNewVerificationToken(String oldToken);
}
