package com.dapafol.userregistration.role.service;

import com.dapafol.userregistration.appuser.entity.AppUser;
import com.dapafol.userregistration.appuser.repository.AppUserRepository;
import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import com.dapafol.userregistration.role.repository.UserRoleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
@Slf4j
public class UserRoleServiceImpl implements UserRoleService{
    @Autowired
    private UserRoleRepository userRoleRepository;
    @Autowired
    private AppUserRepository appUserRepository;

    @Override
    public UserRole saveRole(UserRole role) throws UserRoleNotFoundException {
        return userRoleRepository.save(role);
    }

    @Override
    public UserRole findRoleById(Long id) throws UserRoleNotFoundException {
        UserRole userRole=userRoleRepository.findById(id)
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        return userRole;
    }

    @Override
    public UserRole findRoleByName(String roleName) throws UserRoleNotFoundException {
        UserRole userRole=userRoleRepository.findByRoleName(roleName);
        if (userRole==null){
            throw new UserRoleNotFoundException("Role "+roleName+" Not Found");
        }
        return userRole;
    }

    @Override
    public List<UserRole> findAllRoles(Pageable pageable) {
        pageable= PageRequest.of(0, 10);
        Page<UserRole> userRolePage=userRoleRepository.findAll(pageable);
        return userRolePage.toList();
    }

    @Override
    public void addRoleToUser(String username, String roleName) {
        AppUser appUser=appUserRepository.findByUsername(username);
        UserRole userRole=userRoleRepository.findByRoleName(roleName);
        appUser.getUserRoles().add(userRole);
        appUserRepository.save(appUser);
    }

    @Override
    public UserRole updateRole(UserRole userRole, Long id) throws UserRoleNotFoundException {
        UserRole savedRole=userRoleRepository.findById(id)
                .orElseThrow(()->new UserRoleNotFoundException("Role Not Found"));
        if (Objects.nonNull(userRole.getRoleName()) && !"".equalsIgnoreCase(userRole.getRoleName())){
            savedRole.setRoleName(userRole.getRoleName());
        }
        return userRoleRepository.save(savedRole);
    }

    @Override
    public void deleteRoleById(Long id) throws UserRoleNotFoundException {
        userRoleRepository.deleteById(id);
        Optional<UserRole>optionalUserRole=userRoleRepository.findById(id);
        if (optionalUserRole.isPresent()){
            throw new UserRoleNotFoundException("Role is Not Deleted");
        }
    }

    @Override
    public void deleteAllRoles() {
        userRoleRepository.deleteAll();
    }
}
