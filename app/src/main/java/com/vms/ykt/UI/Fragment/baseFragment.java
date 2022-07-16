package com.vms.ykt.UI.Fragment;

import androidx.fragment.app.Fragment;


import com.vms.ykt.UI.yktMainActivity;


public abstract class baseFragment extends Fragment {


    public yktMainActivity mActivity;
    private String TAG = this.getClass().getSimpleName();


   /** @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //onViewCreatedChilder(view,savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return onCreateViewChilder(inflater, container, savedInstanceState);
    }

    public abstract View onCreateViewChilder(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    public abstract void onViewCreatedChilder(View view, Bundle savedInstanceState);**/

}
