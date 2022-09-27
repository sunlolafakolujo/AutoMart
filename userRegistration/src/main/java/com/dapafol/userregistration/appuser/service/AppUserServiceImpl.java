package com.dapafol.userregistration.appuser.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.appuser.repository.AppUserRepository;
import com.dapafol.userregistration.staticdata.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
@Transactional
public class AppUserServiceImpl implements AppUserService{

    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public AppUser saveUser(AppUser appUser) {

        appUser.setUserId(UUID.randomUUID().toString());
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        return appUserRepository.save(appUser);
    }

    @Override
    public AppUser findUserById(Long id) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User Not Found"));
        return appUser;
    }

    @Override
    public List<AppUser> findUserByFirstName(String firstName,Pageable pageable) throws AppUserNotFoundException {
        pageable= PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByFirstNameLike(firstName,pageable);
        if (appUsers==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUsers;
    }

    @Override
    public List<AppUser> findUserByLastName(String lastName,Pageable pageable) throws AppUserNotFoundException {
        pageable= PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByLastNameLike(lastName,pageable);
        if (appUsers==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUsers;
    }

    @Override
    public List<AppUser> findUserByFirstAndLastName(String firstName, String lastName, Pageable pageable) throws AppUserNotFoundException {
        pageable=PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByFirstNameAndLastName(firstName, lastName, pageable);
        if (appUsers==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUsers;
    }

    @Override
    public AppUser findUserByUsername(String username) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByUsername(username);
        if (appUser==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUser;
    }

    @Override
    public AppUser findUserByEmail(String email) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByEmail(email);
        if (appUser==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUser;
    }

    @Override
    public AppUser findUserByPhone(String phone) throws AppUserNotFoundException {
        AppUser appUser=appUserRepository.findByPhone(phone);
        if (appUser==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUser;
    }

    @Override
    public List<AppUser> findUserByRegistrationStatus(Boolean isEnabled, Pageable pageable) throws AppUserNotFoundException {
        pageable=PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByStatus(isEnabled, pageable);
        if (appUsers==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUsers;
    }

    @Override
    public List<AppUser> findUserByType(UserType userType, Pageable pageable) throws AppUserNotFoundException {
        pageable=PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByUserType(userType, pageable);
        if (appUsers==null){
            throw new AppUserNotFoundException("User Not Found");
        }
        return appUsers;
    }

    @Override
    public List<AppUser> findAllUsers(Pageable pageable) {
        pageable= PageRequest.of(0, 10);
        Page<AppUser> appUserPage=appUserRepository.findAll(pageable);
        return appUserPage.toList();
    }

    @Override
    public AppUser updateUser(AppUser appUser, Long id) throws AppUserNotFoundException {
        AppUser savedAppUser=appUserRepository.findById(id)
                .orElseThrow(()->new AppUserNotFoundException("User Not Found"));
        if (Objects.nonNull(appUser.getFirstName()) && !"".equalsIgnoreCase(appUser.getFirstName())){
            savedAppUser.setFirstName(appUser.getFirstName());
        }if (Objects.nonNull(appUser.getLastName()) && !"".equalsIgnoreCase(appUser.getLastName())){
            savedAppUser.setLastName(appUser.getLastName());
        }if (Objects.nonNull(appUser.getPassword()) && !"".equalsIgnoreCase(appUser.getPassword())){
            savedAppUser.setPassword(appUser.getPassword());
        }if (Objects.nonNull(appUser.getEmail()) && !"".equalsIgnoreCase(appUser.getEmail())){
            savedAppUser.setEmail(appUser.getEmail());
        }if (Objects.nonNull(appUser.getPhone()) && !"".equalsIgnoreCase(appUser.getPhone())){
            savedAppUser.setPhone(appUser.getPhone());
        }if (appUser.getAddress()!=null){
            savedAppUser.setAddress(appUser.getAddress());
        }
        return appUserRepository.save(savedAppUser);
    }

    @Override
    public void deleteUserById(Long id) throws AppUserNotFoundException {
        appUserRepository.deleteById(id);

        Optional<AppUser> optionalAppUser=appUserRepository.findById(id);

        if (optionalAppUser.isPresent()){
            throw new AppUserNotFoundException("User Is Not Deleted");
        }
    }

    @Override
    public void deleteAllUsers() {
        appUserRepository.deleteAll();
    }
}
