package com.vms.ykt.UI.Fragment.icve;

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
import com.vms.ykt.UI.Activity.icveActivity.icve_workExamActivity;
import com.vms.ykt.UI.Adapter.icveAdapter.icve_workExamAdapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;
import com.vms.ykt.yktStuWeb.icve.workExamListInfo;

import java.util.ArrayList;
import java.util.List;

public class icve_workExam_Fragment extends baseFragment {


    private String mParam;

    private String TAG = this.getClass().getSimpleName();


    private zjyUser mZjyUser;

    private List<workExamListInfo> mWorkExamListInfos = new ArrayList<>();


    private icve_workExamActivity mActivity;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private icve_workExamAdapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private icveCourseInfo mCourseIfno;


    private icveMainW mIcveMainW;
    private icveApiW mIcveApiW;

    private int flag;

    public icve_workExam_Fragment(int flag) {
        this.flag = flag;
    }

    public void setData(zjyUser zjyUser, icveCourseInfo CourseIfno) {
        if (zjyUser == null) return;
        this.mCourseIfno=CourseIfno;
        this.mZjyUser = zjyUser;
        icveHttpW mIcveHttpW = new icveHttpW();
        mIcveHttpW.setUserCookie(mZjyUser.getCookie());
        this.mIcveApiW = new icveApiW();
        mIcveApiW.setIcveHttpW(mIcveHttpW);
        this.mIcveMainW = new icveMainW();
        mIcveMainW.setIcveApiW(mIcveApiW);
    }

    private static String ARG_PARAM = "param_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (icve_workExamActivity) context;
      // mParam = getArguments().getString(ARG_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.icve_work_fragmt_view, container, false);

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

                getWorkExam();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mWorkExamListInfos.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new icve_workExamAdapter(mWorkExamListInfos, mZjyUser,mCourseIfno);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mWorkExamListInfos);
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

    private void getWorkExam() {

        switch (flag) {
            case 1:
                mWorkExamListInfos = mIcveMainW.getWorksList(mCourseIfno.getId());
                if (mWorkExamListInfos.size() == 0) {
                }
                break;
            case 2:
                mWorkExamListInfos = mIcveMainW.getExamList(mCourseIfno.getId());
                if (mWorkExamListInfos.size() == 0) {
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

