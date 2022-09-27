package com.dapafol.userregistration.appuser.controller;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.appuser.service.AppUserService;
import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.service.AddressService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AppUserControllerTest {
    @Autowired
    private AppUserService appUserService;

    @Autowired
    private AddressService addressService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private ObjectMapper objectMapper;

    AppUser appUser;
    Address address;

    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        address=new Address();
    }

    @Test
    void testThatWhenYouCallSaveUserMethod_thenUserIsSaved() throws Exception {
        address.setHouseNumber("30");
        address.setStreetName("Owokoniran Street, Ajao");
        address.setCity("Ikeja");
        address.setLandMark("7&8 Bus Stop");
        address.setStateProvince("Lagos");
        address.setCountry("Nigeria");

        appUser.setFirstName("Adeoti");
        appUser.setLastName("Segun");
        appUser.setUsername("adeotis@yahoo.com");
        appUser.setPassword("1234");
        appUser.setEmail("adeotis@yahoo.com");
        appUser.setPhone("08186286328");
        appUser.setIsEnabled(Boolean.TRUE);
        appUser.setAddress(address);

        this.mockMvc.perform(post("/api/users/seller/signUp")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(appUser)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.username", is("adeotis@yahoo.com")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByIdMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findById/{id}",2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.userType", is("BUYER")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByFirstName_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByFirstName/{firstName}","Suru")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].username",is("ericmoore")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByLastNameMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByLastName/{lastName}","Gold")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].username", is("adekunlegold")))
                .andReturn();
    }

    @Test
    void testThatWhenYouGetUserByFirstNameAndLastNameMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByFirstNameAndLastName/{firstName},{lastName}",
                        "Adekunle", "Gold")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$[0].username", is("adekunlegold")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByUsernameMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByUsername/{username}","ericmoore")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userType").value("BUYER"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByEmailMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByEmail/{email}","adekunleg@live.com")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.userType").value("SELLER"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByPhoneMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByPhone/{phone}","08123456326")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("ericmoore@gmail.com"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByStatusMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findUserByStatus/{status}",Boolean.TRUE)
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].email").value("adekunleg@live.com"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetUserByTypeMethod_thenUserIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findByType/{userType}","BUYER")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(1)))
                .andExpect(jsonPath("$[0].phone").value("08123456326"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllUsersMethod_thenUserAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/users/findAllUsers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].phone").value("08023452673"))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallUpdateAppUserMethod_thenUserIsUpdated() throws AppUserNotFoundException, Exception {
        Long id=2L;
        appUser=appUserService.findUserById(id);
        appUser.setPassword("akin@1978");
        appUserService.updateUser(appUser, id);
        this.mockMvc.perform(put("/api/users/updateUser/{id}",2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(appUser)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(appUser.getId()))
                .andExpect(jsonPath("$.password", is("akin@1978")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteUserByIdMethod_thenUserIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/users/deleteById/{id}",2)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllUsersMethod_thenUsersAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/users/deleteAllUsers")
                        .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}