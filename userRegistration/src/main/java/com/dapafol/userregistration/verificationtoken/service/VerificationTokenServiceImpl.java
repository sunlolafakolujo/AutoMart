package com.dapafol.userregistration.verificationtoken.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.repository.AppUserRepository;
import com.dapafol.userregistration.verificationtoken.entity.VerificationToken;
import com.dapafol.userregistration.verificationtoken.exception.VerificationTokenNotFoundException;
import com.dapafol.userregistration.verificationtoken.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class VerificationTokenServiceImpl implements VerificationService{
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public Optional<AppUser> userByFindVerificationToken(String token) {
        return Optional.ofNullable(verificationTokenRepository.findByToken(token).getAppUser());
    }

    @Override
    public void saveVerificationTokenForUser(AppUser appUser, String token) {
        VerificationToken verificationToken=new VerificationToken(token,appUser);
        verificationTokenRepository.save(verificationToken);
    }

    @Override
    public String validateVerificationToken(String token) throws VerificationTokenNotFoundException {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(token);

        if (verificationToken==null){
            throw new VerificationTokenNotFoundException("Invalid token");
        }
        AppUser appUser=verificationToken.getAppUser();
        Calendar cal=Calendar.getInstance();

        if ((verificationToken.getExpirationTime().getTime()-cal.getTime().getTime()<=0)){
            verificationTokenRepository.delete(verificationToken);
            throw new VerificationTokenNotFoundException("Token expired");
        }

        appUser.setIsEnabled(true);
        appUserRepository.save(appUser);

        return "Valid token";
    }

    @Override
    public VerificationToken generateNewVerificationToken(String oldToken) {
        VerificationToken verificationToken=verificationTokenRepository.findByToken(oldToken);
        verificationToken.setToken(UUID.randomUUID().toString());
        return verificationTokenRepository.save(verificationToken);
    }
}
