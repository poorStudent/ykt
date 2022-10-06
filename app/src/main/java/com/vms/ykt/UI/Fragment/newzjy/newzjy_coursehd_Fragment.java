package com.vms.ykt.UI.Fragment.newzjy;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_courseHdActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_courseHdActivity;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_courseHd_Adapter;
import com.vms.ykt.UI.Adapter.zjyAdapter.zjy_courseHDAdapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyCouresActivitInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class newzjy_coursehd_Fragment extends baseFragment {


    private String mParam;

    private String TAG = this.getClass().getSimpleName();


    private newzjy_courseHdActivity mActivity;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private newzjy_courseHd_Adapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private ClassRoom mClassRoom;


    private int flag;

    public newzjy_coursehd_Fragment(int flag) {
        this.flag = flag;
    }

    public void setData() {

    }

    private static String ARG_PARAM = "param_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (newzjy_courseHdActivity) context;
      //  mParam = getArguments().getString(ARG_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.newzjy_coursehd_fragmt_view, container, false);

        }

        Log.d(TAG, "onCreateView: ");
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");

        initData();
        initView(view);
        initListener();

    }

    private void initData(){
        mClassRoom=newZjyUserDao.sClassRoom;

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
        mButton2.setOnClickListener((View v) -> {
        });

        mButton.performClick();

    }

    public void loadData() {
        if (mClassRoom == null) {
            mProgressBar.setVisibility(View.GONE);
            Tool.toast(mActivity, "登录失效");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<classActivity> mClassActivityList=getCourseHD();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mClassActivityList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_courseHd_Adapter(mClassActivityList);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mClassActivityList);
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

    private List<classActivity> getCourseHD() {

        List<classActivity>  mClassActivityList= new  ArrayList();  ;
        switch (flag) {
            case 1:
                mClassActivityList = newZjyMain.getClassActivityQ(mClassRoom);
                if (mClassActivityList.size() == 0) {
                }
                break;
            case 2:
                mClassActivityList = newZjyMain.getClassActivityZ(mClassRoom);
                if (mClassActivityList.size() == 0) {
                }
                break;
            case 3:
                mClassActivityList = newZjyMain.getClassActivityH(mClassRoom);
                if (mClassActivityList.size() == 0) {
                }
                break;
            default:
                return mClassActivityList;
        }
        return mClassActivityList;
    }




}

