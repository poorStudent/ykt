package com.vms.ykt.UI.Activity.moocActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vms.ykt.R;
import com.vms.ykt.UI.Adapter.moocAdapter.mooc_examAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.mooc.moocUserDao;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.mooc.WorkExamList;
import com.vms.ykt.yktStuWeb.mooc.moocApiW;
import com.vms.ykt.yktStuWeb.mooc.moocHttpW;
import com.vms.ykt.yktStuWeb.mooc.moocMianW;

import java.util.ArrayList;
import java.util.List;

public class mooc_examActivity extends AppCompatActivity {
    private static final String TAG = mooc_examActivity.class.getSimpleName();
    private Context mContext;

    private moocCourseInfo mCourseIfno;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private mooc_examAdapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    List<WorkExamList> mWorkExamLists =new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mooc_work_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = mooc_examActivity.this;
        this.mCourseIfno = moocUserDao.sMoocCourseInfo;

        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
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


        });
    }

    private void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {


                mWorkExamLists = moocMianW.getAllonlineExam(mCourseIfno.getCourseOpenId());
                if (mWorkExamLists.size() == 0) {
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mWorkExamLists.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new mooc_examAdapter(mWorkExamLists);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mWorkExamLists);
                                Log.d(TAG, "run: updateData(mZjyCourseIfnos)");
                            }
                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mContext, "获取失败/或没有课程...");
                        }
                        mSwipeRefreshLayout.setRefreshing(false);
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }
}


