package com.si.ordermanagement.permission;

import android.os.Handler;
import android.os.Looper;

import com.squareup.otto.Bus;

/**
 * Maintains a singleton instance for obtaining the bus. Ideally this would be
 * replaced with a more efficient means such as through injection directly into
 * interested classes.
 */
public final class MarshMallowBusProvider extends Bus {

    private static MarshMallowBusProvider instance;

    public static MarshMallowBusProvider getInstance() {
        if (instance == null)
            instance = new MarshMallowBusProvider();

        return instance;
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper());

    @Override
    public void post(final Object event) {
        if (Looper.myLooper() == Looper.getMainLooper()) {
            super.post(event);
        } else {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    MarshMallowBusProvider.getInstance().post(event);
                }
            });
        }

    }
}
