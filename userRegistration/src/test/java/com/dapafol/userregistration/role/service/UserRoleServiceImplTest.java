package com.dapafol.userregistration.role.service;

import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import com.dapafol.userregistration.role.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@Sql(scripts = {"classpath:db/insert.sql"})
@Slf4j
class UserRoleServiceImplTest {
    @Mock
    private UserRoleRepository userRoleRepository;

    @InjectMocks
    private UserRoleService userRoleService=new UserRoleServiceImpl();

    UserRole userRole;

    @BeforeEach
    void setUp() {
        userRole=new UserRole();
    }

    @Test
    void testThaYouCanMockSaveRoleMethod() throws UserRoleNotFoundException {
        when(userRoleRepository.save(userRole)).thenReturn(userRole);
        userRoleService.saveRole(userRole);
        ArgumentCaptor<UserRole> argumentCaptor=ArgumentCaptor.forClass(UserRole.class);
        verify(userRoleRepository, times(1)).save(argumentCaptor.capture());
        UserRole capturedRole=argumentCaptor.getValue();
        assertEquals(capturedRole, userRole);
    }

    @Test
    void testThaYouCanMockFindRoleByIdMethod() throws UserRoleNotFoundException {
        Long id=3L;
        when(userRoleRepository.findById(id)).thenReturn(Optional.of(userRole));
        userRoleService.findRoleById(id);
        verify(userRoleRepository,times(1)).findById(id);
    }

    @Test
    void testThaYouCanMockFindRoleByNameMethod() throws UserRoleNotFoundException {
        String roleName="SELLER";
        when(userRoleRepository.findByRoleName(roleName)).thenReturn(userRole);
        userRoleService.findRoleByName(roleName);
        verify(userRoleRepository, times(1)).findByRoleName(roleName);
    }

    @Test
    void testThaYouCanMockFindAllRolesMethod() {
        List<UserRole> userRoles=new ArrayList<>();
        Pageable pageable= PageRequest.of(0, 10);
        Page<UserRole> userRolePage=new PageImpl<>(userRoles);
        when(userRoleRepository.findAll(pageable)).thenReturn(userRolePage);
        userRoleService.findAllRoles(pageable);
        verify(userRoleRepository, times(1)).findAll(pageable);
    }

    @Test
    void testThatYouCanUpdateRole() throws UserRoleNotFoundException {
        Long id=1L;
        when(userRoleRepository.findById(id)).thenReturn(Optional.of(userRole));
        userRoleService.updateRole(userRole, id);
        verify(userRoleRepository, times(1)).save(userRole);
    }

    @Test
    void testThaYouCanMockDeleteRoleById() throws UserRoleNotFoundException {
        Long id=2L;
        doNothing().when(userRoleRepository).deleteById(id);
        userRoleService.deleteRoleById(id);
        verify(userRoleRepository, times(1)).deleteById(id);
    }

    @Test
    void deleteAllRoles() {
        doNothing().when(userRoleRepository).deleteAll();
        userRoleService.deleteAllRoles();
        verify(userRoleRepository, times(1)).deleteAll();
    }
}