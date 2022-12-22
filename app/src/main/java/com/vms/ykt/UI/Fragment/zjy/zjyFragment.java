package com.vms.ykt.UI.Fragment.zjy;

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
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;


import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_DayTeachActivity;
import com.vms.ykt.UI.Adapter.zjyAdapter.zjyRecyclerAdapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.zjy.*;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class zjyFragment extends baseFragment {


    private String mParam;

    private String TAG = this.getClass().getSimpleName();


    ArrayList<String> signOk = new ArrayList<>();


    private zjyUser mZjyUser;

    private List<zjyCourseIfno> mZjyCourseIfnos = new ArrayList<>();


    private boolean isCreated = false;
    private boolean isFastLoad = true;

    private yktMainActivity mActivity;
    private static zjyFragment sZjyFragment;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private zjyRecyclerAdapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;



    public static Fragment newInstance(String parms) {
        if (sZjyFragment == null) {
            sZjyFragment = new zjyFragment();
        }
        return sZjyFragment;

    }

    public void setData(zjyUser zjyUser) {
        if (zjyUser==null)return;
        mZjyUser=zjyUserDao.sZjyUser;
        zjyHttpW.restCookie(mZjyUser.getCookie());
    }

    private static String ARG_PARAM = "param_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (yktMainActivity) context;
        mParam = getArguments().getString(ARG_PARAM);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.ykt_zjy_fragmt_view, container, false);

        }

        Log.d(TAG, "onCreateView: ");
        return root;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        isCreated = true;
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
        mButton2.setOnClickListener((View v) -> {
            if (mZjyUser == null) {
                Tool.toast(mActivity, "登录失效");
                return ;
            }
            Intent i = new Intent(mActivity, zjy_DayTeachActivity.class);

            mActivity.startActivity(i);
        });
        mButton2.setOnLongClickListener((View v) -> {
            if (mZjyUser == null) {
                Tool.toast(mActivity, "登录失效");
                return true;
            }
            Tool.toast(mActivity, "开始轮询签到...");
            new Thread(() -> {
                doSign(mZjyUser);
            }).start();
            return true;
        });

    }

    public void loadData() {
        if (mZjyUser == null) {
            mProgressBar.setVisibility(View.GONE);
            Tool.toast(mActivity, "登录失效");
            return ;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {


                    mZjyCourseIfnos = zjyMain.geAlltCoures(mZjyUser);
                    if (mZjyCourseIfnos.size() == 0) {
                        mZjyCourseIfnos = zjyMainW.getCoures();
                    }

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mZjyCourseIfnos.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new zjyRecyclerAdapter(mZjyCourseIfnos);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mZjyCourseIfnos);
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

    private void initRecyclerAdapterListener(zjyRecyclerAdapter recyclerAdapter) {

        recyclerAdapter.setOnItemClickListener(new zjyRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {


            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });

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

    public void doSign(zjyUser ZjyUser) {
        List<zjyTeachInfo> DayTeachInfo;//今日课堂列表
        DayTeachInfo = zjyMain.getDayTeachInfo(ZjyUser);
        if (DayTeachInfo.size()==0){
            DayTeachInfo= zjyMainW.getDayfaceTeachInfo();
        }
        for (zjyTeachInfo varZjyDayTeachInfo : DayTeachInfo) {

            String stringActivity = zjyApi.getCourseActivityList2(ZjyUser, varZjyDayTeachInfo);
            Log.d(TAG, "doSign: " + stringActivity);
            ArrayList<zjyCouresActivitInfo> ActivityList = zjyMain.parseActInfo(stringActivity);

            for (zjyCouresActivitInfo varActivitInfo : ActivityList) {

                if (varActivitInfo.getDataType().equals("签到") && !varActivitInfo.getState().equals("3")) {
                    if (signOk.contains(varActivitInfo.getId())) {
                        mActivity.runOnUiThread(() -> {
                            Tool.toast(mActivity, varZjyDayTeachInfo.getCourseName() + "\n" + varActivitInfo.getTitle() + "\n" + "已签到");
                        });
                        continue;
                    }
                    sign(varActivitInfo, ZjyUser, varZjyDayTeachInfo);
                } else if (varActivitInfo.getDataType().equals("签到")) {
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mActivity, varZjyDayTeachInfo.getCourseName() + "\n" + varActivitInfo.getTitle() + "\n" + "签到已结束");
                    });
                }
            }


        }

    }

    public void sign(zjyCouresActivitInfo varActivitInfo, zjyUser zjyUsers, zjyTeachInfo zjyTeachInfos) {

        String resc1 = zjyApi.getJoinActivities(varActivitInfo.getId(), zjyUsers, zjyTeachInfos);
        String resc2 = zjyApi.getSaveSign(varActivitInfo.getId(), varActivitInfo.getGesture(), zjyUsers, zjyTeachInfos);
        final String ret;
        if (resc1!=null&&resc2!=null&&resc2.contains("签到成功")) {
            signOk.add(varActivitInfo.getId());
            ret="签到成功";
        }else {
            String resp= zjyApiW.getSaveSign(varActivitInfo.getId(), varActivitInfo.getGesture(), zjyUsers, zjyTeachInfos);
            if (resp!=null&&resp.contains("{\"code\":1}")){
                signOk.add(varActivitInfo.getId());
                ret="签到成功";
            }else {
                ret="签到失败";

            }
        }
        mActivity.runOnUiThread(() -> {
            Tool.toast(mActivity, zjyTeachInfos.getCourseName() + "\n" + varActivitInfo.getTitle() +"\n"+ ret);
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        Log.d(TAG, "onStart: ");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: ");
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: ");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: ");
    }

    @Override
    public void onActivityCreated(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.d(TAG, "onActivityCreated: ");
    }

    @Override
    public void onAttachFragment(@NonNull @NotNull Fragment childFragment) {
        super.onAttachFragment(childFragment);
        Log.d(TAG, "onAttachFragment: ");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        Log.d(TAG, "onHiddenChanged: ");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mRecyclerAdapter != null) {
            //   mRecyclerAdapter.pauseRequests();
        }
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

}

