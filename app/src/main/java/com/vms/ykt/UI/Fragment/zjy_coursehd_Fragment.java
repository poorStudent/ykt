package com.vms.ykt.UI.Fragment;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_courseHdActivity;
import com.vms.ykt.UI.Adapter.zjyAdapter.zjy_courseHDAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyCouresActivitInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;

public class zjy_coursehd_Fragment extends baseFragment {


    private String mParam;

    private String TAG = this.getClass().getSimpleName();





    private zjyUser mZjyUser;

    private List<zjyCouresActivitInfo> mZjyCouresActivitInfos = new ArrayList<>();


    private zjy_courseHdActivity mActivity;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private zjy_courseHDAdapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private zjyTeachInfo mZjyTeachInfo;

    private zjyHttpW mZjyHttpW;
    private zjyApiW mZjyApiW;
    private zjyMainW mZjyMainW;

    private int flag;

    public zjy_coursehd_Fragment(int flag) {
        this.flag = flag;
    }

    public void setData(zjyUser zjyUser, zjyTeachInfo zjyTeachInfo) {
        if (zjyUser == null) return;
        this.mZjyTeachInfo = zjyTeachInfo;
        this.mZjyUser = zjyUser;
        this.mZjyHttpW = new zjyHttpW();
        this.mZjyApiW = new zjyApiW();
        this.mZjyMainW = new zjyMainW();
        mZjyHttpW.setUserCookie(mZjyUser.getCookie());
        mZjyApiW.setZjyHttpW(mZjyHttpW);
        mZjyMainW.setZjyApiW(mZjyApiW);
    }

    private static String ARG_PARAM = "param_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (zjy_courseHdActivity) context;
      //  mParam = getArguments().getString(ARG_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.zjy_coursehd_fragmt_view, container, false);

        }

        Log.d(TAG, "onCreateView: ");
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        initView(view);
        initListener();
    }

    private void initView(View view) {
        mButton = view.findViewById(R.id.zjy_bt_djjz);
        mButton2 = view.findViewById(R.id.zjy_bt_jrkt);
        mProgressBar = view.findViewById(R.id.zjy_load_Bar);
        mRecyclerView = view.findViewById(R.id.zjy_RecyclerView);
        mSwipeRefreshLayout = view.findViewById(R.id.zjy_swiperefresh);
        //mButton.setText(xsid);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(800);
        defaultItemAnimator.setRemoveDuration(800);
        mRecyclerView.setItemAnimator(defaultItemAnimator);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.teal_200, R.color.teal_700);

    }

    private void initListener() {

        mSwipeRefreshLayout.setOnRefreshListener(() -> {

            mSwipeRefreshLayout.setRefreshing(true);
            loadData();
        });

        mButton.setOnClickListener((View v) -> {

            mProgressBar.setVisibility(View.VISIBLE);
            loadData();
        });

    }

    public void loadData() {
        if (mZjyUser == null) {
            mProgressBar.setVisibility(View.GONE);
            Tool.toast(mActivity, "登录失效");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                getCourseHD();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mZjyCouresActivitInfos.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new zjy_courseHDAdapter(mZjyCouresActivitInfos, mZjyUser,mZjyTeachInfo);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mZjyCouresActivitInfos);
                                Log.d(TAG, "run: updateData(mZjyCourseIfnos)");
                            }
                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mActivity, "获取失败/或没有课程...");
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }

    private void getCourseHD() {

        switch (flag) {
            case 1:
                mZjyCouresActivitInfos = zjyMain.getCourseActivityList1(mZjyUser, mZjyTeachInfo);
                if (mZjyCouresActivitInfos.size() == 0) {
                }
                break;
            case 2:
                mZjyCouresActivitInfos = zjyMain.getCourseActivityList2(mZjyUser, mZjyTeachInfo);
                if (mZjyCouresActivitInfos.size() == 0) {
                }
                break;
            case 3:
                mZjyCouresActivitInfos = zjyMain.getCourseActivityList3(mZjyUser, mZjyTeachInfo);
                if (mZjyCouresActivitInfos.size() == 0) {
                }
                break;
        }
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
            }
        }
    };


}

