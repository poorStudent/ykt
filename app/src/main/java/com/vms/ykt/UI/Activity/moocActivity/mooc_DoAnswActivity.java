package com.vms.ykt.UI.Activity.moocActivity;

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

import com.vms.ykt.R;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.mooc.homeWorkAnswInfo;
import com.vms.ykt.yktStuMobile.mooc.moocApi;
import com.vms.ykt.yktStuMobile.mooc.moocCellInfo;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.yktStuMobile.mooc.moocMianM;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.mooc.WorkExamList;

import java.util.List;

public class mooc_DoAnswActivity extends AppCompatActivity {
    private static final String TAG = mooc_DoAnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private moocCourseInfo mCourseIfno;
    private WorkExamList mWorkExamList;

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
        setContentView(R.layout.mooc_do_answactivity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = mooc_DoAnswActivity.this;
        this.mCourseIfno = (moocCourseInfo) i.getSerializableExtra("Course");
        this.mWorkExamList = (WorkExamList) i.getSerializableExtra("HomeworkInfo");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");
        this.flag = (String) i.getSerializableExtra("flag");
        this.Workflag = (String) i.getSerializableExtra("Workflag");

        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
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

        List<homeWorkAnswInfo> StuAnsw;
        moocCellInfo varCellInfo = new moocCellInfo();
        varCellInfo.setResId(mWorkExamList.getId());
        String workExamType = Workflag;

        String unid = moocMianM.getExamRecord(mZjyUser, mCourseIfno, varCellInfo, workExamType);
        Log.d(TAG, "doWorkexam: " + unid);
        String Preview = moocMianM.getExamPreview(mZjyUser, mCourseIfno, varCellInfo, workExamType);
        if (unid == null || unid.isEmpty())
            unid = Tool.parseJsonS(Preview, "uniqueId");

        StuAnsw = moocMianM.getStuAnsw(mZjyUser, mCourseIfno, varCellInfo.getResId());
        if (unid != null && !StuAnsw.isEmpty() && !unid.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {

            return postHomeWork(StuAnsw, mZjyUser, unid, mCourseIfno, varCellInfo, workExamType);

        }

        StuAnsw = moocMianM.getStuAnsw2(mZjyUser, mCourseIfno, varCellInfo, Preview, workExamType);
        if (unid != null && !StuAnsw.isEmpty() && !unid.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {

            return postHomeWork(StuAnsw, mZjyUser, unid, mCourseIfno, varCellInfo, workExamType);
        }
        runOnUiThread(() -> {
            mProgressBar.setVisibility(View.GONE);
        });
        stringBuffer.append("\n");
        stringBuffer.append("异常未作答");
        mHandler.sendEmptyMessage(100);
        return "";
    }

    private String postHomeWork(List<homeWorkAnswInfo> StuAnsw, zjyUser ZjyUser, String unid, moocCourseInfo CourseInfo, moocCellInfo varCellInfo, String workExamType) {
        runOnUiThread(() -> {
            mProgressBar.setVisibility(View.GONE);
        });
        String resp = "";
        curct = 0;
        ct = StuAnsw.size();
        for (homeWorkAnswInfo varInfo : StuAnsw) {

            if (mThread.isInterrupted()) {
                // return null;
            }
            stringBuffer.append("\n" + varInfo.getTitle());
            stringBuffer.append(" *" + varInfo.getQuestionId());
            stringBuffer.append("\n" + varInfo.getAnswer());
            mHandler.sendEmptyMessage(100);

            if (varInfo.getAnswer().isEmpty()) {
                stringBuffer.append("\n答案空跳过");
                mHandler.sendEmptyMessage(100);
                continue;
            }
            Log.d(TAG, "postHomeWork: " + varInfo.getTitle());
            Log.d(TAG, "postHomeWork: " + varInfo.getAnswer());
            Log.d(TAG, "postHomeWork: " + varInfo.getQuestionId());

            Tool.waitTime(mooc_do_zdjg);
            resp = "其他类型题目无法作答 跳过";
            if (Integer.parseInt(varInfo.getQuestionType()) > 3) {


            } else if (Integer.parseInt(varInfo.getQuestionType()) == 8) {


            } else {

                resp = moocApi.getonlineHomeworkAnswer(ZjyUser, unid, varInfo.getAnswer(), varInfo.getQuestionId(), workExamType);
                curct++;
            }
            stringBuffer.append("\n");
            stringBuffer.append(resp);
            mHandler.sendEmptyMessage(100);

            runOnUiThread(() -> {
                mooc_shuake_jd.setText(curct + "/" + ct);
            });

            Log.d(TAG, "postHomeWork: " + resp);
        }

        if (ct == curct) {

            resp = moocApi.getWorkExamSave(ZjyUser, CourseInfo, varCellInfo, unid, mooc_do_zdsj + "", workExamType);
        } else {
            resp = " 有未作答题 请自行作答提交 ";
        }
        stringBuffer.append("\n");
        stringBuffer.append(resp);
        mHandler.sendEmptyMessage(100);
        Log.d(TAG, "postHomeWork: " + resp);
        return resp;
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


