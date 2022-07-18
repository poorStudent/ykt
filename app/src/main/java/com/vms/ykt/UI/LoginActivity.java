package com.vms.ykt.UI;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ProgressBar;

import android.widget.TextView;


import com.alibaba.fastjson.JSONObject;

import com.vms.ykt.R;
import com.vms.ykt.TestActivity.BlankFragment;
import com.vms.ykt.Util.AppStatus;
import com.vms.ykt.Util.CacheUs;

import com.vms.ykt.Util.PermissionRequest;
import com.vms.ykt.TestActivity.testActivity;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktUtil.zjyLogin.zjyMobileLogin;
import com.vms.ykt.yktUtil.zjyLogin.zjyOtherLogin;
import com.vms.ykt.yktUtil.zjyLogin.zjyWebLogin;


public class LoginActivity extends Activity {

    private String TAG = this.getClass().getSimpleName();
    private TextView cqooc;
    public EditText userNameEdit, passWordEdit, cookEdit;
    public androidx.appcompat.widget.SwitchCompat showPassSwitch, wirtePassSwitch, webLoginSwitch, otherLoginSwitch;
    public Button goLoginButton;
    public ProgressBar loadingProgressBar;
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;
    private PermissionRequest mPermissionRequest;
    private String cookI = "";
    private boolean isRembPass;

    static {
        System.loadLibrary("native-lib");
    }

