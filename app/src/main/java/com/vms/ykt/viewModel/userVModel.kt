package com.vms.ykt.viewModel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.vms.ykt.yktStuMobile.zjy.zjyUser
import com.vms.ykt.yktStuWeb.Cqooc.userInfo

class userVModel(savedStateHandle: SavedStateHandle?) :
    BaseViewModel<String>() {
    private val zjyUserKey = "zjyUserKey";
    var zjyUser: zjyUser? = null;
    private val cqoocUserKey = "cqoocUserKey";
    var cqoocUser: userInfo? = null;
    private var Handle: SavedStateHandle? = null

    init {
        Handle = savedStateHandle;
    }

    fun SGcqoocUser(cqoocUsers: userInfo? = null): userInfo? {
        var cqoocUser: userInfo? = null;
        Handle?.let {
            if (it.get<userInfo>(cqoocUserKey) == null) {
                it.set(cqoocUserKey, cqoocUsers);
                cqoocUser = it.get<userInfo>(cqoocUserKey);
            } else {
                cqoocUser = it.get<userInfo>(cqoocUserKey);
            }
        }
        return cqoocUser;
    }

    fun SGzjyUser(zjyUsers: zjyUser? = null): zjyUser? {
        var zjyUser: zjyUser? = null;
        Handle?.let {
            if (it.get<zjyUser>(zjyUserKey) == null) {
                it.set(zjyUserKey, zjyUsers);
                zjyUser = it.get<zjyUser>(zjyUserKey);
            } else {
                zjyUser = it.get<zjyUser>(zjyUserKey);
            }
        }
        return zjyUser;
    }

}