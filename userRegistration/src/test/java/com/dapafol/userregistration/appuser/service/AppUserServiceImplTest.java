package com.dapafol.userregistration.appuser.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.appuser.repository.AppUserRepository;
import com.dapafol.userregistration.staticdata.UserType;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AppUserServiceImplTest {

    @Mock
    private AppUserRepository appUserRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AppUserService appUserService=new AppUserServiceImpl();

    AppUser appUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        appUser=new AppUser();
    }

    @Test
    void testThatYouCanMockSaveUserMethod() throws AppUserNotFoundException {
        appUser.setPassword(passwordEncoder.encode(appUser.getPassword()));
        when(appUserRepository.save(appUser)).thenReturn(appUser);
        appUserService.saveUser(appUser);
        ArgumentCaptor<AppUser> argumentCaptor=ArgumentCaptor.forClass(AppUser.class);
        verify(appUserRepository, times(1))
                .save(argumentCaptor.capture());
        AppUser capturesUser= argumentCaptor.getValue();

        assertEquals(capturesUser, appUser);
    }

    @Test
    void testThatYouCanMockFindUserByIdMethod() throws AppUserNotFoundException {
        Long id=1L;
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));
        appUserService.findUserById(id);
        verify(appUserRepository, times(1)).findById(id);
    }

    @Test
    void testThatYouCanMockFindUserByFirstNameLikeMethod() throws AppUserNotFoundException {
        String firstName="Su";
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUserList=new ArrayList<>();
        when(appUserRepository.findByFirstNameLike(firstName, pageable)).thenReturn(appUserList);
        appUserService.findUserByFirstName(firstName, pageable);
        verify(appUserRepository, times(1)).findByFirstNameLike(firstName, pageable);
    }

    @Test
    void testThatYouCanMockFindUserByLastNameLikeMethod() throws AppUserNotFoundException {
        String lastName="Er";
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUserList=new ArrayList<>();
        when(appUserRepository.findByLastNameLike(lastName, pageable)).thenReturn(appUserList);
        appUserService.findUserByLastName(lastName, pageable);
        verify(appUserRepository, times(1)).findByLastNameLike(lastName, pageable);
    }

    @Test
    void testThatYouCanMockFindUserByFirstNameAndLastNameMethod() throws AppUserNotFoundException {
        String firstName="Suru";
        String lastName="Eric";
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUserList=new ArrayList<>();
        when(appUserRepository.findByFirstNameAndLastName(firstName,lastName, pageable)).thenReturn(appUserList);
        appUserService.findUserByFirstAndLastName(firstName,lastName, pageable);
        verify(appUserRepository, times(1))
                .findByFirstNameAndLastName(firstName,lastName, pageable);
    }

    @Test
    void testThatYouCanMockFindUserByRegistrationStatusMethod() throws AppUserNotFoundException {
        Boolean regStatus=Boolean.FALSE;
        Pageable pageable= PageRequest.of(0, 10);
        List<AppUser> appUserList=new ArrayList<>();
        when(appUserRepository.findByStatus(regStatus, pageable)).thenReturn(appUserList);
        appUserService.findUserByRegistrationStatus(regStatus, pageable);
        verify(appUserRepository, times(1)).findByStatus(regStatus, pageable);
    }

    @Test
    void testThatYouCanMockFindUserByTypeMethod() throws AppUserNotFoundException {
        UserType userType=UserType.ADMIN;
        Pageable pageable=PageRequest.of(0, 10);
        List<AppUser> appUsers=new ArrayList<>();
        when(appUserRepository.findByUserType(userType,pageable)).thenReturn(appUsers);
        appUserService.findUserByType(userType, pageable);
        verify(appUserRepository, times(1)).findByUserType(userType, pageable);
    }

    @Test
    void testThatYouCanMockFindUserByUsernameMethod() throws AppUserNotFoundException {
        String username="adekunlegold";
        when(appUserRepository.findByUsername(username)).thenReturn(appUser);
        appUserService.findUserByUsername(username);
        verify(appUserRepository, times(1)).findByUsername(username);
    }

    @Test
    void testThatYouCanMockFindUserByEmailMethod() throws AppUserNotFoundException {
        String email="ericmoore@gmail.com";
        when(appUserRepository.findByEmail(email)).thenReturn(appUser);
        appUserService.findUserByEmail(email);
        verify(appUserRepository, times(1)).findByEmail(email);
    }

    @Test
    void testThatYouCanMockFindUserByPhoneMethod() throws AppUserNotFoundException {
        String phone="08123456326";
        when(appUserRepository.findByPhone(phone)).thenReturn(appUser);
        appUserService.findUserByPhone(phone);
        verify(appUserRepository, times(1)).findByPhone(phone);
    }

    @Test
    void testThatYouCanMockFindAllUsersMethod() {
        List<AppUser> appUsers=new ArrayList<>();
        Pageable pageable=PageRequest.of(0, 10);
        Page<AppUser> appUserPage=new PageImpl<>(appUsers);
        when(appUserRepository.findAll(pageable)).thenReturn(appUserPage);
        appUserService.findAllUsers(pageable);
        verify(appUserRepository, times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockUpdateUserMethod() throws AppUserNotFoundException {
        Long id=1L;
        when(appUserRepository.findById(id)).thenReturn(Optional.of(appUser));
        appUserService.updateUser(appUser, id);
        verify(appUserRepository, times(1)).save(appUser);
    }

    @Test
    void testThatYouCanMockDeleteUserByIdMethod() throws AppUserNotFoundException {
        Long id=1L;
        doNothing().when(appUserRepository).deleteById(id);
        appUserService.deleteUserById(id);
        verify(appUserRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllUsers() {
        doNothing().when(appUserRepository).deleteAll();
        appUserService.deleteAllUsers();
        verify(appUserRepository, times(1)).deleteAll();
    }
}