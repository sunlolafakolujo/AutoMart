package com.dapafol.userregistration.passwordtoken.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.passwordtoken.entity.PasswordToken;
import com.dapafol.userregistration.passwordtoken.exception.PasswordTokenNotFoundException;

import java.util.Optional;

public interface PasswordTokenService {
    Optional<AppUser> findUserByPasswordToken(String token);
    String validatePasswordToken(String token) throws PasswordTokenNotFoundException;
    PasswordToken createPasswordResetTokenForUser(AppUser appUser, String token);
    void changeUserPassword(AppUser appUser,String password);
    Boolean checkIfOldPasswordExist(AppUser appUser, String oldPassword);
}
