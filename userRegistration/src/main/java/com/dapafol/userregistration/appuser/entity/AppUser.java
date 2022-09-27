package com.dapafol.userregistration.appuser.entity;

import com.dapafol.userregistration.baseaudit.BaseAudit;
import com.dapafol.userregistration.contact.entity.Address;
import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.staticdata.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * * created date: 08:09:2022
 * *author: Sunlola E. Fakolujo
 */

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppUser extends BaseAudit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private String userId;

    private String firstName;

    private String lastName;

    @Column(unique = true)
    private String username;

    private String password;

    @Email
    @Column(unique = true)
    private String email;

    @Column(unique = true)
    private String phone;

    private Boolean isEnabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id",
            nullable = false,
            referencedColumnName = "id",
            foreignKey = @ForeignKey(name="FK_ADDRESS"))
    private Address address;

    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(name="app_user_user_roles",
    joinColumns=@JoinColumn(name = "app_user_id"),
            inverseJoinColumns = @JoinColumn(name = "user_role_id"))
    private List<UserRole> userRoles=new ArrayList<>();
}
