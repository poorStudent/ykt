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
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.zjy.HomeworkInfo;
import com.vms.ykt.yktStuMobile.zjy.answerInfo;
import com.vms.ykt.yktStuMobile.zjy.homeWorkViewInfo;
import com.vms.ykt.yktStuMobile.zjy.questionIfon;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;

public class zjy_workAnswActivity extends AppCompatActivity {
    private static final String TAG = zjy_workAnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private zjyCourseIfno mCourseIfno;
    private HomeworkInfo mHomeworkInfo;

    private View root = null;
    private TextView mButton2,zjy_exam_answ;
    private Button mButton;
    private String flag;
    private ProgressBar mProgressBar;


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
        this.mContext = zjy_workAnswActivity.this;
        this.mCourseIfno = zjyUserDao.sZjyCourseIfno;
        this.mHomeworkInfo =zjyUserDao.sHomeworkInfo ;
        this.mZjyUser = zjyUserDao.sZjyUser;
        this.flag = (String) i.getSerializableExtra("flag");
        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
        Log.d(TAG, "initData: " + mZjyUser.getUserId());
        Log.d(TAG, "initData: " + mHomeworkInfo.getId());
        Log.d(TAG, "initData: " + mHomeworkInfo.getHomeworkId());
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
        AppStatus.AppStatusInit();
        if (AppStatus.getYunhei()!=null) {
            if (AppStatus.getYunhei().contains(mZjyUser.getDisplayName()) || AppStatus.getYunhei().contains(mZjyUser.getUserName()))
                return "";
        }
       // if (AppStatus.getZjyzy()==null||!AppStatus.getZjyzy().equals("zjyok"))return"";

        if (flag.equals("1"))return doWorkW();
     return doWorkM();
    }

    private String doWorkW(){
        homeWorkViewInfo vHomeWorkViewInfo;
         String resp= zjyApiW.getPreview(mCourseIfno, mHomeworkInfo);
        Log.d(TAG, "doWorkW: "+resp);
        if (resp==null||!resp.contains("redisData"))return resp+"";
        vHomeWorkViewInfo = JSONObject.parseObject(resp, homeWorkViewInfo.class);
        resp=vHomeWorkViewInfo.getRedisData();
        Tool.logi("",resp);
        resp=JSONObject.parseObject(resp).getString("questions");
        Tool.logi("",resp);
        List<questionIfon> varQuestionIfons = JSONArray.parseArray(resp, questionIfon.class);
        ArrayList<answerInfo> varAnswerInfos = zjyMain.parseAnsw(varQuestionIfons);
        String answ=zjyMain.showAnsw(varAnswerInfos);
        if (answ.isEmpty())answ=zjyMain.getAskAnswer2(varQuestionIfons);
        return answ+"";
    }

    private String doWorkM() {
        String resp;
        resp = zjyApi.getHomeworkStuRecord(mZjyUser,mCourseIfno , mHomeworkInfo);
        resp = zjyApi.getPreview(mZjyUser,mCourseIfno , mHomeworkInfo);
        if (resp==null||!resp.contains("data"))return resp+"";
        JSONObject json = JSONObject.parseObject(resp);
        String termId = json.getString("termId");
        String hkResId = json.getString("hkResId");
        JSONObject data = json.getJSONObject("data");
        homeWorkViewInfo varHomeWorkViewInfo = JSONObject.parseObject(data.toJSONString(), homeWorkViewInfo.class);
        varHomeWorkViewInfo.setTermId(termId);
        List<questionIfon> varQuestionIfons = JSONArray.parseArray(varHomeWorkViewInfo.getRedisData(), questionIfon.class);
        ArrayList<answerInfo> varAnswerInfos = zjyMain.parseAnsw(varQuestionIfons);
        String answ=zjyMain.showAnsw(varAnswerInfos);
        if (answ.isEmpty())answ=zjyMain.getAskAnswer2(varQuestionIfons);
        return answ+"";

    }

}


