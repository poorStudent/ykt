package com.vms.ykt.UI.Activity.newZjyActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.UI.Adapter.newzjyAdapter.newzjy_main_Adapter;
import com.vms.ykt.Util.CacheUs;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.List;
import java.util.Objects;

public class newZjy_mainActivity extends AppCompatActivity {

    private Button mButton;
    private TextView mButton2;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private newzjy_main_Adapter mRecyclerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private final String TAG = this.getClass().getSimpleName();
    private Activity mActivity;
    private newZjyUser mNewZjyUser;
    private List<newZjyCourse> mNewZjyCourseList;
  //  private yktUserVM mUserVModel;

    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newzjy_mian_view);
        initData();
        initView();
        initListener();
    }

    private void initData() {
        //mUserVModel= ViewModelUtils.getViewModel(getApplication(), yktUserVM.class);
        mActivity = newZjy_mainActivity.this;
    }

    private void initView() {
        mButton = findViewById(R.id.icve_bt_djjz);
        mButton2 = findViewById(R.id.newzjy_bt_gndq);
        mSwipeRefreshLayout = findViewById(R.id.icve_swiperefresh);
        mRecyclerView = findViewById(R.id.icve_RecyclerView);
        mProgressBar = findViewById(R.id.icve_load_Bar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(mLayoutManager);
        DefaultItemAnimator defaultItemAnimator = new DefaultItemAnimator();
        defaultItemAnimator.setAddDuration(800);
        defaultItemAnimator.setRemoveDuration(800);
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

        mButton.performClick();

        mButton.setOnLongClickListener((view) -> {
            return true;
        });

        mButton2.setOnClickListener((View v)-> {
            goClassRoom(1);
        });

        mButton2.setOnLongClickListener((v) -> {
            goClassRoom(2);
            return true;
        });

        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mSwipeRefreshLayout.setRefreshing(true);
                loadData();
            }
        });
    }

    private void loadData() {

        //mUserInfo=mUserVModel.getCqoocUser();
        if (mNewZjyUser == null || mNewZjyUser.getToken() == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);
            loginDialog();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                mNewZjyCourseList = newZjyMain.getMyClassList(mNewZjyUser);

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (mNewZjyCourseList.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new newzjy_main_Adapter(mNewZjyCourseList, mNewZjyUser);
                                mRecyclerView.setAdapter(mRecyclerAdapter);

                            } else {
                                mRecyclerAdapter.updateData(mNewZjyCourseList);
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

    private void loginDialog() {

        SharedPreferences mPreferences;
        SharedPreferences.Editor mEditor;
        mPreferences = mActivity.getSharedPreferences("newzjyuser", MODE_PRIVATE);
        mEditor = mPreferences.edit();

        boolean isRembPass = mPreferences.getBoolean("newzjyrembPass", false);
        String um = mPreferences.getString("newzjyusername", "");
        String pw = mPreferences.getString("newzjypassword", "");

        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);

        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_login_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        EditText cq_username = dialogView.findViewById(R.id.cq_username);
        EditText cq_password = dialogView.findViewById(R.id.cq_password);
        androidx.appcompat.widget.SwitchCompat cq_wirtePass = dialogView.findViewById(R.id.cq_wirtePass);
        androidx.appcompat.widget.SwitchCompat cq_showPass = dialogView.findViewById(R.id.cq_showPass);
        androidx.appcompat.widget.SwitchCompat cq_otherLogin = dialogView.findViewById(R.id.cq_otherLogin);
        EditText cq_cook = dialogView.findViewById(R.id.cq_cook);
        Button cq_gologin = dialogView.findViewById(R.id.cq_gologin);
        ProgressBar cq_loadingProgressBar = dialogView.findViewById(R.id.cq_loadingProgressBar);

        //取消点击外部消失弹窗
        setDeBugDialog.setCancelable(true);
        //创建AlertDiaLog
        setDeBugDialog.create();
        //AlertDiaLog显示
        final AlertDialog customAlert = setDeBugDialog.show();
        //设置AlertDiaLog宽高属性
        WindowManager.LayoutParams params = Objects.requireNonNull(customAlert.getWindow()).getAttributes();
        params.width = 900;
        params.height = 1000;
        customAlert.getWindow().setAttributes(params);
        // 移除dialog的decorview背景色
        // Objects.requireNonNull(customAlert.getWindow()).getDecorView().setBackground(null);
        //设置自定义界面的点击事件逻辑

        if (isRembPass) {
            cq_username.setText(um);
            cq_password.setText(pw);
            cq_wirtePass.setChecked(true);
        }

        cq_gologin.setEnabled(cq_username.getText().length() > 0 && (cq_password.getText().length() > 0));

        TextWatcher afterTextChangedListener = new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                cq_gologin.setEnabled(cq_password.getText().length() > 0 && (cq_username.getText().length() > 0));
            }

            @Override
            public void afterTextChanged(Editable s) {
            }

        };
        cq_username.addTextChangedListener(afterTextChangedListener);
        cq_password.addTextChangedListener(afterTextChangedListener);

        cq_showPass.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                //选中状态 可以做一些操作
                cq_password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

            } else {
                //未选中状态 可以做一些操作
                cq_password.setTransformationMethod(PasswordTransformationMethod.getInstance());

            }
        });

        cq_wirtePass.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            //选中状态 可以做一些操作
            //未选中状态 可以做一些操作
            mEditor.putBoolean("newzjyrembPass", isChecked);
            mEditor.apply();
        });
        //其他登录 ck
        cq_otherLogin.setOnCheckedChangeListener((CompoundButton buttonView, boolean isChecked) -> {
            if (isChecked) {
                //选中状态 可以做一些操作
                cq_cook.setVisibility(View.VISIBLE);
            } else {
                cq_cook.setVisibility(View.GONE);
            }
        });
        cq_otherLogin.setEnabled(false);

        cq_gologin.setOnClickListener((view) -> {

            final String username = cq_username.getText().toString().trim();
            final String password = cq_password.getText().toString().trim();
            if (cq_wirtePass.isChecked()) {
                mEditor.putString("newzjyusername", username);
                mEditor.putString("newzjypassword", password);
                mEditor.commit();
            }

            if (username.isEmpty() || password.isEmpty()) {
                Tool.toast(mActivity, "账户密码不能为空");
                cq_loadingProgressBar.setVisibility(View.GONE);
                return;
            }


            cq_loadingProgressBar.setVisibility(View.VISIBLE);

            final CacheUs vCacheUs = new CacheUs(username, password);
            final String[] cache = vCacheUs.readCacheUs(mPreferences, "newzjy");
            final String chck = cache[0] ;
            Log.d(TAG, "loginDialog cache: " + chck);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    if (cq_otherLogin.isChecked()) {


                    } else {

                        if (!chck.equals("null")&&!chck.isEmpty()) {
                            mNewZjyUser = JSONObject.parseObject(chck, newZjyUser.class);
                            if(mNewZjyUser!=null) {
                                if (!newZjyMain.isLogin(mNewZjyUser)) {
                                    mNewZjyUser=null;
                                }
                            }
                        }
                        if (mNewZjyUser == null) {
                            mNewZjyUser = newZjyMain.MobileLogin(username, password);
                        }
                    }


                    mActivity.runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {

                            cq_loadingProgressBar.setVisibility(View.GONE);

                            // 更新UI的操作/
                            if (mNewZjyUser == null || mNewZjyUser.getLoginId() == null) {
                                Tool.toast(mActivity, "登录失败\n");
                                return;
                            }
                            Log.d(TAG, mNewZjyUser.getLoginId());

                           // mUserVModel.setNewZjyUser(mNewZjyUser);
                            newZjyUserDao.sNewZjyUser=mNewZjyUser;
                            new Thread(()->{
                                newZjyMain.upAuthorization(mNewZjyUser);
                               // Log.d(TAG, "run: "+mNewZjyUser.getUserAccessToken());
                            }).start();

                            String us=JSONObject.toJSONString(mNewZjyUser);
                            vCacheUs.writeCacheUs(mEditor, "newzjy",  us,"null");

                            Tool.toast(mActivity, "登录成功");
                            customAlert.dismiss();

                            mButton.performClick();

                        }
                    });

                }
            }).start();
        });
    }

    private void goClassRoom(int type){
        if (mNewZjyUser==null){
            Tool.toast(mActivity, "先登陆^_^...");
            return ;
        };

        Intent intent = new Intent(this, newzjy_classRoomActivity.class);
        intent.putExtra("type", type);
        startActivity(intent);
    }

}
