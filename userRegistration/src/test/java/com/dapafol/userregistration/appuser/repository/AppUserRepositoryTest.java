package com.dapafol.userregistration.appuser.repository;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.repository.AddressRepository;
import com.dapafol.userregistration.staticdata.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
@Transactional
class AppUserRepositoryTest {
    @Autowired
    private AppUserRepository appUserRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    AppUser appUser;

    Address address;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        address=new Address();
    }

    @Test
    void testThatYouCanSaveUser(){
        address.setHouseNumber("10");
        address.setStreetName("Owokoniran Street Alapere, Ketu");
        address.setCity("Alimosho");
        address.setLandMark("Biola Bust-stop");
        address.setStateProvince("Lagos");
        address.setCountry("Nigeria");

        appUser.setUserType(UserType.ADMIN);
        appUser.setUserId("ADMIN-".concat(UUID.randomUUID().toString()));
        appUser.setFirstName("Olakunle");
        appUser.setLastName("Adetokunbo");
        appUser.setUsername("oatokunbo");
        appUser.setPassword(passwordEncoder.encode("1234"));
        appUser.setEmail("oadetokunbo@oal.com");
        appUser.setPhone("08168265672");
        appUser.setIsEnabled(Boolean.TRUE);
        appUser.setAddress(address);

        log.info("App User repo before saving {}", appUser);

        assertDoesNotThrow(()->appUserRepository.save(appUser));

        log.info("App User repo after saving {}", appUser);
    }

    @Test
    void testThatYouCanFindUserById() throws AppUserNotFoundException {
        Long id=2L;
        appUser=appUserRepository.findById(id).orElseThrow(()->new AppUserNotFoundException("User Not Found"));

        assertEquals(id, appUser.getId());

        log.info("App User ID 1: {}", appUser.getUsername());
    }

    @Test
    void testThatYouCanFindUserByFirstNameLike() throws AppUserNotFoundException {
        String firstName="Ade";
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByFirstNameLike(firstName,pageable);

        if (appUsers.isEmpty()){
            throw new AppUserNotFoundException("User with first name "+firstName+" Not Found");
        }
        log.info("User with first name Like ade: {}", appUsers);
    }

    @Test
    void testThatYouCanFindUserByLastNameLike() throws AppUserNotFoundException {
        String lastName="Er";
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByLastNameLike(lastName,pageable);

        if (appUsers.isEmpty()){
            throw new AppUserNotFoundException("User with lastname "+lastName+" Not Found");
        }
        log.info("User with last name Like ade: {}", appUsers);
    }

    @Test
    void testThatYouCanFindUserByFirstNameAndLastNameLike() throws AppUserNotFoundException {
        String firstName="Adekunle";
        String lastName="Gold";
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUsers=appUserRepository.findByFirstNameAndLastName(firstName,lastName,pageable);

        if (appUsers.isEmpty()){
            throw new AppUserNotFoundException("User with firstname "+firstName+" and lastname "+lastName+" Not Found");
        }
        log.info("Record of User with firstname: {} and lastname: {}-> {}", firstName,lastName,appUsers);
    }

    @Test
    void testThatYouCanFindUserByUsername() throws AppUserNotFoundException {
        String username="adekunlegold";

        appUser=appUserRepository.findByUsername(username);

        if (appUser==null){
            throw new AppUserNotFoundException("User with username "+username+" Not Found");
        }
        assertEquals(username, appUser.getUsername());
        log.info("User with username : {}", appUser);
    }

    @Test
    void testThatYouCanFindUserByEmail() throws AppUserNotFoundException {
        String email="ericmoore@gmail.com";

        appUser=appUserRepository.findByEmail(email);

        if (appUser==null){
            throw new AppUserNotFoundException("User with email "+email+" Not Found");
        }
        assertEquals(email, appUser.getEmail());
        log.info("User with email : {}", appUser);
    }

    @Test
    void testThatYouCanFindUserByPhone() throws AppUserNotFoundException {
        String phone="08123456326";

        appUser=appUserRepository.findByPhone(phone);

        if (appUser==null){
            throw new AppUserNotFoundException("User with phone "+phone+" Not Found");
        }
        assertEquals(phone, appUser.getPhone());
        log.info("User with phone : {}", appUser);
    }

    @Test
    void testThatYouCanFindUserStatus() throws AppUserNotFoundException {
        Boolean statusReg=Boolean.TRUE;
        Pageable pageable=PageRequest.of(0, 10);
        List<AppUser> regStatus=appUserRepository.findByStatus(statusReg,pageable);

        if (regStatus.isEmpty()){
            throw new AppUserNotFoundException("User with phone "+statusReg+" Not Found");
        }
        log.info("User with registration status : {}", regStatus);
    }

    @Test
    void testThatYouCanFindUserByType() throws AppUserNotFoundException {
        UserType userType=UserType.ADMIN; //test to fail
        Pageable pageable=PageRequest.of(0, 10);
        List<AppUser> appUserPage=appUserRepository.findByUserType(userType, pageable);

        if (appUserPage.isEmpty()){
            throw new AppUserNotFoundException("User with phone "+userType+" Not Found");
        }
        log.info("User Type: {}", appUserPage);
    }

    @Test
    void testThatYouCanFindAllUsers(){
        Pageable pageable= PageRequest.of(0, 10);
        Page<AppUser> appUserPage=appUserRepository.findAll(pageable);
        log.info("Users: {}", appUserPage.toList());
    }

    @Test
    void testToCountAllUsers(){
        List<AppUser> appUsers=appUserRepository.findAll();

        Long numberOfUsers=appUsers.stream().count();

        log.info("Number of users: {}", numberOfUsers);
    }

    @Test
    void testThatYouCanDeleteUserById() throws AppUserNotFoundException {
        Long id=1L;
        appUserRepository.deleteById(id);
        Optional<AppUser>optionalAppUser=appUserRepository.findById(id);

        if (optionalAppUser.isPresent()){
            throw new AppUserNotFoundException("User ID "+id+" Not Deleted");
        }
    }

    @Test
    void testThatYouCanDeleteAllUsers() {
        appUserRepository.deleteAll();;
    }

}