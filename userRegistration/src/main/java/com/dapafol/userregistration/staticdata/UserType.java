package com.dapafol.userregistration.staticdata;

public enum UserType {
    SELLER("SELLER"),
    BUYER("BUYER"),
    ADMIN("ADMIN");

    private final String userType;

    UserType(String userType) {
        this.userType = userType;
    }

    public String getUserType() {
        return userType;
    }

    @Override
    public String toString() {
        return "UserType{" +
                "userType='" + userType + '\'' +
                '}';
    }
}
