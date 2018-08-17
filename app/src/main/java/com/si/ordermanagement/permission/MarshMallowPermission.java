package com.si.ordermanagement.permission;

import android.content.Context;
import android.os.Build;
import android.support.annotation.StringRes;

public class MarshMallowPermission {

    private static MarshMallowInstance instance;

    public MarshMallowPermission(Context context) {
        instance = new MarshMallowInstance(context);
    }

    public MarshMallowPermission setPermissionListener(MarshMallowPermissionListener listener) {

        instance.listener = listener;
        return this;
    }

    public MarshMallowPermission setPermissions(String... permissions) {

        instance.permissions = permissions;
        return this;
    }

    public MarshMallowPermission setRationaleMessage(String rationaleMessage) {

        instance.rationaleMessage = rationaleMessage;
        return this;
    }

    public MarshMallowPermission setRationaleMessage(@StringRes int stringRes) {
        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for RationaleMessage");

        instance.rationaleMessage = instance.context.getString(stringRes);
        return this;
    }

    public MarshMallowPermission setDeniedMessage(String denyMessage) {

        instance.denyMessage = denyMessage;
        return this;
    }

    public MarshMallowPermission setDeniedMessage(@StringRes int stringRes) {
        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for DeniedMessage");

        instance.denyMessage = instance.context.getString(stringRes);
        return this;
    }

    public MarshMallowPermission setGotoSettingButton(boolean hasSettingBtn) {

        instance.hasSettingBtn = hasSettingBtn;
        return this;
    }

    public MarshMallowPermission setGotoSettingButtonText(String rationaleConfirmText) {

        instance.settingButtonText = rationaleConfirmText;
        return this;
    }

    public MarshMallowPermission setGotoSettingButtonText(@StringRes int stringRes) {
        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for setGotoSettingButtonText");

        instance.settingButtonText = instance.context.getString(stringRes);

        return this;
    }

    public MarshMallowPermission setRationaleConfirmText(String rationaleConfirmText) {

        instance.rationaleConfirmText = rationaleConfirmText;
        return this;
    }

    public MarshMallowPermission setRationaleConfirmText(@StringRes int stringRes) {
        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for RationaleConfirmText");

        instance.rationaleConfirmText = instance.context.getString(stringRes);

        return this;
    }

    public MarshMallowPermission setDeniedCloseButtonText(String deniedCloseButtonText) {

        instance.deniedCloseButtonText = deniedCloseButtonText;
        return this;
    }

    public MarshMallowPermission setDeniedCloseButtonText(@StringRes int stringRes) {
        if (stringRes <= 0)
            throw new IllegalArgumentException("Invalid value for DeniedCloseButtonText");

        instance.deniedCloseButtonText = instance.context.getString(stringRes);

        return this;
    }

    public void check() {
        if (instance.listener == null) {
            throw new NullPointerException("You must setPermissionListener() on MarshMallowPermission");
        } else if (MarshMallowLogUtils.isEmpty(instance.permissions)) {
            throw new NullPointerException("You must setPermissions() on MarshMallowPermission");
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            MarshMallowLogUtils.d("preMarshmallow");
            instance.listener.onPermissionGranted();

        } else {
            MarshMallowLogUtils.d("Marshmallow");
            instance.checkPermissions();
        }
    }

}
