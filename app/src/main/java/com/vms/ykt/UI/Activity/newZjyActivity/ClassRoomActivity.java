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
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class ClassRoomActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();
    private int type = 0;
    private Context mContext;
    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private newzjy_classRoom_Adapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private newZjyUser mNewZjyUser;
    //private yktUserVM mUserVModel;

    private List<ClassRoom> mClassRoomList;
    private newZjyCourse mNewZjyCourse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newzjy_classroom_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }


    private void initData() {
        // mUserVModel= ViewModelUtils.getViewModel(getApplication(), yktUserVM.class);
        mNewZjyUser = newZjyUserDao.sNewZjyUser;
        mContext = ClassRoomActivity.this;
        Intent i = getIntent();
        type = i.getIntExtra("type", 0);
        //mNewZjyCourse=(newZjyCourse)i.getSerializableExtra("newZjyCourse");
        newZjyApi.upHeader1();
        mClassRoomList = new ArrayList<>();
        // Log.d(TAG, "initData: "+mNewZjyUser.getUserAccessToken());
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

        if (mNewZjyUser == null || mNewZjyUser.getUserAccessToken() == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {

                switch (type) {
                    case 0:
                        break;
                    case 1:
                        mClassRoomList = newZjyMain.getClassroomByDay();
                        break;
                    case 2:
                        mClassRoomList = newZjyMain.getAllClassroom();
                        break;
                    case 3:
                        mNewZjyCourse = newZjyUserDao.sNewZjyCourse;
                        if (mNewZjyCourse.getCourseId() != null) {
                            mClassRoomList = newZjyMain.getClassroomByStudent(mNewZjyCourse);
                        }
                        break;
                    default:
                        break;
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mClassRoomList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_classRoom_Adapter(mClassRoomList);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mClassRoomList);
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
                signAll();
            }).start();
        });
        newzjy_bt_sckt.setOnClickListener((View view) -> {
            new Thread(() -> {
                DelClassroom();
            }).start();
        });

    }

    private void signAll() {
        if (mClassRoomList.size() != 0) {
            for (ClassRoom vClassRoom : mClassRoomList) {
                List<classActivity> ClassActivities = newZjyMain.getClassActivityZ(vClassRoom);
                for (classActivity vActivity : ClassActivities) {
                    String name = vActivity.getTypeName();
                    String RecordId = vActivity.getRecordId();
                    String id = vActivity.getId();
                    if (name.contains("签到")) {
                        System.out.println("---" + id + " * name " + name + " * getStatus " +
                                vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
                        String resp = vClassRoom.getTitle() + "-" + vActivity.getTypeName() + "\n"
                                + newZjyMain.Sign(vActivity, RecordId);
                        runOnUiThread(() -> {
                            Tool.toast(mContext, resp);
                        });
                    }
                }
            }
        }
    }

    private void DelClassroom() {

    }
}
