package com.vms.ykt.UI.Fragment.mooc;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
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
import com.vms.ykt.UI.Activity.moocActivity.mooc_moreUserSkActivity;
import com.vms.ykt.UI.Adapter.moocAdapter.moocRecyclerAdapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.mooc.*;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuWeb.mooc.moocApiW;
import com.vms.ykt.yktStuWeb.mooc.moocHttpW;
import com.vms.ykt.yktStuWeb.mooc.moocMianW;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class moocFragment extends baseFragment {


    private static String ARG_PARAM = "param_key";
    private String mParam;
    private yktMainActivity mActivity;
    private String TAG = this.getClass().getSimpleName();

    private List<moocCourseInfo> mMoocCourseInfos;
    private List<courseInfoForTeach> mForTeachList;
    private zjyUser mZjyUser;

    private View root = null;
    private Button mButton;
    private TextView mButton2;
    private RecyclerView mRecyclerView;
    private moocRecyclerAdapter mRecyclerAdapter;
    private ProgressBar mProgressBar;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static Fragment newInstance(String mooc) {
        moocFragment vMoocFragment = new moocFragment();
        return vMoocFragment;
    }

    public void setData(zjyUser zjyUser) {
        if (zjyUser == null) return;
        this.mZjyUser = zjyUserDao.sZjyUser;
        moocHttpW.restCookie(mZjyUser.getCookie());


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (yktMainActivity) context;
        mParam = getArguments().getString(ARG_PARAM);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.ykt_mooc_fragmt_view, container, false);

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
        mButton = view.findViewById(R.id.mooc_bt_djjz);
        mButton2 = view.findViewById(R.id.mooc_bt_bt);
        mProgressBar = view.findViewById(R.id.mooc_load_Bar);
        mSwipeRefreshLayout = view.findViewById(R.id.mooc_swiperefresh);
        mRecyclerView = view.findViewById(R.id.mooc_RecyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(900);
        defaultItemAnimator.setRemoveDuration(900);
        mRecyclerView.setItemAnimator(defaultItemAnimator);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.teal_200, R.color.teal_700);
    }

    private void initListener() {
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                loadData();

            }
        });
        mButton.setOnLongClickListener((v) -> {
            Intent i = new Intent(mActivity, mooc_moreUserSkActivity.class);
            mActivity.startActivity(i);
            return true;
        });

        mButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Tool.toast(mActivity, "别点我哦^_^...");
            }
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
    }

    public void loadData() {

        if (mZjyUser == null) {
            mProgressBar.setVisibility(View.GONE);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                mMoocCourseInfos = moocMianM.getMyCourseList(mZjyUser);
                mForTeachList = moocApiW.getCourseForTeach();
                if (mMoocCourseInfos.size() == 0) {
                    mMoocCourseInfos = moocMianW.getMyCourseList(mZjyUser);

                }

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mMoocCourseInfos.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new moocRecyclerAdapter(mForTeachList, mMoocCourseInfos);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mMoocCourseInfos);
                                Log.d(TAG, "run: updateData(mMoocCourseInfos);");
                            }
                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mActivity, "获取失败/或没有课程...");
                        }
                        mProgressBar.setVisibility(View.GONE);
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });

            }
        }).start();
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
        Log.d(TAG, "onDestroy: ");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        Log.d(TAG, "onDestroyView: ");
    }

}
