package com.vms.ykt.viewModel

import android.app.Application
import androidx.lifecycle.SavedStateHandle
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser
import com.vms.ykt.yktStuMobile.zjy.zjyUser
import com.vms.ykt.yktStuWeb.Cqooc.userInfo

class yktUserVM : BaseViewModel<String>() {
    var zjyUser: zjyUser? = null;
    var cqoocUser: userInfo? = null;
    var NewZjyUser : newZjyUser?=null;

}