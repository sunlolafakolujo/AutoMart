package com.dapafol.userregistration.appuser.entity;

import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.staticdata.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewAppUser {

    private UserType userType;

    private String firstName;

    private String lastName;

    private String username;

    private String password;

    private String email;

    private String phone;

    private String matchingPassword;

    private Address address;

    private List<UserRole> userRoles;


}
