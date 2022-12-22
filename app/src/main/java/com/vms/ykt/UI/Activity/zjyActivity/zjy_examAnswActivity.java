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
import com.vms.ykt.yktStuMobile.zjy.ExamInfo;
import com.vms.ykt.yktStuMobile.zjy.ExamViewInfo;
import com.vms.ykt.yktStuMobile.zjy.answerInfo;
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

public class zjy_examAnswActivity extends AppCompatActivity {
    private static final String TAG = zjy_examAnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private zjyCourseIfno mCourseIfno;
    private ExamInfo mExamInfo;

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
        this.mContext = zjy_examAnswActivity.this;
        this.mCourseIfno =zjyUserDao.sZjyCourseIfno ;
        this.mExamInfo = zjyUserDao.sExamInfo;
        this.mZjyUser = zjyUserDao.sZjyUser;
        this.flag = (String) i.getSerializableExtra("flag");
        
        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
        Log.d(TAG, "initData: " + mZjyUser.getUserId());
        Log.d(TAG, "initData: " + mExamInfo.getId());
        Log.d(TAG, "initData: " + mExamInfo.getExamId());
        Log.d(TAG, "initData: " + mExamInfo.getExamStuId());
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
                final String answ= doExam();
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

    private String doExam() {
        AppStatus.AppStatusInit();
        if (AppStatus.getYunhei()!=null) {
            if (AppStatus.getYunhei().contains(mZjyUser.getDisplayName()) || AppStatus.getYunhei().contains(mZjyUser.getUserName()))
                return "";
        }
       // if (AppStatus.getZjyks()==null||!AppStatus.getZjyks().equals("zjyok"))return"";

        if (flag.equals("1"))return doExamW();
        return doExamM();
    }

    private String doExamW(){
        ExamViewInfo vExamViewInfo;
         String resp= zjyApiW.getPreviewNew(mCourseIfno, mExamInfo);
        if (resp==null||!resp.contains("questions"))return "";
        vExamViewInfo = JSONObject.parseObject(resp, ExamViewInfo.class);
        resp=vExamViewInfo.getQuestionData();
        resp=JSONObject.parseObject(resp).getString("questions");
        List<questionIfon> varQuestionIfons = JSONArray.parseArray(resp, questionIfon.class);
        ArrayList<answerInfo> varAnswerInfos = zjyMain.parseAnsw(varQuestionIfons);
        String answ=zjyMain.showAnsw(varAnswerInfos);
        if (answ.isEmpty())answ=zjyMain.getAskAnswer2(varQuestionIfons);
        return answ;
    }

    private String doExamM() {
        Log.d(TAG, "doExamM: "+zjyApi.getExamIsAuthenticate(mZjyUser,mCourseIfno, mExamInfo));
        Log.d(TAG, "doExamM: "+zjyApi.getMqttTokenByExam(mZjyUser,mCourseIfno, mExamInfo));
        String resp = zjyApi.getNewExamPreviewNew(mZjyUser,mCourseIfno, mExamInfo);
        if (resp==null||!resp.contains("data"))return resp+"";
        JSONObject json = JSONObject.parseObject(resp);
        JSONObject data = json.getJSONObject("data");
        ExamViewInfo varExamViewInfo = JSONObject.parseObject(data.toJSONString(), ExamViewInfo.class);
        List<questionIfon> varQuestionIfons = JSONArray.parseArray(varExamViewInfo.getQuestionJson(), questionIfon.class);
        ArrayList<answerInfo> varAnswerInfos = zjyMain.parseAnsw(varQuestionIfons);
        String answ=zjyMain.showAnsw(varAnswerInfos);
        if (answ.isEmpty())answ=zjyMain.getAskAnswer2(varQuestionIfons);
        return answ;
    }

}


