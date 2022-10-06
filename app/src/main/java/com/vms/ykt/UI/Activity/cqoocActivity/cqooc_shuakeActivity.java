package com.vms.ykt.UI.Activity.cqoocActivity;

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
import com.vms.ykt.Util.Tool;

import com.vms.ykt.yktStuWeb.Cqooc.cellLessonsInfo;
import com.vms.ykt.yktStuWeb.Cqooc.cellResourceInfo;
import com.vms.ykt.yktStuWeb.Cqooc.cqApi;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocCourseInfo;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocHttp;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocLogin;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocMain;
import com.vms.ykt.yktStuWeb.Cqooc.userInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class cqooc_shuakeActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private cqoocMain mCqoocMain;
    private cqApi mCqApi;

    private Button cqooc_shuke_sz, cqooc_shuke_ks, cqooc_shuke_zt;
    private SwitchCompat cqooc_shuke_zdtl, cqooc_shuke_zdcy,cqooc_shuke_zdzy,cqooc_shuke_zdks,cqooc_shuke_sk, cqooc_shuke_tgys;
    private TextView cqooc_shuke_rz, cqooc_shuke_jd;

    public boolean isShuke_zt;

    private int cqooc_shuke_jg = 30, cqooc_shuke_csjg = 10, cqooc_shuke_cscs = 2;

    private final ArrayList<String> mCqooc_tlnr = new ArrayList<>();
    private userInfo mUserInfo;
    private cqoocCourseInfo mCourseIfno;

    private Context mContext;
    int curCt = 0;
    int pageCt = 0;
    StringBuilder stringBuffer;
    private String ip;
    private Thread mThread;
    private String xsid = "";
    private boolean isDowork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cqooc_shuke_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {

        Intent i = getIntent();
        this.mContext = cqooc_shuakeActivity.this;
        this.mCourseIfno = (cqoocCourseInfo) i.getSerializableExtra("Course");
        this.mUserInfo = (userInfo) i.getSerializableExtra("cqUser");
        this.mCqoocMain = (cqoocMain) i.getSerializableExtra("mCqoocMain");
        this.mCqApi = (cqApi) i.getSerializableExtra("mCqApi");
        ip = Tool.getIPAddress(mContext);
        Log.d(TAG, "initData: " + mCourseIfno.getTitle());
        Log.d(TAG, "initData: " + mCourseIfno.getUsername());
    }

    private void initView() {
        cqooc_shuke_sz = findViewById(R.id.cqooc_shuke_sz);
        cqooc_shuke_ks = findViewById(R.id.cqooc_shuke_ks);
        cqooc_shuke_zt = findViewById(R.id.cqooc_shuke_zt);
        cqooc_shuke_jd = findViewById(R.id.cqooc_shuke_jd);
        cqooc_shuke_rz = findViewById(R.id.cqooc_shuke_rz);
        cqooc_shuke_zdtl = findViewById(R.id.cqooc_shuke_zdtl);
        cqooc_shuke_zdcy = findViewById(R.id.cqooc_shuke_zdcy);
        cqooc_shuke_zdzy= findViewById(R.id.cqooc_shuke_zdzy);
        cqooc_shuke_zdks= findViewById(R.id.cqooc_shuke_zdks);
        cqooc_shuke_tgys = findViewById(R.id.cqooc_shuke_tgys);
        cqooc_shuke_sk = findViewById(R.id.cqooc_shuke_sk);
        cqooc_shuke_sk.setChecked(true);
        cqooc_shuke_tgys.setChecked(true);
        cqooc_shuke_zdtl.setChecked(true);
        //cqooc_shuke_zdcy.setEnabled(false);
        cqooc_shuke_rz.setMovementMethod(ScrollingMovementMethod.getInstance());
        cqooc_shuke_jd.setText("进度: 0/0");
        Log.d(TAG, "initView: " + cqooc_shuke_jg);
    }

    private void initListener() {

        cqooc_shuke_sz.setOnClickListener((View view) -> {
            showSetDialog();
        });

        cqooc_shuke_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShuke_zt = false;
                pageCt = 0;
                curCt = 0;
                if(stringBuffer==null)stringBuffer = new StringBuilder();
                stringBuffer.delete(0,stringBuffer.length());
                cqooc_shuke_rz.setText("获取中请等待......");
                Tool.toast(cqooc_shuakeActivity.this, "开始获取");
                mThread = new Thread(() -> {
                    if (isDowork) {
                        isDowork = false;
                        doWork();
                        isDowork = true;
                    }
                });
                mThread.start();
            }
        });

        cqooc_shuke_zt.setOnClickListener((View v) -> {
            isShuke_zt = true;
            // mThread.stop();
        });

    }

    private void doWork() {
        AppStatus.AppStatusInit();
        //  if (AppStatus.getCqooc()==null||!AppStatus.getCqooc().equals("cqoocok"))return;
        List<cellLessonsInfo> varList =new ArrayList<>();
        ArrayList<String> varFinishaLessons=new ArrayList<>();
        if (cqooc_shuke_sk.isChecked()) {
            varList = mCqoocMain.getAlllessons(mCourseIfno);
            varFinishaLessons = mCqoocMain.getFinishaLessons(mUserInfo, mCourseIfno);
        }
        pageCt = varList.size();

        for (cellLessonsInfo varCellLessonsInfo : varList) {
            cellResourceInfo varResourceInfo = varCellLessonsInfo.getCellResourceInfo();
            Log.d(TAG, "doWork: --" + varCellLessonsInfo.getTitle() + " *" + varResourceInfo.getResMediaType() + " *" + varCellLessonsInfo.getForumId() + " *" + varCellLessonsInfo.getTestId());
            if (!cqooc_shuke_sk.isChecked()) break;
            if (isShuke_zt) {
                Log.d(TAG, "doWork: stop");
                stringBuffer.append("\n" + Tool.getCurrentData() + " ->已停止");//自我总结
                mHandler.sendEmptyMessage(100);
                return;
            }

            if (mThread.isInterrupted())return;
            curCt++;
            cqooc_shuke_jd.post(() -> {
                cqooc_shuke_jd.setText(curCt + "/" + pageCt);

            });
            if (stringBuffer.length()==1000) stringBuffer.delete(0,stringBuffer.length());

            shuake(varCellLessonsInfo, varFinishaLessons);


        }

        //作业考试
        if (cqooc_shuke_zdzy.isChecked()){


        }
        if (cqooc_shuke_zdks.isChecked()){

        }

    }

    public void shuake(cellLessonsInfo varCellLessonsInfo, ArrayList<String> varFinishaLessons) {
        cellResourceInfo varResourceInfo = varCellLessonsInfo.getCellResourceInfo();
        stringBuffer.append("\n--" + varCellLessonsInfo.getTitle() + " *" + varResourceInfo.getResMediaType() + " *" + varCellLessonsInfo.getForumId() + " *" + varCellLessonsInfo.getTestId());
        mHandler.sendEmptyMessage(100);

        if (true){
            //课件评分
            String resp=mCqoocMain.getAddScore(mCourseIfno.getCourseId(), varCellLessonsInfo.getResId(), "5");
        }
        if (cqooc_shuke_sk.isChecked()) {
            if (cqooc_shuke_tgys.isChecked()) {
                if (varFinishaLessons.contains(varCellLessonsInfo.getId())) {
                    stringBuffer.append("\n 100% 跳过");
                    mHandler.sendEmptyMessage(100);
                    return;
                }
            }
            stringBuffer.append("\n" + Tool.getCurrentData() + " StudyTime->" + mCqApi.getStudyTime(mCourseIfno.getCourseId()).toString());
            mHandler.sendEmptyMessage(100);
            mCqApi.getLearnLogs2(mCourseIfno.getCourseId(), varCellLessonsInfo.getId(), mUserInfo.getUsername());

            String ResMediaType = varCellLessonsInfo.getCellResourceInfo().getResMediaType();
            if (ResMediaType != null && !ResMediaType.equals("null") && !ResMediaType.isEmpty()) {
                String resID = varCellLessonsInfo.getResId();
                String resp = mCqApi.getRes(mCourseIfno.getCourseId(), resID);

                if (ResMediaType.contains("video")) {
                    //String durationTime = Tool.parseJsonS(resp, "durationTime");
                    //System.out.println(durationTime);
                    //System.out.println(cqApi.getCurTimeInit(mCourseIfno.getCourseId(), varCellLessonsInfo.getId()));
                    //视频
                } else {

                    //其他类型
                }

            } else {

                String fourmID = varCellLessonsInfo.getForumId();
                if (fourmID != null && !fourmID.equals("null")) {
                    //讨论
                    String ct = mCqoocMain.getOtherFourmCt(mCourseIfno.getCourseId(), fourmID);
                    if (ct.isEmpty()) {
                        ct = "无";
                    }
                    if (mCqooc_tlnr.size() != 0) {
                        ct = Tool.getRandomStr(mCqooc_tlnr);
                        Log.d(TAG, "shuake: tlnr");
                    }
                    Log.d(TAG, "shuake: " + ct);
                    stringBuffer.append("\n" + Tool.getCurrentData() + " 讨论->" + mCqApi.getAddForum(varCellLessonsInfo, mCourseIfno, ip, ct));
                    stringBuffer.append("\n" + Tool.getCurrentData() + " 讨论log->" + mCqApi.getForumLog(mCourseIfno.getCourseId(), mUserInfo.getUsername()));
                    mHandler.sendEmptyMessage(100);
                }


            }
            waitTime(cqooc_shuke_jg);
            stringBuffer.append("\n" + Tool.getCurrentData() + " LearnLog->" + mCqApi.getLearnLogs3(mCourseIfno.getCourseId(), varCellLessonsInfo.getId(), mUserInfo.getUsername()));
            mHandler.sendEmptyMessage(100);

            String resp = mCqApi.getAddLearnLog(mUserInfo, mCourseIfno, varCellLessonsInfo);
            stringBuffer.append("\n" + Tool.getCurrentData() + " 刷课->" + resp);
            mHandler.sendEmptyMessage(100);
            Log.d(TAG, "shuake: " + resp);

            for (int i = 1; i <= cqooc_shuke_cscs; i++) {
                if (resp == null || (!resp.contains("No error") && !resp.contains("添加"))) {
                    stringBuffer.append("\n 失败 正在尝试重试第 " + i + "次");
                    mHandler.sendEmptyMessage(100);
                    waitTime(cqooc_shuke_csjg);
                    resp = mCqApi.getAddLearnLog(mUserInfo, mCourseIfno, varCellLessonsInfo);
                    stringBuffer.append("\n" + Tool.getCurrentData() + " 刷课->" + resp);
                    mHandler.sendEmptyMessage(100);
                } else {
                    break;
                }
            }
        } else {
            stringBuffer.append("\n 跳过");
            mHandler.sendEmptyMessage(100);
        }
        if (cqooc_shuke_zdcy.isChecked()) {
            String testID = varCellLessonsInfo.getTestId();
            if (testID != null && !testID.equals("null")) {
                //测验

                cellLessonsInfo vInfo = mCqoocMain.parseLessonsTests(varCellLessonsInfo);
                stringBuffer.append("\n" + "自动测验->" + vInfo.getTitle() + " *" + vInfo.getTestId());
                mHandler.sendEmptyMessage(100);

                if (xsid == null || xsid.isEmpty()) {
                    doTest2(vInfo);
                    return;
                }
                ;
                waitTime(1);
                doTest(vInfo);
                //return;
            }
        }
    }

    private void doTest2(cellLessonsInfo info) {


    }

    private void doTest(cellLessonsInfo varCellLessonsInfo) {

        final cqoocHttp mCqoocHttps = new cqoocHttp();
        final cqoocMain mCqoocMains = new cqoocMain();
        final cqApi mCqApis = new cqApi();
        mCqoocHttps.setUserCookie("player=1; xsid=" + xsid);
        mCqApis.setCqoocHttp(mCqoocHttps);
        mCqoocMains.setCqApi(mCqApis);
        String answ = mCqApis.getTestResult(mCourseIfno, varCellLessonsInfo);
        //String openTest=mCqApi.getOpenPaper(mCourseIfno,varCellLessonsInfo);
        answ = mCqoocMain.parseTestAnsw(answ);
        Log.d(TAG, "doTest: " + answ);
        if (answ == null) {
            stringBuffer.append("\n" + "答案获取异常");
            mHandler.sendEmptyMessage(100);
            return;
        }
        Tool.waitTime(500);
        String resp = mCqApi.getTestAdd(mUserInfo, answ, mCourseIfno, varCellLessonsInfo);
        stringBuffer.append("\n" + resp);
        mHandler.sendEmptyMessage(100);
    }

    public void waitTime(int times) {
        stringBuffer.append("\n " + times + "s等待时间.... \n");
        for (int i = 1; i <= times; i++) {
            stringBuffer.append(i + "s ");
            mHandler.sendEmptyMessage(100);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ParmsE) {
                ParmsE.printStackTrace();
            }
        }
    }

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.cqooc_shuke_setdialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        EditText but_pjjg = dialogView.findViewById(R.id.cqooc_shuke_jg);
        EditText but_lspj = dialogView.findViewById(R.id.cqooc_shuke_tlnr);
        EditText but_csjg = dialogView.findViewById(R.id.cqooc_shuke_csjg);
        EditText but_cscs = dialogView.findViewById(R.id.cqooc_shuke_cscs);
        Button but_qd = dialogView.findViewById(R.id.cqooc_shuke_qd);


        EditText but_cy_zh = dialogView.findViewById(R.id.cqooc_cy_zh);
        EditText but_cy_mm = dialogView.findViewById(R.id.cqooc_cy_mm);
        Button but_cy_dl = dialogView.findViewById(R.id.cqooc_cy_dl);
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

        /*

         * */
        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cqooc_shuke_jg = (but_pjjg.getText().toString().trim().isEmpty() ? 30 : Integer.parseInt(but_pjjg.getText().toString()));
                cqooc_shuke_csjg = (but_csjg.getText().toString().trim().isEmpty() ? 10 : Integer.parseInt(but_csjg.getText().toString()));
                cqooc_shuke_cscs = (but_cscs.getText().toString().trim().isEmpty() ? 2 : Integer.parseInt(but_cscs.getText().toString()));

                Log.d(TAG, "onClick: " + cqooc_shuke_jg);
                String[] pj = but_lspj.getText().toString().split(" ");
                for (String p : pj) {
                    if (!p.trim().isEmpty())
                        mCqooc_tlnr.add(p);
                    Log.d(TAG, "onClick: " + p);
                }
                customAlert.cancel();
            }
        });

        but_cy_dl.setOnClickListener((view) -> {
            String username = but_cy_zh.getText().toString();
            String password = but_cy_mm.getText().toString();
            if (!username.isEmpty() && !password.isEmpty()) {
                final cqoocLogin vCqoocLogin = new cqoocLogin();
                new Thread(() -> {
                    xsid = vCqoocLogin.LoignIng(username, password);
                    runOnUiThread(() -> {
                        Tool.toast(mContext, xsid);
                        if (xsid == null || xsid.isEmpty()) {
                            Tool.toast(mContext, "登录失败");
                        } else {
                            Tool.toast(mContext, "登录成功");
                            mUserInfo.setOtherXsid("player=1; xsid=" + xsid);
                        }
                    });
                }).start();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        mHandler.sendEmptyMessage(1);
        super.onDestroy();
    }

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                Tool.tvShowBottom(cqooc_shuke_rz);
                cqooc_shuke_rz.setText(stringBuffer.toString());
                Tool.tvShowBottom(cqooc_shuke_rz);
            }
            if (msg.what==1 && mThread!=null){
                mThread.interrupt();
            }
        }
    };

}
