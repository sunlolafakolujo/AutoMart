package com.dapafol.userregistration.appuser.controller;

import com.dapafol.userregistration.role.service.UserRoleService;
import com.dapafol.userregistration.appuser.entity.RoleToUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/api/role")
@RequiredArgsConstructor
public class RoleToUserController {
    private final UserRoleService userRoleService;

    @PostMapping("/roleToUser/{email}")
    public ResponseEntity<?> addRoleToUser(@PathVariable(value = "email")String email,
                                           @RequestBody RoleToUser roleToUser){
        userRoleService.addRoleToUser(email, roleToUser.getRoleName());
        return ResponseEntity.ok().build();
    }
}
