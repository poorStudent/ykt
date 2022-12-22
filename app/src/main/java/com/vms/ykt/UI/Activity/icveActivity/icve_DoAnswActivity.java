package com.vms.ykt.UI.Activity.icveActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.icve.icveUserDao;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.AnswersInfo;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;
import com.vms.ykt.yktStuWeb.icve.workExamListInfo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class icve_DoAnswActivity extends AppCompatActivity {
    private static final String TAG = icve_DoAnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private icveCourseInfo mCourseIfno;
    private workExamListInfo mWorkExamListInfo;

    private View root = null;
    private TextView mButton2, mooc_shuke_skrz, mooc_shuake_jd;
    private EditText mooc_zdsj, mooc_zdjg;
    private int mooc_do_zdsj = 1200, mooc_do_zdjg = 500;
    private Button mButton;
    private String flag;
    private String Workflag;
    private ProgressBar mProgressBar;
    private StringBuilder stringBuffer;
    private int ct, curct;
    private Thread mThread;
    private boolean isDoWork = true;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icve_do_answactivity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = icve_DoAnswActivity.this;
        mCourseIfno = icveUserDao.sIcveCourseInfo;
        mZjyUser = zjyUserDao.sZjyUser;
        this.mWorkExamListInfo =icveUserDao.sWorkExamListInfo ;
        this.flag = (String) i.getSerializableExtra("flag");
        this.Workflag = (String) i.getSerializableExtra("Workflag");
        Log.d(TAG, "initData: " + mCourseIfno.getTitle());
        Log.d(TAG, "initData: " + mZjyUser.getUserId());

    }

    private void initView() {
        mButton = findViewById(R.id.zjy_bt_djjz);
        mButton2 = findViewById(R.id.zjy_bt_jrkt);
        mProgressBar = findViewById(R.id.zjy_load_Bar);
        mooc_shuke_skrz = findViewById(R.id.zjy_exam_answ);
        mooc_zdsj = findViewById(R.id.mooc_zdsj);
        mooc_zdjg = findViewById(R.id.mooc_zdjg);
        mooc_shuake_jd = findViewById(R.id.mooc_shuake_jd);
        mooc_shuke_skrz.setMovementMethod(ScrollingMovementMethod.getInstance());
        mooc_shuake_jd.setText("进度: 0/0");
    }

    private void initListener() {

        mButton.setOnClickListener((View v) -> {

            mProgressBar.setVisibility(View.VISIBLE);
            mooc_shuake_jd.setText("进度: 0/0");
            mThread = new Thread(() -> {
                if (isDoWork) {
                    isDoWork = false;
                    loadData();
                    isDoWork = true;
                }
            });
            mThread.start();
        });
        mButton2.setOnClickListener((View v) -> {


        });
    }

    private void loadData() {


        // if (AppStatus.getAll()==null||!AppStatus.getZjyzy().equals("zjyok"))return;
        if (!mooc_zdsj.getText().toString().trim().isEmpty()) {
            mooc_do_zdsj = Integer.parseInt(mooc_zdsj.getText().toString().trim());
        }
        if (!mooc_zdjg.getText().toString().trim().isEmpty()) {
            mooc_do_zdjg = Integer.parseInt(mooc_zdjg.getText().toString().trim());
        }

        String answ = doWork();

        Log.d(TAG, "run: " + answ);


    }

    private String doWork() {
        //if (flag.equals("1"))return doWorkW();
        return doWorkM();
    }

    private String doWorkW() {
        String answ = "";
        return answ + "";
    }

    private String doWorkM() {
        stringBuffer = new StringBuilder();
        ct = 0;
        curct = 0;

        if (mWorkExamListInfo.getType() == 1) {
            DoWork(mWorkExamListInfo);
        } else {
            DoExam(mWorkExamListInfo);
        }

        runOnUiThread(() -> {
            mProgressBar.setVisibility(View.GONE);
        });
        return "";
    }

    private void DoWork(workExamListInfo WorkInfo) {
        Log.d(TAG, "DoWork: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 作业->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = icveApiW.getWorkPerview(mCourseIfno.getId(), WorkInfo.getId());
        if (data==null||!data.contains("answer")){
            stringBuffer.append("\n 作业->");
            stringBuffer.append(data);
            mHandler.sendEmptyMessage(100);
            return;
        }
        String answerId = JSONObject.parseObject(data).getJSONObject("answer").getString("Id");
        if (answerId == null || answerId.isEmpty()) {
            stringBuffer.append("\n 作业->获取id失败 跳过");
            mHandler.sendEmptyMessage(100);
            return;
        }
        List<AnswersInfo> vAnswersInfoList = icveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = icveMainW.getAnswArrays(data);
        }
        if (vAnswersInfoList.size() == 0) {
            return;
        }
        ct=vAnswersInfoList.size();
        List<HashMap<Object, Object>> answList = new ArrayList<>();

        for (AnswersInfo vAnswersInfo : vAnswersInfoList) {
            HashMap<Object, Object> answHashMap = new HashMap<>();
            answHashMap.put("paperItemId", vAnswersInfo.getId());
            answHashMap.put("answer", vAnswersInfo.getAnswersContent());
            answList.add(answHashMap);
            stringBuffer.append("\n");
            stringBuffer.append(vAnswersInfo.getId());
            stringBuffer.append(" ");
            stringBuffer.append(vAnswersInfo.getAnswersContent());
            mHandler.sendEmptyMessage(100);
            if (vAnswersInfo.getAnswersContent() != null && !vAnswersInfo.getAnswersContent().isEmpty()) {
                curct++;
                runOnUiThread(() -> {
                    mooc_shuake_jd.setText(curct + "/" + ct);
                });
            }
        }
        if (curct != ct) {
            stringBuffer.append("\n 作业->" + "有未作题目 自行作答提交");
            mHandler.sendEmptyMessage(100);
            return;
        }
        String asnwJson = JSONArray.toJSONString(answList);
        Log.d(TAG, "doTest: " + asnwJson);
        String resp = icveApiW.getAnswerOnlineWorks(answerId, asnwJson);
        stringBuffer.append("\n 作业->" + resp);
        mHandler.sendEmptyMessage(100);
    }

    private void DoExam(workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 考试->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = icveApiW.getExamPerview(mCourseIfno.getId(), WorkInfo.getId());
        if (data==null||!data.contains("answer")){
            stringBuffer.append("\n 考试->");
            stringBuffer.append(data);
            mHandler.sendEmptyMessage(100);
            return;
        }
        String answerId = JSONObject.parseObject(data).getJSONObject("answer").getString("Id");
        if (answerId == null || answerId.isEmpty()) {
            stringBuffer.append("\n 考试->获取id失败 跳过");
            mHandler.sendEmptyMessage(100);
            return;
        }

        List<AnswersInfo> vAnswersInfoList = icveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = icveMainW.getAnswArrays(data);
        }
        if (vAnswersInfoList.size() == 0) {
            return;
        }
        ct=vAnswersInfoList.size();
        List<HashMap<Object, Object>> answList = new ArrayList<>();
        for (AnswersInfo vAnswersInfo : vAnswersInfoList) {
            HashMap<Object, Object> answHashMap = new HashMap<>();
            answHashMap.put("paperItemId", vAnswersInfo.getId());
            answHashMap.put("answer", vAnswersInfo.getAnswersContent());
            answList.add(answHashMap);
            stringBuffer.append("\n");
            stringBuffer.append(vAnswersInfo.getId());
            stringBuffer.append(" ");
            stringBuffer.append(vAnswersInfo.getAnswersContent());
            mHandler.sendEmptyMessage(100);

            if (vAnswersInfo.getAnswersContent() != null && !vAnswersInfo.getAnswersContent().isEmpty()) {
                curct++;
                runOnUiThread(() -> {

                    mooc_shuake_jd.setText(curct + "/" + ct);
                });
            }
        }
        if (curct != ct) {
            stringBuffer.append("\n 考试->" + "有未作题目 自行作答提交");
            mHandler.sendEmptyMessage(100);
            return;
        }
        String asnwJson = JSONArray.toJSONString(answList);
        Log.d(TAG, "doTest: " + asnwJson);
        String resp = icveApiW.getAnswerExam(answerId, asnwJson);
        stringBuffer.append("\n 考试->" + resp);
        mHandler.sendEmptyMessage(100);
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                Tool.tvShowBottom(mooc_shuke_skrz);
                mooc_shuke_skrz.setText(stringBuffer.toString());
                Tool.tvShowBottom(mooc_shuke_skrz);
            }
            if (msg.what == 0) {

            }
        }
    };

    @Override
    protected void onDestroy() {
        if (mThread != null) mThread.interrupt();
        super.onDestroy();
    }
}


