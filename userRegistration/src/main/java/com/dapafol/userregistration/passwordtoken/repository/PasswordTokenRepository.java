package com.dapafol.userregistration.passwordtoken.repository;

import com.dapafol.userregistration.passwordtoken.entity.PasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PasswordTokenRepository extends JpaRepository<PasswordToken, Long>,PasswordTokenRepositoryCustom {
}
