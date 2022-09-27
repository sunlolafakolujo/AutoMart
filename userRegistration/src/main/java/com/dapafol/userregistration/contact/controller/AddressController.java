package com.dapafol.userregistration.contact.controller;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.contact.entity.AddressDto;
import com.dapafol.userregistration.contact.entity.NewAddress;
import com.dapafol.userregistration.contact.entity.UpdateAddress;
import com.dapafol.userregistration.contact.exception.AddressException;
import com.dapafol.userregistration.contact.service.AddressService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "api/addresses")
@RequiredArgsConstructor
public class AddressController {
    private final AddressService addressService;
    private final ModelMapper modelMapper;

    @PostMapping("/saveAddress")
    public ResponseEntity<NewAddress> saveAddress(@RequestBody @Valid NewAddress newAddress){
        Address address=modelMapper.map(newAddress, Address.class);
        Address post=addressService.saveAddress(address);
        NewAddress posted=modelMapper.map(post, NewAddress.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findAddressById/{id}")
    public ResponseEntity<AddressDto> getAddressById(@PathVariable(value = "id") Long id) throws AddressException {
        Address address=addressService.findAddressById(id);
        AddressDto addressDto=convertAddressToDto(address);

        return new ResponseEntity<>(addressDto, HttpStatus.OK);
    }

    @GetMapping("/findAllAddresses")
    public ResponseEntity<List<AddressDto>> getAllAddresses(){
        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(addressService.findAllAddresses(pageable)
                .stream()
                .map(this::convertAddressToDto)
                .collect(Collectors.toList()), HttpStatus.OK);
    }

    @PutMapping("/updateAddressById/{id}")
    public ResponseEntity<UpdateAddress> updateAddress(@RequestBody @Valid UpdateAddress updateAddress,
                                                       @PathVariable(value = "id") Long id) throws AddressException {
        Address address=modelMapper.map(updateAddress, Address.class);
        Address post=addressService.updateAddress(address, id);
        UpdateAddress posted=modelMapper.map(post, UpdateAddress.class);

        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteAddressById/{id}")
    public ResponseEntity<?> deleteAddressById(@PathVariable(value = "id") Long id) throws AddressException {
        addressService.deleteAddressById(id);

        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllAddresses")
    public ResponseEntity<?> deleteAllAddresses() {
        addressService.deleteAllAddress();

        return ResponseEntity.noContent().build();
    }


    private AddressDto convertAddressToDto(Address address){
        AddressDto addressDto=new AddressDto();

        addressDto.setHouseNumber(address.getHouseNumber());
        addressDto.setStreetName(address.getStreetName());
        addressDto.setCity(address.getCity());
        addressDto.setLandMark(address.getLandMark());
        addressDto.setStateProvince(address.getStateProvince());
        addressDto.setCountry(address.getCountry());

        return addressDto;
    }
}
