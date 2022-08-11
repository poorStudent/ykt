package com.vms.ykt.TestActivity;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.SavedStateHandle;

import com.vms.ykt.viewModel.BaseViewModel;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import org.jetbrains.annotations.NotNull;

public class TestViewModel extends BaseViewModel<zjyUser> {


    public void addType(int i, int type){
        zjyUser vData=(getMutableLiveDataT().getValue());
        vData.setType(vData.getType()+type);
    }

    public void setName(int i,String string){
        zjyUser vData=(getMutableLiveDataT().getValue());
        vData.setUserName(string);
    }
}