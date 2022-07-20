package com.vms.ykt.hookTest;

import android.app.Application;
import android.provider.Settings;
import android.util.Log;

public class hookDogApplication extends Application {
    String TAG = hookDogApplication.class.getName()+"==>>";
    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "onCreate: ");
    }
}
