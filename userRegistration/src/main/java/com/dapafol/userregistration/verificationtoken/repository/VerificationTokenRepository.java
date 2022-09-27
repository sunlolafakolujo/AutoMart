package com.dapafol.userregistration.verificationtoken.repository;

import com.dapafol.userregistration.verificationtoken.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken,Long>, VerificationTokeRepositoryCustom {
}
