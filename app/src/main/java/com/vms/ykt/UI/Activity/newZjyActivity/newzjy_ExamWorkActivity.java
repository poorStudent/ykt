package com.vms.ykt.UI.Activity.newZjyActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vms.ykt.R;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_classRoom_Adapter;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_examWork_Adapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.ExamWork;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class newzjy_ExamWorkActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private int type = 4;
    private Context mContext;
    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private newzjy_examWork_Adapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private newZjyUser mNewZjyUser;
    //private yktUserVM mUserVModel;

    private List<ExamWork> mExamWorkList;
    private newZjyCourse mNewZjyCourse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newzjy_examwork_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }


    private void initData() {
        // mUserVModel= ViewModelUtils.getViewModel(getApplication(), yktUserVM.class);
        mNewZjyUser = newZjyUserDao.sNewZjyUser;
        mNewZjyCourse = newZjyUserDao.sNewZjyCourse;
        mContext = newzjy_ExamWorkActivity.this;
        Intent i = getIntent();
        type = i.getIntExtra("type", 4);
        //mNewZjyCourse=(newZjyCourse)i.getSerializableExtra("newZjyCourse");
         Log.d(TAG, "newzjy_ExamWorkActivity: "+mNewZjyCourse.getCourseId());
         Log.d(TAG, "type: "+type);

    }

    private void initView() {
        mButton = findViewById(R.id.zjy_bt_djjz);
        mButton2 = findViewById(R.id.zjy_bt_jrkt);
        mProgressBar = findViewById(R.id.zjy_load_Bar);
        mRecyclerView = findViewById(R.id.zjy_RecyclerView);
        mSwipeRefreshLayout = findViewById(R.id.zjy_swiperefresh);
        //mButton.setText(xsid);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
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

    private void loadData() {

        if (mNewZjyUser == null || mNewZjyUser.getUNTYXLCOOKIE() == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.d(TAG, "type: "+type);
                switch (type) {
                    case 0:
                        mExamWorkList = newZjyMain.getExam_list_data_t(mNewZjyCourse.getCourseId());
                        if (mExamWorkList.size()==0){
                            mExamWorkList = newZjyMain.getExam_list_data_tw(mNewZjyCourse.getCourseId());
                        }
                        break;
                    case 1:
                        mExamWorkList = newZjyMain.getExam_list_data_w(mNewZjyCourse.getCourseId());
                        if (mExamWorkList.size()==0){
                            mExamWorkList = newZjyMain.getExam_list_data_ww(mNewZjyCourse.getCourseId());
                        }
                        break;
                    case 2:
                        mExamWorkList = newZjyMain.getExam_list_data_e(mNewZjyCourse.getCourseId());
                        if (mExamWorkList.size()==0){
                            mExamWorkList = newZjyMain.getExam_list_data_ew(mNewZjyCourse.getCourseId());
                        }
                        break;
                    case 3://附件作业

                        break;
                    default:
                        break;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mExamWorkList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_examWork_Adapter(mExamWorkList);
                                mRecyclerAdapter.setType(type);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mExamWorkList);
                                Log.d(TAG, "run: updateData(mExamWorkList)");
                            }
                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mContext, "获取失败...");
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();

    }

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_classroom_tool_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_sckt = dialogView.findViewById(R.id.newzjy_bt_sckt);
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
            new Thread(() -> {

            }).start();
        });
        newzjy_bt_sckt.setOnClickListener((View view) -> {
            new Thread(() -> {

            }).start();
        });

    }


}
