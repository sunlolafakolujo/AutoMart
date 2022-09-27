package com.dapafol.userregistration.role.service;

import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserRoleService {
    UserRole saveRole(UserRole role) throws UserRoleNotFoundException;
    UserRole findRoleById(Long id) throws UserRoleNotFoundException;
    UserRole findRoleByName(String roleName) throws UserRoleNotFoundException;
    List<UserRole> findAllRoles(Pageable pageable);
    void addRoleToUser(String username, String roleName);
    UserRole updateRole(UserRole userRole, Long id) throws UserRoleNotFoundException;
    void deleteRoleById(Long id) throws UserRoleNotFoundException;
    void deleteAllRoles();
}
