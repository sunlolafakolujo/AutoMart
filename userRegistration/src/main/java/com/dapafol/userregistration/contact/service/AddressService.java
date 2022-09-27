package com.dapafol.userregistration.contact.service;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.exception.AddressException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AddressService {

    Address saveAddress(Address address);

    Address findAddressById(Long id) throws AddressException;

    List<Address> findAllAddresses(Pageable pageable);

    Address updateAddress(Address address, Long id) throws AddressException;

    void deleteAddressById(Long id) throws AddressException;

    void deleteAllAddress();
}
