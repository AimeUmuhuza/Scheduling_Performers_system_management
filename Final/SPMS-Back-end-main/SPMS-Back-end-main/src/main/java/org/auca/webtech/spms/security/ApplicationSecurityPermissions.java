package org.auca.webtech.spms.security;

public enum ApplicationSecurityPermissions {
	 VIEW_USER("user:read"), READ_USER("user:write"),ACCESS_EVENT("user:read-write"),
	 VIEW_TIME("time:read");

    private String permission;
    ApplicationSecurityPermissions(String permission) {
        this.permission=permission;
    }

    public String getPermission() {
        return permission;
    }
}
