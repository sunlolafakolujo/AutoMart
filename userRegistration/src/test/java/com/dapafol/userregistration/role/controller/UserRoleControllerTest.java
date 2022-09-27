package com.dapafol.userregistration.role.controller;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.exception.AppUserNotFoundException;
import com.dapafol.userregistration.appuser.service.AppUserService;
import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import com.dapafol.userregistration.role.service.UserRoleService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class UserRoleControllerTest {
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private AppUserService appUserService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    AppUser appUser;
    UserRole userRole;
    @BeforeEach
    void setUp() {
        appUser=new AppUser();
        userRole=new UserRole();
    }

    @Test
    void testThatWhenYouCallSaveRoleMethod_thenRoleIsSave() throws Exception {
        userRole.setRoleName("GOVERNMENT");

        this.mockMvc.perform(post("/api/roles/saveRole")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(userRole)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.roleName", is("GOVERNMENT")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetRoleByIdMethod_thenRoleIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/roles/findRoleById/{id}",1)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.roleName", is("BUYER")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetRoleByNameMethod_thenRoleIsReturned() throws Exception {
        this.mockMvc.perform(get("/api/roles/findRoleByName/{name}","ADMIN")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.roleName", is("ADMIN")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllRolesMethod_thenRolesAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/roles/findAllRoles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(4)))
                .andExpect(jsonPath("$[0].roleName", is("BUYER")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallUpdateRole_thenRoleIsUpdated() throws UserRoleNotFoundException, Exception {
        Long id=3L;
        userRole=userRoleService.findRoleById(id);
        userRole.setRoleName("SENIOR ADMIN");
        userRoleService.updateRole(userRole,id);
        this.mockMvc.perform(put("/api/roles/updateRole/{id}", 3)
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsBytes(userRole)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(userRole.getId()))
                .andExpect(jsonPath("$.roleName",is("SENIOR ADMIN")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteRoleByIdMethod_thenRoleIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/roles/deleteRoleById/{id}",3)
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllRolesMethod_thenRoleIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/roles/deleteAllRoles")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}