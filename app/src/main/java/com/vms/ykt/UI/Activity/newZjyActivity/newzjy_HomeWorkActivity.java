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
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_examWork_Adapter;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_homeWork_Adapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ExamWork;
import com.vms.ykt.yktStuMobile.newZJY.Homework;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.List;
import java.util.Objects;


public class newzjy_HomeWorkActivity extends AppCompatActivity {
    private final String TAG = this.getClass().getSimpleName();

    private Context mContext;
    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private newzjy_homeWork_Adapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private newZjyUser mNewZjyUser;
    //private yktUserVM mUserVModel;

    private List<Homework> mHomework;
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
        mContext = newzjy_HomeWorkActivity.this;
        //mNewZjyCourse=(newZjyCourse)i.getSerializableExtra("newZjyCourse");
         Log.d(TAG, "newzjy_HomeWorkActivity: "+mNewZjyCourse.getCourseId());

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
                newZjyMain.upAuthorizationHomework(mNewZjyUser,mNewZjyCourse.getCourseId());

                Log.d(TAG, "run: "+mNewZjyUser.getHomeworkAccessToken());
                mHomework=newZjyMain.getAllHomework(mNewZjyCourse.getCourseId());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mHomework.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_homeWork_Adapter(mHomework);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mHomework);
                                Log.d(TAG, "run: updateData(mHomework)");
                            }
                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mContext, "获取失败...");
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });
                newZjyMain.getTeacherAuthorizationHomework(mNewZjyUser.getOtNewZjyUser());
            }
        }).start();

    }

    private void showSetDialog() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        newZjyApi.upContent1();
    }
}
