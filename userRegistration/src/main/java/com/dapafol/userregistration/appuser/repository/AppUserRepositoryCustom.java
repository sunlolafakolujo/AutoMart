package com.dapafol.userregistration.appuser.repository;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.staticdata.UserType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface AppUserRepositoryCustom {

    @Query("From AppUser a Where a.firstName LIKE %?1%")
    List<AppUser> findByFirstNameLike(String firstName, Pageable pageable);

    @Query("From AppUser a Where a.lastName LIKE %?1%")
    List<AppUser> findByLastNameLike(String lastName, Pageable pageable);

    @Query("From AppUser a Where a.firstName=?1 AND a.lastName=?2")
    List<AppUser> findByFirstNameAndLastName(String firstName, String lastName, Pageable pageable);

    @Query("From AppUser a Where a.username=?1")
    AppUser findByUsername(String username);

    @Query("From AppUser a Where a.email=?1")
    AppUser findByEmail(String email);

    @Query("From AppUser a Where a.phone=?1")
    AppUser findByPhone(String phone);

    @Query("From AppUser a Where a.isEnabled=?1")
    List<AppUser> findByStatus(Boolean isEnabled, Pageable pageable);

    @Query("From AppUser a Where a.userType=?1")
    List<AppUser> findByUserType(UserType userType, Pageable pageable);
}
