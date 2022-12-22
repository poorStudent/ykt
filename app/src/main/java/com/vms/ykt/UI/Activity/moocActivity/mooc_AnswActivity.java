package com.vms.ykt.UI.Activity.moocActivity;

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
import com.vms.ykt.yktDao.mooc.moocUserDao;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.mooc.homeWorkAnswInfo;
import com.vms.ykt.yktStuMobile.mooc.moocCellInfo;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.yktStuMobile.mooc.moocMianM;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.mooc.WorkExamList;

import java.util.List;

public class mooc_AnswActivity extends AppCompatActivity {
    private static final String TAG = mooc_AnswActivity.class.getSimpleName();
    private Context mContext;
    private zjyUser mZjyUser;
    private moocCourseInfo mCourseIfno;
    private WorkExamList mWorkExamList;

    private View root = null;
    private TextView mButton2,zjy_exam_answ;
    private Button mButton;
    private String flag;
    private String Workflag;
    private ProgressBar mProgressBar;
    private Thread mThread;
    private boolean isDoWork=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mooc_work_answactivity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = mooc_AnswActivity.this;
        this.mWorkExamList = moocUserDao.sWorkExamList;
        this.mCourseIfno = moocUserDao.sMoocCourseInfo;
        this.mZjyUser = zjyUserDao.sZjyUser;
        this.flag = (String) i.getSerializableExtra("flag");
        this.Workflag = (String) i.getSerializableExtra("Workflag");

        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
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
            if (isDoWork){
                isDoWork=false;
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
                isDoWork=true;
            }
        }).start();
    }

    private String doWork(){
     //if (flag.equals("1"))return doWorkW();
     return doWorkM();
    }

    private String doWorkW(){
        String answ="";
        return answ+"";
    }

    private String doWorkM() {

        List<homeWorkAnswInfo> StuAnsw;
        moocCellInfo varCellInfo =new moocCellInfo();
        varCellInfo.setResId(mWorkExamList.getId());
        String workExamType = Workflag;
        StuAnsw = moocMianM.getStuAnsw(mZjyUser, mCourseIfno, varCellInfo.getResId());
        if (!StuAnsw.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {
            return moocMianM.parseAnsw(StuAnsw);

        }

        StuAnsw = moocMianM.getStuAnsw2(mZjyUser, mCourseIfno, varCellInfo,"", workExamType);
        if (!StuAnsw.isEmpty()  && !StuAnsw.get(0).getAnswer().isEmpty()) {

            return moocMianM.parseAnsw(StuAnsw);
        }

        return "";
    }

}


