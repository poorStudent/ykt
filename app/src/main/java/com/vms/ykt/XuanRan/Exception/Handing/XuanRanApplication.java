package com.vms.ykt.XuanRan.Exception.Handing;

import android.app.*;

public class XuanRanApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler.init(this, "/sdcard/XuanRan/" + "NoKnow Log" + "/Log/");
    }

}
