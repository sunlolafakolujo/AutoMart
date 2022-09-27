package com.dapafol.userregistration.appuser.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.staticdata.UserType;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AppUserService {
    AppUser saveUser(AppUser appUser);
    AppUser findUserById(Long id) throws AppUserNotFoundException;
    List<AppUser> findUserByFirstName(String firstName,Pageable pageable) throws AppUserNotFoundException;
    List<AppUser> findUserByLastName(String lastName,Pageable pageable) throws AppUserNotFoundException;
    List<AppUser> findUserByFirstAndLastName(String firstName, String lastName,Pageable pageable) throws AppUserNotFoundException;
    AppUser findUserByUsername(String username) throws AppUserNotFoundException;
    AppUser findUserByEmail(String email) throws AppUserNotFoundException;
    AppUser findUserByPhone(String phone) throws AppUserNotFoundException;
    List<AppUser> findUserByRegistrationStatus(Boolean isEnabled,Pageable pageable) throws AppUserNotFoundException;
    List<AppUser> findUserByType(UserType userType,Pageable pageable) throws AppUserNotFoundException;
    List<AppUser>  findAllUsers(Pageable pageable);
    AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException;
    void deleteUserById(Long id) throws AppUserNotFoundException;
    void deleteAllUsers();
}
