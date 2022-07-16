package com.vms.ykt;


import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;

import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import org.jetbrains.annotations.NotNull;

public class TestViewModel extends BaseViewModel<zjyUser>{

    public TestViewModel(@NonNull @NotNull Application application, SavedStateHandle savedStateHandle) {
        super(application, savedStateHandle);
    }

    public void addType(int i, int type){
        zjyUser vData=(getLiveDataListTLL().get(i)).getValue();
        vData.setType(vData.getType()+type);
    }

    public void setName(int i,String string){
        zjyUser vData=(getLiveDataListTLL().get(i)).getValue();
        vData.setUserName(string);
    }
}