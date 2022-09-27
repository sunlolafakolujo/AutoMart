package com.dapafol.userregistration.role.repository;

import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Slf4j
@Sql(scripts = {"classpath:db/insert.sql"})
class UserRoleRepositoryTest {
    @Autowired
    private UserRoleRepository userRoleRepository;

    UserRole userRole;

    @BeforeEach
    void setUp() {
        userRole=new UserRole();
    }

    @Test
    void testThatYouCanSaveRole(){
        userRole.setRoleName("MANAGER");
        log.info("Role repo before saving: {}",userRole);
        assertDoesNotThrow(()->userRoleRepository.save(userRole));
        log.info("Role repo after saving: {}",userRole);
    }

    @Test
    void testThatYouCanFindRoleById() throws UserRoleNotFoundException {
        Long id=4L;
        userRole=userRoleRepository.findById(id).orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        assertEquals(id, userRole.getId());
        log.info("Role ID 1: {}",userRole);
    }

    @Test
    void testThatYouCanFindRoleByName(){
        String roleName="ADMIN";
        userRole=userRoleRepository.findByRoleName(roleName);
        assertEquals(roleName, userRole.getRoleName());
        log.info("Role Name: {}", userRole);
    }

    @Test
    void testThatYouCanFindAllRoles(){
        List<UserRole>  roles=userRoleRepository.findAll();
        log.info("Roles: {}",roles);
    }

    @Test
    void testThatYouCanUpdateRoles() throws UserRoleNotFoundException {
        Long id=3L;
        userRole=userRoleRepository.findById(id).orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        userRole.setRoleName("EMPLOYEE");
        assertDoesNotThrow(()->userRoleRepository.save(userRole));
        assertThat(userRole.getRoleName()).isEqualTo("EMPLOYEE");
        log.info("ROle name: {}", userRole.getRoleName());
    }

    @Test
    void testThatYouCanDeleteRoleById() throws UserRoleNotFoundException {
        Long id=3L;
        userRoleRepository.deleteById(id);
        Optional<UserRole> optionalUserRole=userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()){
            throw new UserRoleNotFoundException("Role ID "+id+" Not Deleted");
        }
    }

    @Test
    void testThatYouCanDeleteAllRoles(){
        userRoleRepository.deleteAll();
    }
}