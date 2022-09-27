package com.dapafol.userregistration.contact.repository;

import com.dapafol.userregistration.contact.entity.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
}
