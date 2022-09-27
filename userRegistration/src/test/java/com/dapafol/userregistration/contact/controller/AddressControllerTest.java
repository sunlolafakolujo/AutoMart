package com.dapafol.userregistration.contact.controller;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.exception.AddressException;
import com.dapafol.userregistration.contact.service.AddressService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AddressControllerTest {
    @Autowired
    AddressService addressService;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    Address address;

    @BeforeEach
    void setUp() {
        address=new Address();
    }

    @Test
    void testThatWhenYouCallSaveAddress_thenAddressIsSaved() throws Exception {
        address.setHouseNumber("314");
        address.setStreetName("Ozumba Mbadiwe Avenue, Victoria Island");
        address.setCity("Eti Osa");
        address.setLandMark("Mobil House");
        address.setStateProvince("Lagos");
        address.setCountry("Nigeria");

        this.mockMvc.perform(post("/api/addresses/saveAddress")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(address)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.city", is("Eti Osa")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAddressById_thenAddressIsReturned() throws Exception {

        this.mockMvc.perform(get("/api/addresses/findAddressById/2")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.city", is("Surulere")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallGetAllAddressesMethod_thenAddressesAreReturned() throws Exception {
        this.mockMvc.perform(get("/api/addresses/findAllAddresses")
                .header(HttpHeaders.AUTHORIZATION, "Bearer "))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.*", hasSize(2)))
                .andExpect(jsonPath("$[0].city", is("Ebute Metta")))
                .andReturn();
    }

    @Test
    void testThatWhenYoCallUpdateAddressMethod_thenAddressIsUpdated() throws AddressException, Exception {
        Long id=1L;
        address=addressService.findAddressById(id);
        address.setHouseNumber("Block 202, Flat 2");
        address.setStreetName("Amuwo Odofin Housing Estate, Mile 2");
        address.setCity("Amuwo Odofin");
        address.setLandMark("Mile 2 Bus Stop");
        address.setStateProvince("Lagos");
        address.setCountry("Nigeria");

        addressService.updateAddress(address, id);
        this.mockMvc.perform(put("/api/addresses/updateAddressById/1")
                .header(HttpHeaders.AUTHORIZATION, "Bearer ")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsBytes(address)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.houseNumber", is("Block 202, Flat 2")))
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAddressByIdMethod_thenAddressIsDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/addresses/deleteAddressById/1"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }

    @Test
    void testThatWhenYouCallDeleteAllAddressesMethod_thenAllAddressesAreDeleted() throws Exception {
        this.mockMvc.perform(delete("/api/addresses/deleteAllAddresses"))
                .andDo(print())
                .andExpect(status().isNoContent())
                .andReturn();
    }
}