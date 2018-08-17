package com.si.ordermanagement.permission;

import java.util.ArrayList;

public class MarshMallowPermissionEvent {

    public boolean permission;
    public ArrayList<String> deniedPermissions;

    public MarshMallowPermissionEvent(boolean permission, ArrayList<String> deniedPermissions) {
        this.permission = permission;
        this.deniedPermissions = deniedPermissions;
    }

    public boolean hasPermission() {
        return permission;
    }

    public ArrayList<String> getDeniedPermissions() {
        return deniedPermissions;
    }

}
