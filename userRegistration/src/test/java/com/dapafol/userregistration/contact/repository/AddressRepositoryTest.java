package com.dapafol.userregistration.contact.repository;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.exception.AddressException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AddressRepositoryTest {

    @Autowired
    private AddressRepository addressRepository;

    Address address;

    @BeforeEach
    void setUp() {
        address=new Address();
    }

    @Test
    void testThatYouCanSaveAddress(){
        address.setHouseNumber("17");
        address.setStreetName("Wakeman Street, Alagomeji");
        address.setCity("Ebute Metta");
        address.setLandMark("AP Club");
        address.setStateProvince("Lagos");
        address.setCountry("Nigeria");

        log.info("Address repo before saving {}", address);

        assertDoesNotThrow(()->addressRepository.save(address));

        assertEquals("17", address.getHouseNumber());

        log.info("Address repo after saving {}", address);
    }

    @Test
    void testThatYouCanFindAddressById() throws AddressException {
        Long id=1L;
        address=addressRepository.findById(id).orElseThrow(()->new AddressException("Invalid ID: "+ id));

        assertEquals("2", address.getHouseNumber());

        log.info("Address ID: {}", address);
    }

    @Test
    void testThatYouCanFindAllAddresses(){

        List<Address>addresses=addressRepository.findAll();

        log.info("Addresses: {} ", addresses);
    }

    @Test
    void testThatYouCanCountAddresses(){
        Long numberOfAddresses=addressRepository.count();

        log.info("Address count: {}",numberOfAddresses);
    }

    @Test
    void testThatYouCanUpdateAddress() throws AddressException {
        Long id=2L;
        address=addressRepository.findById(id).orElseThrow(()->new AddressException("Invalid ID"));
        address.setHouseNumber("5");
        assertDoesNotThrow(()->addressRepository.save(address));
        assertThat(address.getHouseNumber()).isEqualTo("5");

        log.info("New house number: {}", address.getHouseNumber());
    }

    @Test
    void testThatYouCanDeleteAddressById() throws AddressException {
        Long id=1L;
        addressRepository.deleteById(id);
        Optional<Address> optionalAddress=addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            throw new AddressException("Address ID "+id+"Not Deleted");
        }
    }

    @Test
    void testThatYouCanDeleteAllAddress(){
        addressRepository.deleteAll();
    }
}