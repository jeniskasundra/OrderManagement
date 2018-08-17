package com.si.ordermanagement.permission;

import android.content.Context;
import android.content.Intent;
import com.squareup.otto.Subscribe;

public class MarshMallowInstance {


    public MarshMallowPermissionListener listener;
    public String[] permissions;
    public String rationaleMessage;
    public String denyMessage;
    public String settingButtonText;
    public boolean hasSettingBtn = true;

    public String deniedCloseButtonText;
    public String rationaleConfirmText;
    public Context context;

    public MarshMallowInstance(Context context) {
        this.context = context;
        MarshMallowBusProvider.getInstance().register(this);

        deniedCloseButtonText = "Close";
        rationaleConfirmText = "Confirm";
    }

    public void checkPermissions() {
        MarshMallowLogUtils.d("");

        Intent intent = new Intent(context, MarshMallowPermissionActy.class);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_PERMISSIONS, permissions);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_RATIONALE_MESSAGE, rationaleMessage);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_DENY_MESSAGE, denyMessage);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_PACKAGE_NAME, context.getPackageName());
        intent.putExtra(MarshMallowPermissionActy.EXTRA_SETTING_BUTTON, hasSettingBtn);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_DENIED_DIALOG_CLOSE_TEXT, deniedCloseButtonText);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_RATIONALE_CONFIRM_TEXT, rationaleConfirmText);
        intent.putExtra(MarshMallowPermissionActy.EXTRA_SETTING_BUTTON_TEXT, settingButtonText);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Subscribe
    public void onPermissionResult(MarshMallowPermissionEvent event) {
        MarshMallowLogUtils.d("");
        if (event.hasPermission()) {
            listener.onPermissionGranted();
        } else {
            listener.onPermissionDenied(event.getDeniedPermissions());
        }
        MarshMallowBusProvider.getInstance().unregister(this);
    }

}
