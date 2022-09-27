package com.dapafol.userregistration.contact.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class AddressDto {

    @JsonIgnore
    private Long id;

    private String houseNumber;

    private String streetName;

    private String city;

    private String landMark;

    private String stateProvince;

    private String country;
}
