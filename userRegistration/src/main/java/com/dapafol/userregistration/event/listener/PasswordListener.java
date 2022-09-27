package com.dapafol.userregistration.event.listener;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.email.EmailConfiguration;
import com.dapafol.userregistration.event.PasswordEvent;
import com.dapafol.userregistration.passwordtoken.service.PasswordTokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class PasswordListener implements ApplicationListener<PasswordEvent> {
    @Autowired
    private PasswordTokenService passwordTokenService;
    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(PasswordEvent event) {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());

        AppUser appUser= event.getAppUser();
        String token= UUID.randomUUID().toString();
        passwordTokenService.createPasswordResetTokenForUser(appUser, token);
        SimpleMailMessage message=constructEmailToResetPassword(event,appUser,token);
        mailSender.send(message);

    }

    private SimpleMailMessage constructEmailToResetPassword(PasswordEvent event, AppUser appUser, String token) {
        String link= event.getApplicationUrl()+"/api/appUser/savePassword/"+token;
        String to= appUser.getEmail();
        String subject="Reset Password";
        String text="Dear "+appUser.getUsername()+",\r\n"+"Click on the link to reset password "+link;

        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setTo(to);
        mailMessage.setFrom("fakolujos@gmail.com");
        mailMessage.setSubject(subject);
        mailMessage.setText(text);
        return mailMessage;


    }
}
