package com.dapafol.userregistration.contact.service;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.exception.AddressException;
import com.dapafol.userregistration.contact.repository.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService{

    @Autowired
    private AddressRepository addressRepository;

    @Override
    public Address saveAddress(Address address) {
        return addressRepository.save(address);
    }

    @Override
    public Address findAddressById(Long id) throws AddressException {
        Address address=addressRepository.findById(id).orElseThrow(() ->new AddressException("Invalid id"));
        return address;
    }

    @Override
    public List<Address> findAllAddresses(Pageable pageable) {
        pageable= PageRequest.of(0, 10);
        Page<Address> addressPage=addressRepository.findAll(pageable);
        return addressPage.toList();
    }

    @Override
    public Address updateAddress(Address address, Long id) throws AddressException {
        Address savedAddress=addressRepository.findById(id).orElseThrow(() ->new AddressException("Invalid id"));
        if (Objects.nonNull(address.getHouseNumber()) && !"".equalsIgnoreCase(address.getHouseNumber())){
            savedAddress.setHouseNumber(address.getHouseNumber());
        }if (Objects.nonNull(address.getStreetName()) && !"".equalsIgnoreCase(address.getStreetName())){
            savedAddress.setStreetName(address.getStreetName());
        }if (Objects.nonNull(address.getCity()) && !"".equalsIgnoreCase(address.getCity())){
            savedAddress.setCity(address.getCity());
        }if (Objects.nonNull(address.getLandMark()) && !"".equalsIgnoreCase(address.getLandMark())){
            savedAddress.setLandMark(address.getLandMark());
        }if (Objects.nonNull(address.getStateProvince()) && !"".equalsIgnoreCase(address.getStateProvince())){
            savedAddress.setStateProvince(address.getStateProvince());
        }if (Objects.nonNull(address.getCountry()) && !"".equalsIgnoreCase(address.getCountry())){
            savedAddress.setCountry(address.getCountry());
        }
        return addressRepository.save(savedAddress);
    }

    @Override
    public void deleteAddressById(Long id) throws AddressException {
        addressRepository.deleteById(id);
        Optional<Address> optionalAddress=addressRepository.findById(id);
        if (optionalAddress.isPresent()){
            throw new AddressException("Address ID "+id+" Not Deleted");
        }
    }

    @Override
    public void deleteAllAddress() {
        addressRepository.deleteAll();
    }
}
