package com.vms.ykt.Util

import android.content.SharedPreferences


open class CacheUs {

    var flag: String="";

    constructor(mUserName: String?, mPassWord: String?) {
        this.flag = mUserName + mPassWord;
    }

    public fun readCacheUs(mPreferences: SharedPreferences?,flg:String): Array<String?>? {
        val usInfo = arrayOfNulls<String>(2)
        usInfo[0] = mPreferences?.getString(Tool.md5("us${flg+flag}".hashCode().toString()), "null");
        usInfo[1] = mPreferences?.getString(Tool.md5("ck${flg+flag}".hashCode().toString()), "null");
        return usInfo;
    }

    public fun writeCacheUs(mEditor: SharedPreferences.Editor?,flg:String, usi: String?, cki: String?): Boolean {
        mEditor?.putString(Tool.md5("us${flg+flag}".hashCode().toString()), usi);
        mEditor?.putString(Tool.md5("ck${flg+flag}".hashCode().toString()), cki);
        return mEditor!!.commit();
    }

}