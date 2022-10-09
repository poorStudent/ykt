package com.vms.ykt.UI.Fragment.newzjy;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_courseHdActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_signStudentActivity;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_courseHd_Adapter;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_signStu_Adapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.SignAndQuestionStu;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class newzjy_signStu_Fragment extends baseFragment {


    private String mParam;

    private String TAG = this.getClass().getSimpleName();


    private newzjy_signStudentActivity mActivity;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;

    private newzjy_signStu_Adapter mRecyclerAdapter = null;

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private classActivity mClassActivity;

    private newZjyUser mNewZjyUser;

    private List<SignAndQuestionStu> mSignAndQuestionStuList;


    private int flag;

    public newzjy_signStu_Fragment(int flag) {
        this.flag = flag;


    }

    public void setData() {

    }

    private static String ARG_PARAM = "param_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (newzjy_signStudentActivity) context;
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

    private void initData() {
        mClassActivity=newZjyUserDao.sClassActivity;
        mNewZjyUser=newZjyUserDao.sNewZjyUser;
    }

    private void initView(View view) {
        mButton = view.findViewById(R.id.zjy_bt_djjz);
        mButton2 = view.findViewById(R.id.zjy_bt_jrkt);
        mButton2.setHeight(Tool.dp2px(mActivity, 50));
        mButton2.setTextColor(mActivity.getColor(R.color.white));
        mButton2.setText("一键操作");
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
            showSetDialog();
        });

        mButton.performClick();

    }

    public void loadData() {
        if (mClassActivity == null) {
            mProgressBar.setVisibility(View.GONE);
            Tool.toast(mActivity, "登录失效");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                mSignAndQuestionStuList = getSignAndQuestionStu();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mSignAndQuestionStuList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_signStu_Adapter(mSignAndQuestionStuList);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mSignAndQuestionStuList);
                                Log.d(TAG, "run: mSignAndQuestionStuList");
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

    private List<SignAndQuestionStu> getSignAndQuestionStu() {
        mSignAndQuestionStuList=new ArrayList<>();
        switch (flag) {
            case 1:
                mSignAndQuestionStuList = newZjyMain.getUnSignStudent(mClassActivity.getId());
                if (mSignAndQuestionStuList.size() == 0) {
                }
                break;
            case 2:
                mSignAndQuestionStuList = newZjyMain.getSignStudent(mClassActivity.getId());
                if (mSignAndQuestionStuList.size() == 0) {
                }
                break;
        }
        return mSignAndQuestionStuList;

    }

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_signstu_fragment_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_qd2 = dialogView.findViewById(R.id.newzjy_bt_qd2);
        Button newzjy_bt_qbcd = dialogView.findViewById(R.id.newzjy_bt_qbcd);
        Button newzjy_bt_qbsj = dialogView.findViewById(R.id.newzjy_bt_qbsj);
        Button newzjy_bt_qbgj = dialogView.findViewById(R.id.newzjy_bt_qbgj);
        Button newzjy_bt_qbzt = dialogView.findViewById(R.id.newzjy_bt_qbzt);
        Button newzjy_bt_qbbj = dialogView.findViewById(R.id.newzjy_bt_qbbj);
        Button newzjy_bt_qbqq = dialogView.findViewById(R.id.newzjy_bt_qbqq);



        //取消点击外部消失弹窗
        setDeBugDialog.setCancelable(true);
        //创建AlertDiaLog
        setDeBugDialog.create();
        //AlertDiaLog显示
        final AlertDialog customAlert = setDeBugDialog.show();
        //设置AlertDiaLog宽高属性
        WindowManager.LayoutParams params = Objects.requireNonNull(customAlert.getWindow()).getAttributes();
        params.width = 900;
        params.height = 850;
        customAlert.getWindow().setAttributes(params);
        // 移除dialog的decorview背景色
        // Objects.requireNonNull(customAlert.getWindow()).getDecorView().setBackground(null);
        //设置自定义界面的点击事件逻辑

        newzjy_bt_qd.setOnClickListener((View view) -> {
          new Thread(()->{
              sign();

          }).start();
        });
        newzjy_bt_qd2.setOnClickListener((View view) -> {
            patchSign(1);
        });

        newzjy_bt_qbcd.setOnClickListener((View view) -> {
            patchSign(2);
        });

        newzjy_bt_qbsj.setOnClickListener((View view) -> {
            patchSign(3);
        });

        newzjy_bt_qbgj.setOnClickListener((View view) -> {
            patchSign(4);
        });

        newzjy_bt_qbzt.setOnClickListener((View view) -> {
            patchSign(5);
        });

        newzjy_bt_qbbj.setOnClickListener((View view) -> {
            patchSign(6);
        });

        newzjy_bt_qbqq.setOnClickListener((View view) -> {
            patchSign(7);
        });


    }

    private void sign() {
       if (mSignAndQuestionStuList!=null){

           for (SignAndQuestionStu vSignAndQuestionStu:mSignAndQuestionStuList){
               newZjyMain.Sign(mActivity, newZjyUserDao.sClassActivity,vSignAndQuestionStu);
               try {
                   Thread.sleep(1000);
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
           }
       }
    }

    private void patchSign(int type) {
        new Thread(()->{
            if (mSignAndQuestionStuList != null) {
                for (SignAndQuestionStu vSignAndQuestionStu : mSignAndQuestionStuList) {
                    newZjyMain.patchSign(mActivity,vSignAndQuestionStu,type);
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }

        }).start();

    }


}

