package com.dapafol.userregistration.contact.service;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.exception.AddressException;
import com.dapafol.userregistration.contact.repository.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class AddressServiceImplTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService=new AddressServiceImpl();

    Address address;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        address=new Address();
    }

    @Test
    void testThatYouCanMockSaveAddressMethod() {
        when(addressRepository.save(address)).thenReturn(address);
        addressService.saveAddress(address);
        ArgumentCaptor<Address> addressArgumentCaptor=ArgumentCaptor.forClass(Address.class);
        verify(addressRepository, Mockito.times(1))
                .save(addressArgumentCaptor.capture());
        Address capturedAddress=addressArgumentCaptor.getValue();
        assertEquals(capturedAddress, address);
    }

    @Test
    void testThatYouCanMockFindAddressByIdMethod() throws AddressException {
        Long id=2L;
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        addressService.findAddressById(id);
        verify(addressRepository, Mockito.times(1)).findById(id);
    }

    @Test
    void testThatYouCanMockFindAllAddressesMethod() {
        List<Address> addresses=new ArrayList<>();
        Pageable pageable= PageRequest.of(0, 10);
        Page<Address> addressPage=new PageImpl<>(addresses);
        when(addressRepository.findAll(pageable)).thenReturn(addressPage);
        addressService.findAllAddresses(pageable);
        verify(addressRepository, Mockito.times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanMockUpdateAddressMethod() throws AddressException {
        Long id=2L;
        when(addressRepository.findById(id)).thenReturn(Optional.of(address));
        addressService.updateAddress(address, id);
        verify(addressRepository, times(1)).save(address);
    }

    @Test
    void testThatYouCanMockDeleteAddressById() throws AddressException {
        Long id=1L;
        doNothing().when(addressRepository).deleteById(id);
        addressService.deleteAddressById(id);
        verify(addressRepository, times(1)).deleteById(id);
    }

    @Test
    void testThatYouCanMockDeleteAllAddress() {
        doNothing().when(addressRepository).deleteAll();
        addressService.deleteAllAddress();
        verify(addressRepository, times(1)).deleteAll();
    }
}