package com.vms.ykt.UI.Fragment.newzjy;

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
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_courseHdActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_courseHdActivity;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_courseHd_Adapter;
import com.vms.ykt.UI.Adapter.zjyAdapter.zjy_courseHDAdapter;
import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.SignAndQuestionStu;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyCouresActivitInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class newzjy_coursehd_Fragment extends baseFragment {


    private String mParam;

    private String TAG = this.getClass().getSimpleName();


    private newzjy_courseHdActivity mActivity;

    private View root = null;
    private TextView mButton2;
    private Button mButton;
    private RecyclerView mRecyclerView;
    private newzjy_courseHd_Adapter mRecyclerAdapter = null;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private ProgressBar mProgressBar;

    private ClassRoom mClassRoom;

    List<classActivity> mClassActivityList;

    private int flag;

    public newzjy_coursehd_Fragment(int flag) {
        this.flag = flag;
    }

    public void setData() {

    }

    private static String ARG_PARAM = "param_key";

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mActivity = (newzjy_courseHdActivity) context;
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
        mClassRoom = newZjyUserDao.sClassRoom;

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
        if (mClassRoom == null) {
            mProgressBar.setVisibility(View.GONE);
            Tool.toast(mActivity, "登录失效");
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                mClassActivityList = getCourseHD();
                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (mClassActivityList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_courseHd_Adapter(mClassActivityList);
                                mRecyclerView.setAdapter(mRecyclerAdapter);
                            } else {
                                mRecyclerAdapter.updateData(mClassActivityList);
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

    private List<classActivity> getCourseHD() {

        List<classActivity> mClassActivityList = new ArrayList();
        ;
        switch (flag) {
            case 1:
                mClassActivityList = newZjyMain.getClassActivityQ(mClassRoom);
                if (mClassActivityList.size() == 0) {
                }
                break;
            case 2:
                mClassActivityList = newZjyMain.getClassActivityZ(mClassRoom);
                if (mClassActivityList.size() == 0) {
                }
                break;
            case 3:
                mClassActivityList = newZjyMain.getClassActivityH(mClassRoom);
                if (mClassActivityList.size() == 0) {
                }
                break;
            default:
                return mClassActivityList;
        }
        return mClassActivityList;
    }

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_coursehd_fragment_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_kqhd = dialogView.findViewById(R.id.newzjy_bt_kqhd);
        Button newzjy_bt_jshd = dialogView.findViewById(R.id.newzjy_bt_jshd);
        Button newzjy_bt_cjqdtw = dialogView.findViewById(R.id.newzjy_bt_cjqdtw);
        Button newzjy_bt_cjcrtw = dialogView.findViewById(R.id.newzjy_bt_cjcrtw);
        Button newzjy_bt_cjtl = dialogView.findViewById(R.id.newzjy_bt_cjtl);
        Button newzjy_bt_cjfztl = dialogView.findViewById(R.id.newzjy_bt_cjfztl);
        Button newzjy_bt_schd = dialogView.findViewById(R.id.newzjy_bt_schd);

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

        newzjy_bt_cjqdtw.setOnClickListener((View view) -> {
            SaveActivityTw1();
        });
        newzjy_bt_cjcrtw.setOnClickListener((View view) -> {
            SaveActivityTw2();
        });

        newzjy_bt_cjtl.setOnClickListener((View view) -> {
            SaveActivityTl();

        });
        newzjy_bt_cjfztl.setOnClickListener((View view) -> {
            CreateGroupActivity();


        });


        if (mClassActivityList == null || mClassActivityList.size() == 0) {
            return;
        }

        newzjy_bt_qd.setOnClickListener((View view) -> {
            new Thread(() -> {
                sign();
            }).start();

        });

        newzjy_bt_kqhd.setOnClickListener((View view) -> {
            new Thread(() -> {
                startActivity();
            }).start();
        });


        newzjy_bt_jshd.setOnClickListener((View view) -> {

            new Thread(() -> {
                overActivity();
            }).start();

        });
        newzjy_bt_schd.setOnClickListener((View view) -> {

            new Thread(() -> {
                DelActivity();
            }).start();

        });

    }

    private void SaveActivityTw1() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_coursehd_fragment_fztl_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_fzs = dialogView.findViewById(R.id.newzjy_et_fzs);
        EditText newzjy_et_qdrs = dialogView.findViewById(R.id.newzjy_et_qdrs);

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
            String tlnr=newzjy_et_fzs.getText().toString();
            String qdrs=newzjy_et_qdrs.getText().toString();
            if (!tlnr.isEmpty() && !qdrs.isEmpty()) {
                new Thread(() -> {
                    String resp=newZjyApi.getSaveActivityTw1(mClassRoom.getId(),mClassRoom.getCourseId(),tlnr,qdrs);
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mActivity, mClassRoom.getClassName() + "\n" + resp);
                    });
                }).start();
            }
        });
    }

    public static String getStudentsQuestioned(String classroomId) {
        String data="null";
        StringBuilder vStringBuilder =new StringBuilder();
        List<SignAndQuestionStu> vSignAndQuestionStus=newZjyMain.getStudentsQuestioned(classroomId);
        for (SignAndQuestionStu vStu:vSignAndQuestionStus){
            vStringBuilder.append(vStu.getStuName());
            vStringBuilder.append("- id : ");
            vStringBuilder.append(vStu.getStuId());
        }
        data=vStringBuilder.toString();
        return data;
    }

    private void SaveActivityTw2() {

        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_coursehd_fragment_fztl_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_fzs = dialogView.findViewById(R.id.newzjy_et_fzs);
        EditText newzjy_et_crid = dialogView.findViewById(R.id.newzjy_et_crid);
        TextView crwb = dialogView.findViewById(R.id.crwb);

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
        crwb.setText("加载中.....");
        new Thread(() -> {
            String ids=getStudentsQuestioned(mClassRoom.getId());
              mActivity.runOnUiThread(() -> {
                  crwb.setText(ids);
            });
        }).start();

        newzjy_bt_qd.setOnClickListener((View view) -> {
            String tlnr=newzjy_et_fzs.getText().toString();
             String crid=newzjy_et_crid.getText().toString();
            if (!tlnr.isEmpty() && !crid.isEmpty()) {
                new Thread(() -> {
                    String[] crids= crid.split(",");
                    String id= JSONObject.toJSONString(crids);
                    String resp=newZjyApi.getSaveActivityTw2(mClassRoom.getId(),mClassRoom.getCourseId(),tlnr,id);
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mActivity, mClassRoom.getClassName() + "\n" + resp);
                    });
                }).start();
            }
        });
    }

    private void SaveActivityTl() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_coursehd_fragment_fztl_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_fzs = dialogView.findViewById(R.id.newzjy_et_fzs);

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
            String tlnr=newzjy_et_fzs.getText().toString();
            if (!tlnr.isEmpty()) {
                new Thread(() -> {
                    String resp=newZjyApi.getSaveActivityTl(mClassRoom.getId(),mClassRoom.getCourseId(),tlnr);
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mActivity, mClassRoom.getClassName() + "\n" + resp);
                    });
                }).start();
            }
        });
    }

    private void CreateGroupActivity() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_coursehd_fragment_fztl_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_fzs = dialogView.findViewById(R.id.newzjy_et_fzs);

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
            String fzs=newzjy_et_fzs.getText().toString();
            if (!fzs.isEmpty()) {
                new Thread(() -> {
                    String resp=newZjyApi.getCreateGroupActivity(mClassRoom.getId(),mClassRoom.getCourseId(),fzs);
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mActivity, mClassRoom.getClassName() + "\n" + resp);
                    });
                }).start();
            }
        });
    }

    private void sign() {
        for (classActivity vActivity : mClassActivityList) {
            newZjyMain.Sign(mActivity, vActivity);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private void startActivity() {
        for (classActivity vActivity : mClassActivityList) {
            String resp = newZjyMain.startActivity(vActivity);
            mActivity.runOnUiThread(() -> {
                Tool.toast(mActivity, vActivity.getTypeName() + "\n" + resp);
            });
        }
    }

    private void overActivity() {
        for (classActivity vActivity : mClassActivityList) {
            String resp = newZjyMain.overActivity(vActivity);
            mActivity.runOnUiThread(() -> {
                Tool.toast(mActivity, vActivity.getTypeName() + "\n" + resp);
            });
        }
    }

    private void DelActivity() {
        for (classActivity vActivity : mClassActivityList) {
            String resp = newZjyMain.getDelActivity(vActivity.getClassroomId(), vActivity.getId());
            mActivity.runOnUiThread(() -> {
                Tool.toast(mActivity, vActivity.getTypeName() + "\n" + resp);
            });
        }
    }
}

