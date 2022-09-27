package com.dapafol.userregistration.role.controller;

import com.dapafol.userregistration.role.entity.NewUserRole;
import com.dapafol.userregistration.role.entity.UpdateUserRole;
import com.dapafol.userregistration.role.entity.UserRole;
import com.dapafol.userregistration.role.entity.UserRoleDto;
import com.dapafol.userregistration.role.exception.UserRoleNotFoundException;
import com.dapafol.userregistration.role.service.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/roles")
@RequiredArgsConstructor
public class UserRoleController {
    private final UserRoleService userRoleService;
    private final ModelMapper modelMapper;

    @PostMapping("/saveRole")
    public ResponseEntity<NewUserRole> saveRole(@RequestBody @Valid NewUserRole newUserRole) throws UserRoleNotFoundException {
        UserRole userRole=modelMapper.map(newUserRole, UserRole.class);
        UserRole post=userRoleService.saveRole(userRole);
        NewUserRole posted=modelMapper.map(post, NewUserRole.class);
        return new ResponseEntity<>(posted, HttpStatus.CREATED);
    }

    @GetMapping("/findRoleById/{id}")
    public ResponseEntity<UserRoleDto> getRoleById(@PathVariable(value = "id") Long id) throws UserRoleNotFoundException {
        UserRole userRole= userRoleService.findRoleById(id);
        UserRoleDto userRoleDto=convertUserRoleToDto(userRole);
        return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
    }

    @GetMapping("/findRoleByName/{name}")
    public ResponseEntity<UserRoleDto> getRoleByName(@PathVariable(value = "name")String roleName)
                                                                throws UserRoleNotFoundException {
        UserRole userRole= userRoleService.findRoleByName(roleName);
        UserRoleDto userRoleDto=convertUserRoleToDto(userRole);
        return new ResponseEntity<>(userRoleDto, HttpStatus.OK);
    }

    @GetMapping("/findAllRoles")
    public ResponseEntity<List<UserRoleDto>> getAllRoles(){
        Pageable pageable= PageRequest.of(0, 10);
        return new ResponseEntity<>(userRoleService.findAllRoles(pageable)
                .stream()
                .map(this::convertUserRoleToDto)
                .collect(Collectors.toList()),HttpStatus.OK);
    }

    @PutMapping("/updateRole/{id}")
    public ResponseEntity<UpdateUserRole> updateRole(@RequestBody UpdateUserRole updateUserRole,
                                                     @PathVariable(value = "id")Long id)
                                                    throws UserRoleNotFoundException {
        UserRole userRole=modelMapper.map(updateUserRole, UserRole.class);
        UserRole post=userRoleService.updateRole(userRole, id);
        UpdateUserRole posted=modelMapper.map(post, UpdateUserRole.class);
        return new ResponseEntity<>(posted, HttpStatus.OK);
    }

    @DeleteMapping("/deleteRoleById/{id}")
    public ResponseEntity<?> deleteRoleById(@PathVariable(value = "id") Long id) throws UserRoleNotFoundException {
        userRoleService.deleteRoleById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAllRoles")
    public ResponseEntity<?> deleteAllRoles(){
        userRoleService.deleteAllRoles();
        return ResponseEntity.noContent().build();
    }




    private UserRoleDto convertUserRoleToDto(UserRole userRole){
        UserRoleDto userRoleDto=new UserRoleDto();
        userRoleDto.setRoleName(userRole.getRoleName());
        return userRoleDto;
    }
}
