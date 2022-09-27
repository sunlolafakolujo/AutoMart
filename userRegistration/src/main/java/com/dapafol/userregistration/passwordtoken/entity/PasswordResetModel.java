package com.dapafol.userregistration.passwordtoken.entity;

import lombok.Data;

@Data
public class PasswordResetModel {
    private String username;//or email
    private String oldPassword;
    private String newPassword;
}
