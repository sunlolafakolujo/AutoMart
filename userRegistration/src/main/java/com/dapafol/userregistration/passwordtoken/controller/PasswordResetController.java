package com.dapafol.userregistration.passwordtoken.controller;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.appuser.service.AppUserService;
import com.dapafol.userregistration.event.PasswordEvent;
import com.dapafol.userregistration.event.RegistrationEvent;
import com.dapafol.userregistration.passwordtoken.entity.PasswordResetModel;
import com.dapafol.userregistration.passwordtoken.exception.PasswordTokenNotFoundException;
import com.dapafol.userregistration.passwordtoken.service.PasswordTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/appUser")
@RequiredArgsConstructor
public class PasswordResetController {
    private final PasswordTokenService passwordTokenService;
    private final AppUserService appUserService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/resetPassword")
    public String resetPassword(@RequestBody PasswordResetModel passwordResetModel,
                                HttpServletRequest request) throws AppUserNotFoundException {

        AppUser appUser= appUserService.findUserByUsername(passwordResetModel.getUsername());

        if (appUser!=null){
            publisher.publishEvent(new PasswordEvent(appUser, applicationUrl(request)));
        }

        return "An email will be sent to you to reset your password";
    }

    @PostMapping("/savePassword/{token}")
    public String savePassword(@RequestBody PasswordResetModel passwordResetModel,
                               @PathVariable(value = "token") String token) throws PasswordTokenNotFoundException {

        Optional<AppUser> appUserOptional = passwordTokenService.findUserByPasswordToken(token);
        if (appUserOptional.isPresent()){
            passwordTokenService.changeUserPassword(appUserOptional.get(), passwordResetModel.getNewPassword());
            return "Password reset successfully";
        }else return "Invalid token";
    }

    @PostMapping("/changePassword")
    public String changeUserPassword(@RequestBody PasswordResetModel passwordResetModel)
                                            throws AppUserNotFoundException, PasswordTokenNotFoundException {

        AppUser appUser=appUserService.findUserByUsername(passwordResetModel.getUsername());
        if (appUser==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        if (!passwordTokenService.checkIfOldPasswordExist(appUser, passwordResetModel.getOldPassword())){
            throw new PasswordTokenNotFoundException("Invalid old password");
        }
        passwordTokenService.changeUserPassword(appUser, passwordResetModel.getNewPassword());
        return "Password changed successfully";
    }


    private String applicationUrl(HttpServletRequest request) {
        return "http://"+
                request.getServerName()+":"+
                request.getServerPort()+
                request.getContextPath();
    }
}
