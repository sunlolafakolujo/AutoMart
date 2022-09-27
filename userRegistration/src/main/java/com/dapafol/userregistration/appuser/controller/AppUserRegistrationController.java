package com.dapafol.userregistration.appuser.controller;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.entity.NewAppUser;
import com.dapafol.userregistration.appuser.service.AppUserService;
import com.dapafol.userregistration.event.RegistrationEvent;
import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import com.dapafol.userregistration.role.service.UserRoleService;
import com.dapafol.userregistration.staticdata.UserType;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/api/users")
@RequiredArgsConstructor
public class AppUserRegistrationController {
    private final AppUserService appUserService;
    private final ModelMapper modelMapper;
    private final UserRoleService userRoleService;
    private final ApplicationEventPublisher publisher;

    @PostMapping("/seller/signUp")
    public ResponseEntity<NewAppUser> registerSeller(@RequestBody @Valid NewAppUser newAppUser,
                                                     HttpServletRequest request) throws UserRoleNotFoundException {

        newAppUser.setUserType(UserType.SELLER);

        List<UserRole> userRoles=new ArrayList<>();
        UserRole userRole=userRoleService.findRoleByName("SELLER");
        userRoles.add(userRole);
        newAppUser.setUserRoles(userRoles);

        AppUser appUser=modelMapper.map(newAppUser, AppUser.class);
        AppUser post=appUserService.saveUser(appUser);
        RegistrationEvent registrationEvent=new RegistrationEvent(post, applicationUrl(request));
        publisher.publishEvent(registrationEvent);
        NewAppUser posted=modelMapper.map(post, NewAppUser.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/buyer/signUp")
    public ResponseEntity<NewAppUser> registerBuyer(@RequestBody @Valid NewAppUser newAppUser,
                                                     HttpServletRequest request) throws UserRoleNotFoundException {

        newAppUser.setUserType(UserType.BUYER);

        List<UserRole> userRoles=new ArrayList<>();
        UserRole userRole=userRoleService.findRoleByName("BUYER");
        userRoles.add(userRole);
        newAppUser.setUserRoles(userRoles);

        AppUser appUser=modelMapper.map(newAppUser, AppUser.class);
        AppUser post=appUserService.saveUser(appUser);
        RegistrationEvent registrationEvent=new RegistrationEvent(post, applicationUrl(request));
        publisher.publishEvent(registrationEvent);
        NewAppUser posted=modelMapper.map(post, NewAppUser.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @PostMapping("/employeeRegistration")
    public ResponseEntity<NewAppUser> registerEmployee(@RequestBody @Valid NewAppUser newAppUser,
                                                    HttpServletRequest request) throws UserRoleNotFoundException {

        newAppUser.setUserType(UserType.ADMIN);

        List<UserRole> userRoles=new ArrayList<>();
        UserRole userRole=userRoleService.findRoleByName("ADMIN");
        userRoles.add(userRole);
        newAppUser.setUserRoles(userRoles);

        AppUser appUser=modelMapper.map(newAppUser, AppUser.class);
        AppUser post=appUserService.saveUser(appUser);
        RegistrationEvent registrationEvent=new RegistrationEvent(post, applicationUrl(request));
        publisher.publishEvent(registrationEvent);
        NewAppUser posted=modelMapper.map(post, NewAppUser.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    private String applicationUrl(HttpServletRequest request) {
        return "http://"
                + request.getServerName()
                + ":"
                + request.getServerPort()
                + request.getContextPath();
    }
}
