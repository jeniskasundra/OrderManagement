package com.si.ordermanagement.permission;

import java.util.ArrayList;

public interface MarshMallowPermissionListener {

    void onPermissionGranted();

    void onPermissionDenied(ArrayList<String> deniedPermissions);

}
