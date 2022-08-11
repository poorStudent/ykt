package com.vms.ykt.viewModel;



import androidx.lifecycle.MutableLiveData;

import androidx.lifecycle.ViewModel;


public class BaseViewModel<T> extends ViewModel {

    protected MutableLiveData<T> mMutableLiveDataT;
    protected MutableLiveData<T[]> mMutableLiveDataTA;




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
