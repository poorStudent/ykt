package com.vms.ykt;


import android.app.Application;
import android.mtp.MtpConstants;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class BaseViewModel<T> extends AndroidViewModel {
    protected MutableLiveData<List<T>> mMutableLiveDataTL;
    protected MutableLiveData<T> mMutableLiveDataT;
    protected List<MutableLiveData<T>> mLiveDataListTLL;
    protected MutableLiveData<T[]> mMutableLiveDataTA;

    public BaseViewModel(@NonNull @NotNull Application application, SavedStateHandle savedStateHandle) {
        super(application);

    }

    public List<MutableLiveData<T>> setLiveDataListTLL(List<MutableLiveData<T>> liveDataList) {
        if (mLiveDataListTLL == null) {
            mLiveDataListTLL = new ArrayList<>();
        }
        mLiveDataListTLL.addAll(liveDataList);
        return mLiveDataListTLL;
    }

    public List<MutableLiveData<T>> getLiveDataListTLL() {
        return mLiveDataListTLL;
    }

    public void cdLiveDataListTLL(List<MutableLiveData<T>> liveDataList) {
        if (mLiveDataListTLL == null) {
            mLiveDataListTLL = new ArrayList<>();
        }
        mLiveDataListTLL.clear();
        setLiveDataListTLL(liveDataList);

    }


    public MutableLiveData<T[]> getMutableLiveDataTA() {
        return mMutableLiveDataTA;
    }

    public MutableLiveData<T[]> setMutableLiveDataTA(T[] ts) {
        if (mMutableLiveDataTA == null) {
            mMutableLiveDataTA = new MutableLiveData<>();
        }
        mMutableLiveDataTA.setValue(ts);
        return mMutableLiveDataTA;
    }


    public MutableLiveData<List<T>> getMutableLiveDataTL() {
        return mMutableLiveDataTL;
    }

    public MutableLiveData<List<T>> setMutableLiveDataTL(List<T> list) {
        if (mMutableLiveDataTL == null) {
            mMutableLiveDataTL = new MutableLiveData<>();
        }
        mMutableLiveDataTL.setValue(list);
        return mMutableLiveDataTL;
    }


    public MutableLiveData<T> getMutableLiveDataT() {
        return mMutableLiveDataT;
    }

    public MutableLiveData<T> setMutableLiveDataT(T t) {
        if (mMutableLiveDataT == null) {
            mMutableLiveDataT = new MutableLiveData<>();
        }
        mMutableLiveDataT.setValue(t);
        return mMutableLiveDataT;
    }

}
