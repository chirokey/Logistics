package com.logistics.entity.user;

public enum UserRole {
    ADMIN,
    DRIVER;

    public static final String rolePrefix = "ROLE_";

    public String getRoleWithPrefix() {
        return rolePrefix+name();
    }

    public static class RoleString {
        public static final String ADMIN = rolePrefix + "ADMIN";
        public static final String DRIVER = rolePrefix + "DRIVER";
    }
}
