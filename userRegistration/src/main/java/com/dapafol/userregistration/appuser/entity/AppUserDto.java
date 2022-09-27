package com.dapafol.userregistration.appuser.entity;

import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.staticdata.UserType;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUserDto {
    @JsonIgnore
    private Long id;

    private UserType userType;

    private String userId;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String houseNumber;

    private String streetName;

    private String city;

    private String landMark;

    private String stateProvince;

    private String country;

    private List<UserRole> userRoles;
}
