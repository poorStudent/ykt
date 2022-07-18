package com.vms.ykt.UI.Activity.zjyActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.ykt.R;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

public class zjy_workDoActivity extends AppCompatActivity {
    private static final String TAG = zjy_workDoActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private zjyCourseIfno mCourseIfno;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjy_work_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = zjy_workDoActivity.this;
        this.mCourseIfno = (zjyCourseIfno) i.getSerializableExtra("Course");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");

    }

    private void initView() {

    }

    private void initListener() {

    }

    private void loadData() {
    }
}

