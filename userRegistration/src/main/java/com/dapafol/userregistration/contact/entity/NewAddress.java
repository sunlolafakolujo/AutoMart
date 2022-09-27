package com.dapafol.userregistration.contact.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAddress {

    private String houseNumber;

    private String streetName;

    private String city;

    private String landMark;

    private String stateProvince;

    private String country;
}
