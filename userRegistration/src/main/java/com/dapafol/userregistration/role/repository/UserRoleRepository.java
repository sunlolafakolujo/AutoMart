package com.dapafol.userregistration.role.repository;

import com.dapafol.userregistration.role.entity.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long>,UserRoleRepositoryCustom {
}
