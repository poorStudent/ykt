package com.vms.ykt.UI.Fragment.icve;

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
import com.vms.ykt.UI.Activity.icveActivity.icve_moreUserSkActivity;
import com.vms.ykt.UI.Adapter.icveAdapter.icveRecyclerAdapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;


import org.jetbrains.annotations.NotNull;

import java.util.List;

public class icveFragment extends baseFragment {


    private static String ARG_PARAM = "param_key";
    private String mParam;
    private zjyUser mZjyUser;
    private yktMainActivity mActivity;
    private String TAG = this.getClass().getSimpleName();


    private View root=null;
    private Button mButton;
    private TextView mButton2;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private icveRecyclerAdapter mRecyclerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;


    public static Fragment newInstance(String icve) {
        icveFragment vIcveFragment =new icveFragment();
        return vIcveFragment;
    }

    public void setData(zjyUser zjyUser) {
        if (zjyUser==null)return;
        mZjyUser= zjyUserDao.sZjyUser;
        icveHttpW.restCookie(mZjyUser.getCookie());
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
            root = inflater.inflate(R.layout.ykt_icve_fragmt_view, container, false);

        }

        Log.d(TAG, "onCreateView: ");
        return root;
    }

    @Override
    public void onViewCreated( View view,  Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        initView(view);
        initListener();
    }


    private void initView(View view){
        mButton=view.findViewById(R.id.icve_bt_djjz);
        mButton2=view.findViewById(R.id.icve_bt_bt);
        mSwipeRefreshLayout=view.findViewById(R.id.icve_swiperefresh);
        mRecyclerView=view.findViewById(R.id.icve_RecyclerView);
        mProgressBar=view.findViewById(R.id.icve_load_Bar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(800);
        defaultItemAnimator.setRemoveDuration(800);
        mRecyclerView.setItemAnimator(defaultItemAnimator);
        mSwipeRefreshLayout.setColorSchemeResources(R.color.teal_200, R.color.teal_700);
    }

    private void initListener(){
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                loadData();

            }
        });
        mButton.setOnLongClickListener((v) -> {
            Intent i = new Intent(mActivity, icve_moreUserSkActivity.class);
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

    private void loadData(){
        if (mZjyUser==null){
            mProgressBar.setVisibility(View.GONE);
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                List<icveCourseInfo> vCourseList = icveMainW.getMyCourseList(mZjyUser);

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ( vCourseList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new icveRecyclerAdapter(vCourseList);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(vCourseList);
                            }
                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mActivity, "获取失败...");
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
    public void onCreate( Bundle savedInstanceState) {
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
