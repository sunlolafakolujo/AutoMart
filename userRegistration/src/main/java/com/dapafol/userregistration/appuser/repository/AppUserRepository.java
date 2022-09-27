package com.dapafol.userregistration.appuser.repository;

import com.dapafol.userregistration.appuser.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser,Long>, AppUserRepositoryCustom {
}
