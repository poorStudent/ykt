package com.vms.ykt.UI.Activity.zjyActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SwitchCompat;

import com.vms.ykt.R;
import com.vms.ykt.Util.AppStatus;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class zjy_keHouActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();


    private Button mKeHou_sz, mKeHou_ks, mKeHou_zt;
    private EditText mKeHou_et_kcsj;
    private SwitchCompat mKeHou_bt_ktpj, mKeHou_bt_zwzj;
    private TextView mKeHou_rz, mKeHou_jd;

    public boolean ismKeHou_zt;

    private static long mKeHou_pjjg = 1000;

    private static final ArrayList<String> mKeHou_zwzj = new ArrayList<>();
    private static final ArrayList<String> mKeHou_lspj = new ArrayList<>();
    private  zjyUser mZjyUser;
    private  zjyCourseIfno mCourseIfno;

    private String date="";
    private Context mContext;
    int curCt = 0;
    int pageCt = 0;
    StringBuffer stringBuffer;
    private zjyMainW mZjyMainW;
    private Thread mThread;
    private boolean isDowork=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjy_kehou_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = zjy_keHouActivity.this;
        this.mCourseIfno = (zjyCourseIfno) i.getSerializableExtra("Course");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");
        this.mZjyMainW = (zjyMainW) i.getSerializableExtra("mZjyMainW");

        mKeHou_lspj.add("课件清晰明了");
        mKeHou_lspj.add("无");
        mKeHou_lspj.add("讲得非常棒");
        mKeHou_lspj.add("好");

        mKeHou_zwzj.add("好");
        mKeHou_zwzj.add("学到了很多新东西");
        mKeHou_zwzj.add("无");
        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
        Log.d(TAG, "initData: " + mZjyUser.getUserId());
    }

    private void initView() {
        mKeHou_sz = findViewById(R.id.zjy_kehou_sz);
        mKeHou_bt_ktpj = findViewById(R.id.zjy_kehou_ktpj);
        mKeHou_bt_zwzj = findViewById(R.id.zjy_kehou_zwzj);
        mKeHou_et_kcsj = findViewById(R.id.zjy_kehou_kccjsj);
        mKeHou_ks = findViewById(R.id.zjy_kehou_ks);
        mKeHou_zt = findViewById(R.id.zjy_kehou_tz);
        mKeHou_jd = findViewById(R.id.zjy_kehou_jd);
        mKeHou_rz = findViewById(R.id.zjy_kehou_rz);
        mKeHou_bt_ktpj.setChecked(true);
        mKeHou_bt_zwzj.setChecked(true);
        mKeHou_rz.setMovementMethod(ScrollingMovementMethod.getInstance());
        mKeHou_jd.setText("进度: 0/0");
        Log.d(TAG, "initView: " + mKeHou_pjjg);
    }

    private void initListener() {

        mKeHou_sz.setOnClickListener((View view)->{
                showSetDialog();
            });

        mKeHou_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ismKeHou_zt = false;
                pageCt=0;
                curCt=0;
                stringBuffer = new StringBuffer();
                date =mKeHou_et_kcsj.getText().toString().trim();
                mKeHou_rz.setText("获取课堂中请等待......");
                Tool.toast(zjy_keHouActivity.this, "开始获取");
                new Thread(()->{
                    if (isDowork) {
                        isDowork=false;
                        doWork(mZjyUser, mCourseIfno);
                        isDowork=true;
                    }
                }).start();

            }
        });

        mKeHou_zt.setOnClickListener((View v)-> {
                ismKeHou_zt = true;
        });


    }

    public void khpj(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno, zjyTeachInfo varZjyAllTeachInfo) {

        curCt++;

        String kjmc = "\n  -" + varZjyAllTeachInfo.getTitle() + "* " + varZjyAllTeachInfo.getDateCreated() + "* ";
        stringBuffer.append(kjmc);
        mHandler.sendEmptyMessage(100);


        runOnUiThread(()->{
                mKeHou_jd.setText("进度: " + curCt + "/" + pageCt);
        });

        if(!date.isEmpty()){
            String time=varZjyAllTeachInfo.getDateCreated();
            if (Tool.parseDataTime(time)<= Tool.parseDataTime(date)){
                stringBuffer.append("\n跳过");
                mHandler.sendEmptyMessage(100);
                Log.d(TAG, "khpj: "+time);
                return;
            }
        }
        if (mKeHou_bt_ktpj.isChecked()) {
            Tool.waitTime(mKeHou_pjjg);
            String resp = zjyApi.getAddEvaluationStu(zjyUser, zjyAllCourseIfno, varZjyAllTeachInfo, Tool.getRandomStr(mKeHou_lspj));
           // resp=mZjyMainW.SelfratingSave(zjyAllCourseIfno,varZjyAllTeachInfo, Tool.getRandomStr(mKeHou_lspj));
            stringBuffer.append("\n" + Tool.getCurrentData() + " 课堂评价->" + resp);//老师评价
            mHandler.sendEmptyMessage(100);
        }

        if (mKeHou_bt_zwzj.isChecked()) {
            Tool.waitTime(mKeHou_pjjg);
            String resp = zjyApi.getSaveSelfEvaluation(zjyUser, zjyAllCourseIfno, varZjyAllTeachInfo, Tool.getRandomStr(mKeHou_zwzj));
            //resp=mZjyMainW.EvaluationSave(zjyAllCourseIfno,varZjyAllTeachInfo, Tool.getRandomStr(mKeHou_lspj));
            stringBuffer.append("\n" + Tool.getCurrentData() + " 自我总结->" + resp);//自我总结
            mHandler.sendEmptyMessage(100);
        }


    }

    private void doWork(zjyUser zjyUser, zjyCourseIfno mCourseIfno) {
        AppStatus.AppStatusInit();
        if (AppStatus.getYunhei()!=null) {
            if (AppStatus.getYunhei().contains(mZjyUser.getDisplayName()) || AppStatus.getYunhei().contains(mZjyUser.getUserName()));
                //return ;
        }
        StringBuffer vStringBuffer = new StringBuffer();
        List<zjyTeachInfo> vZjyTeachInfos = new ArrayList<>();
        List<zjyTeachInfo> vList=zjyMain.getAllFaceTeach(zjyUser, mCourseIfno);
        if (vList.size()==0){
            //web
            vList= mZjyMainW.getAllFaceTeachInfoByCourse(mCourseIfno);
        }
        for (zjyTeachInfo varZjyAllTeachInfo : vList) {
           /** if (!ismKeHou_zt) {
                khpj(zjyUser, mCourseIfno, varZjyAllTeachInfo );
            } else {
                return;
            }**/
            vZjyTeachInfos.add(varZjyAllTeachInfo);
            String kjmc = "\n" + " -" + varZjyAllTeachInfo.getTitle() + "* " + varZjyAllTeachInfo.getDateCreated() + "* ";
            vStringBuffer.append(kjmc);
            mKeHou_rz.post(()-> {
                mKeHou_rz.setText(vStringBuffer.toString());
                Tool.tvShowBottom(mKeHou_rz);
            });

        }
        pageCt=vZjyTeachInfos.size();
       for (zjyTeachInfo vZjyTeachInfo : vZjyTeachInfos) {
            if (!ismKeHou_zt) {
                khpj(zjyUser, mCourseIfno, vZjyTeachInfo);
            } else {
                stringBuffer.append("\n" + Tool.getCurrentData() + " ->已停止");//自我总结
                mHandler.sendEmptyMessage(100);
                return;
            }
        }


    }

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.zjy_kehou_setdialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        EditText but_pjjg = dialogView.findViewById(R.id.zjy_kehou_jg);
        EditText but_lspj = dialogView.findViewById(R.id.zjy_kehou_ktpjnr);
        EditText but_zwzj = dialogView.findViewById(R.id.zjy_kehou_zwzjnr);
        Button but_qd = dialogView.findViewById(R.id.zjy_kehou_qd);
        //取消点击外部消失弹窗
        setDeBugDialog.setCancelable(true);
        //创建AlertDiaLog
        setDeBugDialog.create();
        //AlertDiaLog显示
        final AlertDialog customAlert = setDeBugDialog.show();
        //设置AlertDiaLog宽高属性
        WindowManager.LayoutParams params = Objects.requireNonNull(customAlert.getWindow()).getAttributes();
        params.width = 900;
        params.height = 850;
        customAlert.getWindow().setAttributes(params);
        // 移除dialog的decorview背景色
        // Objects.requireNonNull(customAlert.getWindow()).getDecorView().setBackground(null);
        //设置自定义界面的点击事件逻辑

        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mKeHou_pjjg = (but_pjjg.getText().toString().trim().isEmpty() ? 1000 : Integer.valueOf(but_pjjg.getText().toString()));

                Log.d(TAG, "onClick: " + mKeHou_pjjg);
                String[] pj = but_lspj.getText().toString().split(" ");
                for (String p : pj) {
                    if (!p.trim().isEmpty())
                        mKeHou_zwzj.add(p);
                    Log.d(TAG, "onClick: " + p);
                }
                String[] qtpj = but_zwzj.getText().toString().split(" ");
                for (String p : qtpj) {
                    if (!p.trim().isEmpty())
                        mKeHou_lspj.add(p);
                }
                customAlert.dismiss();
                customAlert.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                mKeHou_rz.setText(stringBuffer.toString());
                Tool.tvShowBottom(mKeHou_rz);
            }
        }
    };


}
