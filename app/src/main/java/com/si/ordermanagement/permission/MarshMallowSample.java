package com.si.ordermanagement.permission;

import java.util.ArrayList;

/**
 * Created by dilip on 2/6/16.
 */
public class MarshMallowSample {

    /*
    * Complete Sample How to use this Permission our project
    * */
//    MarshMallowPermissionListener permissionlistener = new MarshMallowPermissionListener() {
//        @Override
//        public void onPermissionGranted() {
//            Toast.makeText(MarshMallowSample.this, "MarshMallowPermission Granted", Toast.LENGTH_SHORT).show();
//        }
//
//        @Override
//        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//            Toast.makeText(MarshMallowSample.this, "MarshMallowPermission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
//        }
//
//    };
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [MarshMallowPermission]")
//    .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();


    /*
    *  Common Permission Result Listener
    */
    MarshMallowPermissionListener permissionlistener = new MarshMallowPermissionListener() {
        @Override
        public void onPermissionGranted() {
//            Toast.makeText(MarshMallowSample.this, "MarshMallowPermission Granted", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onPermissionDenied(ArrayList<String> deniedPermissions) {
//            Toast.makeText(MarshMallowSample.this, "MarshMallowPermission Denied\n" + deniedPermissions.toString(), Toast.LENGTH_SHORT).show();
        }

    };


//=======================================  Type of Permission Request (Single , Multiple) ==============================//
    /*
    * Single Permission Request for
    * */
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [MarshMallowPermission]")
//    .setPermissions(android.Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();


    /*
    * Multiple Permission Request For
    * */
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [MarshMallowPermission]")
//    .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();


//=======================================  Type of check Permission process with dialog ==============================//
    /*
    * No Dialog only check Request Permission
    * */
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();


    /*
    * Dialog to Check Permission
    * */
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [MarshMallowPermission]")
//    .setPermissions(android.Manifest.permission.CAMERA, android.Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();

    /*
    * Rationale to Check Permission
    * */
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setRationaleMessage("we need permission for read contact and find your location")
//    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();


    /*
    * Rationale to Check Permission and Send Permission intent
    * */
//    new MarshMallowPermission(this)
//    .setPermissionListener(permissionlistener)
//    .setRationaleMessage("we need permission for read contact and find your location")
//    .setDeniedMessage("If you reject permission,you can not use this service\n\nPlease turn on permissions at [Setting] > [Permission]")
//    .setGotoSettingButtonText("go to setting.")
//    .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION)
//    .check();


}
