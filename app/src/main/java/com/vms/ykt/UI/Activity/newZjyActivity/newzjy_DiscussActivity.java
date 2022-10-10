package com.vms.ykt.UI.Activity.newZjyActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
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
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_discuss_Adapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.SignAndQuestionStu;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.List;
import java.util.Objects;


public class newzjy_DiscussActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private int type = 0;
    private Context mContext;
    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private newzjy_discuss_Adapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private newZjyUser mNewZjyUser;
    private List<SignAndQuestionStu> mSignAndQuestionStuList;
    private classActivity mClassActivity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newzjy_discuss_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }


    private void initData() {

        mNewZjyUser = newZjyUserDao.sNewZjyUser;
        mClassActivity = newZjyUserDao.sClassActivity;
        mContext = newzjy_DiscussActivity.this;
        Intent i = getIntent();

        // Log.d(TAG, "initData: "+mNewZjyUser.getUserAccessToken());
    }

    private void initView() {
        mButton = findViewById(R.id.zjy_bt_djjz);
        mButton2 = findViewById(R.id.zjy_bt_jrkt);
        mButton2.setText("一键操作");
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

        if (mClassActivity == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                mSignAndQuestionStuList = newZjyMain.getPrStuActivityRecord(mClassActivity.getId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mSignAndQuestionStuList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_discuss_Adapter(mSignAndQuestionStuList);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mSignAndQuestionStuList);
                                Log.d(TAG, "run: updateData(mZjyCourseIfnos)");
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
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_discuss_too_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_yjgf = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_tjtl = dialogView.findViewById(R.id.newzjy_bt_sckt);
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

        newzjy_bt_yjgf.setText("一键改分");

        newzjy_bt_yjgf.setOnClickListener((View view) -> {
            if(mSignAndQuestionStuList!=null) {
                updateStudentSignStatus();
            }
        });

        newzjy_bt_tjtl.setText("提交讨论");
        newzjy_bt_tjtl.setOnClickListener((View view) -> {
                SaveStuDiscussAnswer();
        });

    }

    private void updateStudentSignStatus() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_discuss_too_dialog_xgfs, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        Button newzjy_bt_xgfs = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_fs = dialogView.findViewById(R.id.newzjy_et_fzs);
        TextView newzjy_tv_fsts = dialogView.findViewById(R.id.newzjy_tv_fzs);


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
        newzjy_tv_fsts.setText("分数： ");
        newzjy_et_fs.setHint("5");

        newzjy_bt_xgfs.setOnClickListener((View view) -> {
            String fs=newzjy_et_fs.getText().toString();
            if (!fs.isEmpty()) {
                new Thread(() -> {
                    for (SignAndQuestionStu vSignAndQuestionStu:mSignAndQuestionStuList){
                        String resp=newZjyApi.getUpdateStudentSignStatus(fs,vSignAndQuestionStu.getId());
                        runOnUiThread(() -> {
                            Tool.toast(mContext,  vSignAndQuestionStu.getStuName()+ "\n" + resp);
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }


                }).start();
            }
        });
    }

    private void SaveStuDiscussAnswer() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_discuss_too_dialog_xgfs, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件


        Button newzjy_bt_xgfs = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_cxqd = dialogView.findViewById(R.id.newzjy_bt_cxqd);
        EditText newzjy_et_fs = dialogView.findViewById(R.id.newzjy_et_fzs);
        TextView newzjy_tv_fsts = dialogView.findViewById(R.id.newzjy_tv_fzs);


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
        newzjy_tv_fsts.setText("内容： ");
        newzjy_et_fs.setHint("11111");
        newzjy_bt_xgfs.setText("提交");
        newzjy_bt_xgfs.setOnClickListener((View view) -> {
            String nr=newzjy_et_fs.getText().toString();
                new Thread(() -> {
                        String resp=newZjyApi.getSaveStuDiscussAnswer(mClassActivity.getId(),nr);
                        runOnUiThread(() -> {
                            Tool.toast(mContext,  mClassActivity.getTypeName()+ "\n" + resp);
                        });

                }).start();

        });
        newzjy_bt_cxqd.setVisibility(View.VISIBLE);
        newzjy_bt_cxqd.setText("修改提交");
        newzjy_bt_cxqd.setOnClickListener((View view) -> {
            String nr=newzjy_et_fs.getText().toString();
            final String vRecordId=mClassActivity.getRecordId();
            if (vRecordId!=null && !vRecordId.isEmpty())
                new Thread(() -> {
                        String resp=newZjyApi.getRenewStuTopicInfo(mClassActivity.getId(),vRecordId,nr);
                        runOnUiThread(() -> {
                            Tool.toast(mContext,  mClassActivity.getTypeName()+ "\n" + resp);
                        });

                }).start();

        });
    }

}
