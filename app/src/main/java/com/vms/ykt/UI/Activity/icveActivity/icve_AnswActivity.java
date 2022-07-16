package com.vms.ykt.UI.Activity.icveActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.vms.ykt.R;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.AnswersInfo;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;
import com.vms.ykt.yktStuWeb.icve.workExamListInfo;

import java.util.List;

public class icve_AnswActivity extends AppCompatActivity {
    private static final String TAG = icve_AnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private icveCourseInfo mCourseIfno;
    private workExamListInfo mWorkExamListInfo;

    private View root = null;
    private TextView mButton2, zjy_exam_answ;
    private Button mButton;
    private String flag;
    private String Workflag;
    private ProgressBar mProgressBar;
    private Thread mThread;
    private boolean isDoWork = true;

    private icveMainW mIcveMainW;
    private icveApiW mIcveApiW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icve_work_answactivity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = icve_AnswActivity.this;
        this.mCourseIfno = (icveCourseInfo) i.getSerializableExtra("Course");
        this.mWorkExamListInfo = (workExamListInfo) i.getSerializableExtra("vWorkExamListInfo");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");
        this.flag = (String) i.getSerializableExtra("flag");
        this.Workflag = (String) i.getSerializableExtra("Workflag");
        icveHttpW mIcveHttpW = new icveHttpW();
        mIcveHttpW.setUserCookie(mZjyUser.getCookie());
        this.mIcveApiW = new icveApiW();
        mIcveApiW.setIcveHttpW(mIcveHttpW);
        this.mIcveMainW = new icveMainW();
        mIcveMainW.setIcveApiW(mIcveApiW);
        Log.d(TAG, "initData: " + mCourseIfno.getTitle());
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
            if (isDoWork) {
                isDoWork = false;
                mProgressBar.setVisibility(View.VISIBLE);
                loadData();
            }

        });
        mButton2.setOnClickListener((View v) -> {


        });
    }

    private void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // if (AppStatus.getAll()==null||!AppStatus.getZjyzy().equals("zjyok"))return;
                final String answ = doWork();

                Log.d(TAG, "run: " + answ);
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
                isDoWork = true;
            }
        }).start();
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

        if (mWorkExamListInfo.getType()==1){
            String data = mIcveApiW.getWorkPerview(mCourseIfno.getId(), mWorkExamListInfo.getId());
            if (data == null || data.isEmpty()) {
                return data;
            }
            List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPapers(data);
            if (vAnswersInfoList.size() == 0) {
                vAnswersInfoList = mIcveMainW.getAnswArrays(data);
            }
            if (vAnswersInfoList.size() == 0) {
                return data;
            }
            return mIcveMainW.paserAnsw(vAnswersInfoList);
        }else {
            String data = mIcveApiW.getExamPerview(mCourseIfno.getId(),mWorkExamListInfo.getId());
            if (data == null || data.isEmpty()) {
                return data;
            }
            List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPapers(data);
            if (vAnswersInfoList.size() == 0) {
                vAnswersInfoList = mIcveMainW.getAnswArrays(data);
            }
            if (vAnswersInfoList.size() == 0) {
                return data;
            }
            return mIcveMainW.paserAnsw(vAnswersInfoList);
        }

    }

}


