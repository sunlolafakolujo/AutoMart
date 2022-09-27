package com.dapafol.userregistration.role.repository;

import com.dapafol.userregistration.role.entity.UserRole;
import org.springframework.data.jpa.repository.Query;

public interface UserRoleRepositoryCustom {
    @Query("From UserRole u Where u.roleName=?1")
    UserRole findByRoleName(String roleName);
}
