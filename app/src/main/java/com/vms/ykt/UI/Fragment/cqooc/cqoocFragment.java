package com.vms.ykt.UI.Fragment.cqooc;

import android.annotation.SuppressLint;
import android.content.Context;
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
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.cqoocActivity.cqooc_moreUserSkActivity;
import com.vms.ykt.UI.Adapter.cqoocAdapter.cqoocRecyclerAdapter;


import com.vms.ykt.UI.Fragment.baseFragment;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.Util.CacheUs;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.viewModel.ViewModelUtils;
import com.vms.ykt.viewModel.yktUserVM;
import com.vms.ykt.yktDao.cqooc.cqoocUserDao;
import com.vms.ykt.yktStuWeb.Cqooc.cqApi;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocCourseInfo;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocHttp;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocLogin;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocMain;
import com.vms.ykt.yktStuWeb.Cqooc.userInfo;

import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;

import static android.content.Context.MODE_PRIVATE;

public class cqoocFragment extends baseFragment {


    private static final String ARG_PARAM = "param_key";
    private static int goCqooc;
    private String mParam;
    private yktMainActivity mActivity;
    private final String TAG = this.getClass().getSimpleName();

    private View root = null;
    private Button mButton;
    private TextView mButton2;
    private RecyclerView mRecyclerView;
    private ProgressBar mProgressBar;
    private cqoocRecyclerAdapter mRecyclerAdapter;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    private List<cqoocCourseInfo> mCqoocCourseInfo;
    private userInfo mUserInfo = null;

    private static yktUserVM mUserVModel;

    public static Fragment newInstance(int icve) {
        cqoocFragment vIcveFragment = new cqoocFragment();
        //mUserVModel=((yktMainActivity)vIcveFragment.requireActivity()).mUserVModel;
       // mUserVModel=ViewModelUtils.getPublicViewModel(vIcveFragment.requireActivity(), userVModel.class,vIcveFragment.requireActivity());

        goCqooc=icve;
        return vIcveFragment;
    }

    @Override
    public void onAttach(@NotNull Context context) {
        super.onAttach(context);
        mActivity = (yktMainActivity) context;
        mUserVModel= ViewModelUtils.getViewModel(mActivity.getApplication(), yktUserVM.class);
        mParam = getArguments() != null ? getArguments().getString(ARG_PARAM) : "";
    }

    @Override
    public View onCreateView(@NotNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (root == null) {
            root = inflater.inflate(R.layout.ykt_cqooc_fragmt_view, container, false);

        }

        Log.d(TAG, "onCreateView: ");
        return root;
    }

    @Override
    public void onViewCreated(@NotNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "onViewCreated: ");
        initView(view);
        initListener();
        if (goCqooc==3){goCqooc=1;mButton.performClick();}
    }

    private void initView(View view) {
        mButton = view.findViewById(R.id.icve_bt_djjz);
        mButton2 = view.findViewById(R.id.cqooc_bt_bt);
        mSwipeRefreshLayout = view.findViewById(R.id.icve_swiperefresh);
        mRecyclerView = view.findViewById(R.id.icve_RecyclerView);
        mProgressBar = view.findViewById(R.id.icve_load_Bar);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false);
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
        mButton.setOnLongClickListener((view)->{
            Intent i = new Intent(mActivity, cqooc_moreUserSkActivity.class);
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

    private void loadData() {

        //mUserInfo=mUserVModel.getCqoocUser();
        if (mUserInfo == null || mUserInfo.getUsername() == null) {
            mSwipeRefreshLayout.setRefreshing(false);
            mProgressBar.setVisibility(View.GONE);;
            newInit("");
            loginDialog();
            return;
        }
        new Thread(new Runnable() {
            @Override
            public void run() {

                mCqoocCourseInfo = cqoocMain.getAllCourse(mUserInfo);

                mActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if ( mCqoocCourseInfo.size() != 0) {
                            if (mRecyclerAdapter == null) {
                                mRecyclerAdapter = new cqoocRecyclerAdapter(mCqoocCourseInfo);
                                mRecyclerView.setAdapter(mRecyclerAdapter);

                            } else {
                                mRecyclerAdapter.updateData(mCqoocCourseInfo);
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
        mPreferences = mActivity.getSharedPreferences("cquser", MODE_PRIVATE);
        mEditor = mPreferences.edit();

        boolean isRembPass = mPreferences.getBoolean("cqrembPass", false);
        String um = mPreferences.getString("cqusername", "");
        String pw = mPreferences.getString("cqpassword", "");

        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);

        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.cqooc_login_dialog, null);
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
            mEditor.putBoolean("cqrembPass", isChecked);
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

        cq_gologin.setOnClickListener((view) -> {
            final cqoocLogin vCqoocLogin = new cqoocLogin();
            final String username = cq_username.getText().toString().trim();
            final String password = cq_password.getText().toString().trim();
            if (cq_wirtePass.isChecked()) {
                mEditor.putString("cqusername", username);
                mEditor.putString("cqpassword", password);
                mEditor.commit();
            }

            if (username.isEmpty() || password.isEmpty()) {
                Tool.toast(mActivity, "账户密码不能为空");
                cq_loadingProgressBar.setVisibility(View.GONE);
                return;
            }


            cq_loadingProgressBar.setVisibility(View.VISIBLE);

            final CacheUs vCacheUs = new CacheUs(username, password);
            final String[] cache = vCacheUs.readCacheUs(mPreferences, "cq");
            final String chck = cache != null ? cache[1] : null;
            Log.d(TAG, "loginDialog cache: " + chck);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    final String ck;
                    if (cq_otherLogin.isChecked()) {
                        String userCk = cq_cook.getText().toString();

                        if (userCk.trim().isEmpty()) {
                            return;
                        }
                        newInit(userCk);
                        mUserInfo = cqoocMain.getUsreInfo(userCk);
                        ck = userCk;
                    } else {

                        if (!chck.equals("null")) {
                            newInit(chck);
                            mUserInfo = cqoocMain.getUsreInfo(chck);
                        }
                        if (mUserInfo == null) {
                            ck = vCqoocLogin.LoignIng(username, password);
                            if (ck != null && !ck.isEmpty()) {
                                newInit(ck);
                                mUserInfo = cqoocMain.getUsreInfo(ck);
                            }
                        } else {
                            ck = chck;
                            Log.d(TAG, "run: chche");
                        }
                    }


                    mActivity.runOnUiThread(new Runnable() {
                        @SuppressLint("SetTextI18n")
                        @Override
                        public void run() {

                            cq_loadingProgressBar.setVisibility(View.GONE);

                            // 更新UI的操作/
                            if (mUserInfo == null || mUserInfo.getUsername() == null) {
                                Tool.toast(mActivity, "登录失败\n" + ck);
                                return;
                            }



                            vCacheUs.writeCacheUs(mEditor, "cq", "null", ck);
                            mUserInfo.setCookie("player=1; xsid=" + ck);
                            Tool.toast(mActivity, "登录成功");
                            customAlert.dismiss();

                            cqoocUserDao.sUserInfo=mUserInfo;

                            Log.d(TAG, mUserInfo.getCookie());
                            Log.d(TAG, mUserInfo.getUsername());
                            Log.d(TAG, mUserInfo.getId());

                            mButton2.setText("cqooc\n 学习时长：" + mUserInfo.getStaytime() + " 分钟\n登录次数：" + mUserInfo.getSignNum());
                            mButton.performClick();

                        }
                    });

                }
            }).start();
        });
    }

    private void newInit(String ck){
        cqoocHttp.restCookie("player=1; xsid=" + ck);
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
