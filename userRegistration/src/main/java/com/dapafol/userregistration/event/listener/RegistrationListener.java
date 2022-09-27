package com.dapafol.userregistration.event.listener;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.email.EmailConfiguration;
import com.dapafol.userregistration.event.RegistrationEvent;
import com.dapafol.userregistration.verificationtoken.service.VerificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.UUID;

@Component
public class RegistrationListener implements ApplicationListener<RegistrationEvent> {
    @Autowired
    private VerificationService verificationService;

    @Autowired
    private EmailConfiguration emailConfiguration;

    @Override
    public void onApplicationEvent(RegistrationEvent event) {
        JavaMailSenderImpl mailSender=new JavaMailSenderImpl();
        mailSender.setHost(emailConfiguration.getHost());
        mailSender.setPort(emailConfiguration.getPort());
        mailSender.setUsername(emailConfiguration.getUsername());
        mailSender.setPassword(emailConfiguration.getPassword());

        AppUser appUser= event.getAppUser();
        String token= UUID.randomUUID().toString();
        verificationService.saveVerificationTokenForUser(appUser, token);
        SimpleMailMessage mail= confirmationEmailMessage(event, appUser, token);
        mailSender.send(mail);
    }

    private SimpleMailMessage confirmationEmailMessage(RegistrationEvent event, AppUser appUser, String token){
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        String confirmUrl=event.getApplicationUrl() + "/api/users/verifyAccount/"+token;
        String receiver= appUser.getEmail();
        String subject="Verify Account";
        String from="fakolujos@gmail.com";

        mailMessage.setTo(receiver);
        mailMessage.setFrom(from);
        mailMessage.setSentDate(new Date());
        mailMessage.setSubject(subject);
        mailMessage.setText("Dear "+appUser.getFirstName()+",\n\n"+"Click the link to verify email "+confirmUrl);

        return mailMessage;
    }
}
