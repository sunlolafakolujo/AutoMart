package com.dapafol.userregistration.appuser.controller;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.verificationtoken.entity.VerificationToken;
import com.dapafol.userregistration.verificationtoken.exception.VerificationTokenNotFoundException;
import com.dapafol.userregistration.verificationtoken.service.VerificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Optional;

@RestController
@RequestMapping(path = "api/users")
@RequiredArgsConstructor
public class AppUserVerificationTokenController {
    private final VerificationService verificationService;

    @GetMapping("/verifyAccount/{token}")
    public String confirmAccount(@PathVariable(value = "token") String token) throws VerificationTokenNotFoundException {
        String result=verificationService.validateVerificationToken(token);

        if (result.equalsIgnoreCase("Valid")){
            return "User verified successfully";
        }
        return "Bad user";
    }

    @GetMapping("/resendVerificationTokenForUser/{token}")
    public String resendNewVerificationToken(@PathVariable(value = "token") String token, HttpServletRequest request){
        VerificationToken verificationToken=verificationService.generateNewVerificationToken(token);
        AppUser appUser= verificationToken.getAppUser();
        resendVerificationTokenMail(applicationUrl(request),verificationToken,appUser);
        return "Verification link sent";
    }

    @GetMapping("/findUserByVerificationToken/{token}")
    public Optional<AppUser> getUserByVerificationToken(@PathVariable(value = "token") String token){
        Optional<AppUser> appUserOptional=verificationService.userByFindVerificationToken(token);
        return appUserOptional;
    }

    private SimpleMailMessage resendVerificationTokenMail(String applicationUrl, VerificationToken verificationToken, AppUser appUser) {
        String link=applicationUrl + "/api/users/verifyAccount/"+verificationToken.getToken();
        String subject="Resend Verification Token";
        String text="Dear "+appUser.getFirstName()+",\n\n"+"Click on the link to get new verification token "+link;
        return constructMail(subject, text,appUser);
    }

    private SimpleMailMessage constructMail(String subject, String text, AppUser appUser){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(appUser.getEmail());
        mailMessage.setSubject(subject);
        mailMessage.setSentDate(new Date());
        mailMessage.setText(text);
        return mailMessage;
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+ ":"+
                request.getServerPort()+
                request.getContextPath();
    }
}