    private boolean isLoging=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initId();
        initData();
        initListener();
        new BlankFragment();
        Log.d(TAG, "onCreate: " + Tool.getDEVICEModle());

    }

    private void initId() {
        userNameEdit = findViewById(R.id.cq_username);
        passWordEdit = findViewById(R.id.cq_password);
        cookEdit = findViewById(R.id.cq_cook);
        showPassSwitch = findViewById(R.id.zjy_showPass);
        wirtePassSwitch = findViewById(R.id.zjy_wirtePass);
        webLoginSwitch = findViewById(R.id.zjy_WebLogin);
        otherLoginSwitch = findViewById(R.id.zjy_otherLogin);
        goLoginButton = findViewById(R.id.zjy_gologin);
        loadingProgressBar = findViewById(R.id.cq_loadingProgressBar);
        cqooc = findViewById(R.id.cqooc);
    }

    private void initData() {

        mPermissionRequest = new PermissionRequest();
        mPermissionRequest.checkPermission(this);

        mPreferences = getSharedPreferences("user", MODE_PRIVATE);
        mEditor = mPreferences.edit();
        isRembPass = mPreferences.getBoolean("rembPass", false);
        if (isRembPass) {
            String um = mPreferences.getString("username", "");
            String pw = mPreferences.getString("password", "");
            userNameEdit.setText(um);
            passWordEdit.setText(pw);
            wirtePassSwitch.setChecked(isRembPass);
        }

        if (passWordEdit.getText().length() > 0 && (userNameEdit.getText().length() > 0)) {
            goLoginButton.setEnabled(true);
        } else {
            goLoginButton.setEnabled(false);
        }

    }

    private void initListener() {

        cqooc.setOnClickListener((view) -> {

            Intent intent = new Intent(LoginActivity.this, yktMainActivity.class);
            intent.putExtra("goCqooc", 3);
            startActivity(intent);
            finish();

        });
        cqooc.setOnLongClickListener((view) -> {

            Intent intent = new Intent(LoginActivity.this, testActivity.class);
            intent.putExtra("goCqooc", 3);
            startActivity(intent);
            return true;
        });
        goLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLoging){
                    Login();
                }
            }
        });

        showPassSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中状态 可以做一些操作
                    passWordEdit.setTransformationMethod(HideReturnsTransformationMethod.getInstance());

                } else {
                    //未选中状态 可以做一些操作
                    passWordEdit.setTransformationMethod(PasswordTransformationMethod.getInstance());

                }
            }
        });
        wirtePassSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中状态 可以做一些操作
                    mEditor.putBoolean("rembPass", true);
                    mEditor.commit();
                } else {
                    //未选中状态 可以做一些操作
                    mEditor.putBoolean("rembPass", false);
                    mEditor.commit();

                }
            }
        });
        //其他登录 ck
        otherLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中状态 可以做一些操作
                    webLoginSwitch.setChecked(false);
                    Tool.toast(getApplicationContext(), "未开放请用默认登录");
                    otherLoginSwitch.setChecked(false);
                }
            }
        });
        //web版登录
        webLoginSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    //选中状态 可以做一些操作
                    otherLoginSwitch.setChecked(false);
                    Tool.toast(getApplicationContext(), "未开放请用默认登录");
                    webLoginSwitch.setChecked(false);
                }
            }
        });

        userNameEdit.addTextChangedListener(afterTextChangedListener);
        passWordEdit.addTextChangedListener(afterTextChangedListener);

    }

    TextWatcher afterTextChangedListener = new TextWatcher() {

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // ignore
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (passWordEdit.getText().length() > 0 && (userNameEdit.getText().length() > 0)) {
                goLoginButton.setEnabled(true);
            } else {
                goLoginButton.setEnabled(false);
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    private boolean isLogin(zjyUser zjyUser) {
        String resp = zjyApi.getUserInfo(zjyUser);
        Log.d(TAG, "isLogin: " + resp);
        if (resp == null || resp.isEmpty()) return true;
        if (JSONObject.parseObject(resp).getString("code").equals("1")) return false;
        return true;
    }

    private void Login() {

        loadingProgressBar.setVisibility(View.VISIBLE);
        final String username = userNameEdit.getText().toString().trim();
        final String password = passWordEdit.getText().toString().trim();
        if (wirtePassSwitch.isChecked()) {
            mEditor.putString("username", username);
            mEditor.putString("password", password);
            mEditor.commit();
        }


        if (username.isEmpty() || password.isEmpty()) {
            Tool.toast(getApplicationContext(), "账户密码不能为空");
            loadingProgressBar.setVisibility(View.GONE);
            return;
        }


        final CacheUs vCacheUs = new CacheUs(username, password);

        isLoging=false;
        new Thread(new Runnable() {

            @Override
            public void run() {
                AppStatus.AppStatusInit();

                String[] ret = null;
                final String resp;
                final String appv;
                final String ck;

                String ap;
                ap = zjyApi.getAppVersion1();
                if (ap == null || ap.isEmpty()) {
                    appv = zjyApi.getAppVersion2();
                } else {
                    appv = ap;
                }

                if (webLoginSwitch.isChecked()) {
                    ret = zjyWebLogin.login(username, password, getApplicationContext());
                    resp = ret[0];
                    ck = ret[1];
                } else if (otherLoginSwitch.isChecked()) {
                    String userInfo = cookEdit.getText().toString();
                    zjyOtherLogin.login(userInfo);
                    resp = userInfo;
                    ck = ret[1];
                } else {
                    zjyMobileLogin.appv = appv;
                    String[] cache = vCacheUs.readCacheUs(mPreferences, "ykt");

                    if (!cache[0].equals("null")) {
                        zjyUser vZjyUser = JSONObject.parseObject(cache[0], zjyUser.class);
                        vZjyUser.setAppv(appv);
                        Log.d(TAG, "run: " + cache[0]);
                        if (isLogin(vZjyUser)) {
                            ret = zjyMobileLogin.login(username, password);
                        } else {
                            ret = cache;
                            Log.d(TAG, "run: cache");
                        }
                    } else {
                        ret = zjyMobileLogin.login(username, password);
                    }

                    resp = ret[0];
                    ck = ret[1];

                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        isLoging=true;
                        loadingProgressBar.setVisibility(View.GONE);

                        if (resp == null) return;
                        if (!JSONObject.parseObject(resp).getString("code").equals("1")) {
                            Tool.toast(LoginActivity.this, resp);
                            return;
                        }

                        // 更新UI的操作/
                        zjyUser ZjyUser = JSONObject.parseObject(resp, zjyUser.class);


                        if (AppStatus.getYunbai() != null && (AppStatus.getYunbai().contains(ZjyUser.getDisplayName()) || AppStatus.getYunbai().contains(ZjyUser.getUserName()))) {
                        } else {
                            if (AppStatus.getAll() == null || !AppStatus.getAll().equals("allok")) {

                                //return;
                            }
                        }


                        Tool.toast(LoginActivity.this, "登录成功");
                        vCacheUs.writeCacheUs(mEditor, "ykt", resp, ck);

                        Intent intent = new Intent(LoginActivity.this, yktMainActivity.class);
                        intent.putExtra("userInfo", resp);
                        intent.putExtra("appv", appv);
                        intent.putExtra("ck", ck);
                        startActivity(intent);
                        finish();

                    }
                });

            }
        }).start();
    }

    public native String stringFromJNI();

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        boolean hasPermissionDismiss = false;      //有权限没有通过
        if (mPermissionRequest.MY_PERMISSIONS_REQUEST_CODE == requestCode) {
            for (int i = 0; i < grantResults.length; i++) {
                if (grantResults[i] == -1) {
                    hasPermissionDismiss = true;   //发现有未通过权限
                    break;
                }
            }
        }

        if (hasPermissionDismiss) {
            //如果有没有被允许的权限
            //假如存在有没被允许的权限,可提示用户手动设置 或者不让用户继续操作
            mPermissionRequest.showPermissionDialog(this,permissions);

        } else {
            Log.e(TAG, "onRequestPermissionsResult >>>已全部授权");
        }

    }
}

