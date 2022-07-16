package com.vms.ykt.UI.Activity.zjyActivity;

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
import com.vms.ykt.UI.Adapter.zjyAdapter.zjy_dayTeachAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;

public class zjy_DayTeachActivity extends AppCompatActivity {
    private static final String TAG = zjy_examActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private zjyCourseIfno mCourseIfno;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private zjy_dayTeachAdapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;
    List<zjyTeachInfo> mDayTeachInfo = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjy_exam_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private zjyHttpW mZjyHttpW;
    private zjyApiW mZjyApiW;
    private zjyMainW mZjyMainW;

    private void initData() {
        Intent i = getIntent();
        this.mContext = zjy_DayTeachActivity.this;

        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");

        this.mZjyHttpW = new zjyHttpW();
        this.mZjyApiW = new zjyApiW();
        this.mZjyMainW = new zjyMainW();
        mZjyHttpW.setUserCookie(mZjyUser.getCookie());
        mZjyApiW.setZjyHttpW(mZjyHttpW);
        mZjyMainW.setZjyApiW(mZjyApiW);
        Log.d(TAG, "initData: " + mZjyUser.getUserId());
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


                mDayTeachInfo = zjyMain.getDayTeachInfo(mZjyUser);
                if (mDayTeachInfo.size() == 0) {
                    mZjyMainW.getDayfaceTeachInfo();
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mDayTeachInfo.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new zjy_dayTeachAdapter(mDayTeachInfo, mZjyUser, mCourseIfno);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mDayTeachInfo);
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

}
