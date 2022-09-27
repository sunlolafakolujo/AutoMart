package com.dapafol.userregistration.passwordtoken.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.repository.AppUserRepository;
import com.dapafol.userregistration.passwordtoken.entity.PasswordToken;
import com.dapafol.userregistration.passwordtoken.exception.PasswordTokenNotFoundException;
import com.dapafol.userregistration.passwordtoken.repository.PasswordTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

@Service
@Transactional
public class PasswordTokenServiceImpl implements PasswordTokenService{
    @Autowired
    private PasswordTokenRepository passwordTokenRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Optional<AppUser> findUserByPasswordToken(String token) {
        return Optional.ofNullable(passwordTokenRepository.findByToken(token).getAppUser());
    }

    @Override
    public String validatePasswordToken(String token) throws PasswordTokenNotFoundException {
        PasswordToken passwordToken=passwordTokenRepository.findByToken(token);
        if (passwordToken==null){
            throw new PasswordTokenNotFoundException("Invalid token");
        }
        Calendar calendar=Calendar.getInstance();
        if ((passwordToken.getExpirationTime().getTime()-calendar.getTime().getTime()<=0)){
            passwordTokenRepository.delete(passwordToken);
            throw new PasswordTokenNotFoundException("Token expired");
        }
        return "Valid token ";
    }

    @Override
    public PasswordToken createPasswordResetTokenForUser(AppUser appUser, String token) {
        PasswordToken passwordToken=new PasswordToken(token,appUser);
        return passwordTokenRepository.save(passwordToken);
    }

    @Override
    public void changeUserPassword(AppUser appUser, String newPassword) {
        appUser.setPassword(passwordEncoder.encode(newPassword));
        appUserRepository.save(appUser);
    }

    @Override
    public Boolean checkIfOldPasswordExist(AppUser appUser,String oldPassword) {
        return passwordEncoder.matches(appUser.getPassword(), oldPassword);
    }
}
