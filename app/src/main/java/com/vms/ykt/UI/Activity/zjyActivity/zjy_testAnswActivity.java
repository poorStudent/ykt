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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.Util.AppStatus;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.answerInfo;
import com.vms.ykt.yktStuMobile.zjy.questionIfon;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyCouresActivitInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;

public class zjy_testAnswActivity extends AppCompatActivity {
    private static final String TAG = zjy_testAnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private zjyCourseIfno mCourseIfno;


    private View root = null;
    private TextView mButton2,zjy_exam_answ;
    private Button mButton;
    private String flag;
    private ProgressBar mProgressBar;

    private zjyTeachInfo mZjyTeachInfo;
    private zjyCouresActivitInfo mZjyCouresActivitInfo;
    private zjyHttpW mZjyHttpW;
    private zjyApiW mZjyApiW;
    private zjyMainW mZjyMainW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjy_exam_answactivity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = zjy_testAnswActivity.this;
        this.mCourseIfno = (zjyCourseIfno) i.getSerializableExtra("Course");
        this.mZjyTeachInfo = (zjyTeachInfo) i.getSerializableExtra("mZjyTeachInfo");
        this.mZjyCouresActivitInfo = (zjyCouresActivitInfo) i.getSerializableExtra("vZjyCouresActivitInfo");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");
        this.flag = (String) i.getSerializableExtra("flag");
        this.mZjyHttpW=new zjyHttpW();
        this.mZjyApiW=new zjyApiW();
        this.mZjyMainW=new zjyMainW();
        mZjyHttpW.setUserCookie(mZjyUser.getCookie());
        mZjyApiW.setZjyHttpW(mZjyHttpW);
        mZjyMainW.setZjyApiW(mZjyApiW);
        Log.d(TAG, "initData: " + mZjyUser.getUserId());
    }

    private void initView() {
        mButton = findViewById(R.id.zjy_bt_djjz);
        mButton2 = findViewById(R.id.zjy_bt_jrkt);
        mProgressBar = findViewById(R.id.zjy_load_Bar);
        zjy_exam_answ = findViewById(R.id.zjy_exam_answ);

    }

    private void initListener() {



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
                //if (AppStatus.getAll()==null||AppStatus.getZjyzy()==null||!AppStatus.getZjyzy().equals("zjyok"))return;
               final String answ= doWork();

                Log.d(TAG, "run: "+answ);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        if (!answ.isEmpty()) {
                            zjy_exam_answ.setText(answ);

                            mButton.setVisibility(View.GONE);
                        } else {
                            Tool.toast(mContext, "获取失败...");
                        }
                        mProgressBar.setVisibility(View.GONE);
                    }
                });

            }
        }).start();
    }

    private String doWork(){
     return doWorkM();
    }

    private String doWorkW(){
        return "";
    }

    private String doWorkM() {
        String resp;
        AppStatus.AppStatusInit();
        if (AppStatus.getYunhei()!=null) {
            if (AppStatus.getYunhei().contains(mZjyUser.getDisplayName()) || AppStatus.getYunhei().contains(mZjyUser.getUserName()))
                return "";
        }
        resp = zjyApi.getAndSaveStuAnswerRecord(mZjyUser, mZjyTeachInfo.getOpenClassId(), mZjyCouresActivitInfo.getId());
        if (resp==null||!resp.contains("stuQuesList"))return resp+"";
        JSONObject json = JSONObject.parseObject(resp);
        String stuQuesList=json.getString("stuQuesList");
        List<questionIfon> varQuestionIfons = JSONArray.parseArray(stuQuesList,questionIfon.class);
        ArrayList<answerInfo> varAnswerInfos = zjyMain.parseAnsw(varQuestionIfons);
        String answ=zjyMain.showAnsw(varAnswerInfos);
        if (answ.isEmpty())answ=zjyMain.getAskAnswer2(varQuestionIfons);
        return answ+"";
    }
}


