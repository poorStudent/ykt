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

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class cqooc_moreUserSkActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();


    private Button cqooc_shuke_sz, cqooc_shuke_ks, cqooc_shuke_zt;
    private SwitchCompat cqooc_shuke_zdtl, cqooc_shuke_zdcy, cqooc_shuke_tgys, cqooc_shuke_sk;
    private TextView cqooc_shuke_rz;

    public boolean isShuke_zt;

    private int cqooc_shuke_jg = 30, cqooc_shuke_csjg = 5, cqooc_shuke_cscs = 2;

    private final ArrayList<String> mCqooc_tlnr = new ArrayList<>();


    private Context mContext;

    StringBuilder stringBuffer;

    private Thread mThread;
    private ExecutorService MExecutorService = Executors.newFixedThreadPool(3);

    private final String dp = Tool.getSD().getAbsolutePath() + File.separator + "vmsykt" + File.separator;

    ArrayList<String> cqoocUsers;
    private boolean isDowork = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cqooc_shuke_more_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = cqooc_moreUserSkActivity.this;
    }

    private void initView() {
        cqooc_shuke_sz = findViewById(R.id.cqooc_shuke_sz);
        cqooc_shuke_ks = findViewById(R.id.cqooc_shuke_ks);
        cqooc_shuke_zt = findViewById(R.id.cqooc_shuke_zt);
        cqooc_shuke_rz = findViewById(R.id.cqooc_shuke_rz);
        cqooc_shuke_zdtl = findViewById(R.id.cqooc_shuke_zdtl);
        cqooc_shuke_zdcy = findViewById(R.id.cqooc_shuke_zdcy);
        cqooc_shuke_tgys = findViewById(R.id.cqooc_shuke_tgys);
        cqooc_shuke_sk = findViewById(R.id.cqooc_shuke_sk);
        cqooc_shuke_sk.setChecked(true);
        cqooc_shuke_tgys.setChecked(true);
        cqooc_shuke_zdtl.setChecked(true);
        cqooc_shuke_zdcy.setEnabled(false);
        cqooc_shuke_rz.setMovementMethod(ScrollingMovementMethod.getInstance());
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

                stringBuffer.delete(0, stringBuffer.length());
                cqooc_shuke_rz.setText("获取中请等待......");
                Log.d(TAG, "onClick: " + dp);
                Tool.toast(cqooc_moreUserSkActivity.this, "开始获取");
                if (isDowork) {
                    isDowork = false;
                    Work();
                    isDowork = true;
                }

            }
        });

        cqooc_shuke_zt.setOnClickListener((View v) -> {
            isShuke_zt = true;
            //mThread.stop();
        });

    }

    private void Work() {
        if (!cqooc_shuke_sk.isChecked()) return;
        cqoocUsers = Tool.readLineFile(dp, "cqoocUser.txt");
        for (String s : cqoocUsers) {
            final ArrayList<String> course = new ArrayList<>();
            final String[] info = s.split("\\|");
            Log.d(TAG, "Work: " + info[0]);
            Log.d(TAG, "Work: " + info[1]);
            if (info.length <= 2) {
                stringBuffer.append("\n" + info[0] + " 待刷课 0");
                mHandler.sendEmptyMessage(100);
                return;
            }
            for (int i = 2; i < info.length; i++) {
                course.add(info[i]);
            }
            Log.d(TAG, "Work: " + course.get(0));

            Runnable vRunnable = new Runnable() {
                @Override
                public void run() {
                    waitTime(Tool.getRandomInt(1, 3));
                    goWork(info[0], info[1], course);
                }

            };
            MExecutorService.execute(vRunnable);
        }
    }

    private synchronized void  goWork(String username, String password, ArrayList<String> course) {

        final cqoocLogin vCqoocLogin = new cqoocLogin();
        
        final userInfo mUserInfo;
        String ck = vCqoocLogin.LoignIng(username, password);


        cqoocHttp.restCookie("player=1; xsid=" + ck);
        Log.d(TAG, "goWork: " + ck);
        if (ck != null && !ck.isEmpty()) {
            mUserInfo = cqoocMain.getUsreInfo(ck);
        } else {
            mUserInfo = null;
        }
        if (mUserInfo == null || mUserInfo.getUsername() == null) {
            stringBuffer.append("\n" + Tool.getCurrentData() + " " + username + " ->登录失败");
            mHandler.sendEmptyMessage(100);
            return;
        }
        stringBuffer.append("\n" + Tool.getCurrentData() + " " + username + " ->登录成功");
        mHandler.sendEmptyMessage(100);
        mUserInfo.setCookie("player=1; xsid=" + ck);
        doWork(mUserInfo , course);
    }

    private void doWork(userInfo userInfo  ,ArrayList<String> course) {

        AppStatus.AppStatusInit();
        if (AppStatus.getCqooc() == null || !AppStatus.getCqooc().equals("cqoocok")) return;

        for (cqoocCourseInfo courseIfno : cqoocMain.getAllCourse(userInfo)) {

            if (!course.contains(courseIfno.getClassTitle())) {
                stringBuffer.append("\n" + " " + userInfo.getName() + " 跳过->" + courseIfno.getTitle());
                mHandler.sendEmptyMessage(100);
                continue;
            }
            int curCt = 0;
            int pageCt = 0;
            stringBuffer.append("\n" + " " + userInfo.getName() + " 开始->" + courseIfno.getTitle());
            mHandler.sendEmptyMessage(100);
            List<cellLessonsInfo> varList = new ArrayList<>();
            ArrayList<String> varFinishaLessons = new ArrayList<>();
            if (cqooc_shuke_sk.isChecked()) {
                varList = cqoocMain.getAlllessons(courseIfno);
                varFinishaLessons = cqoocMain.getFinishaLessons(userInfo, courseIfno);
            }

            for (cellLessonsInfo varCellLessonsInfo : varList) {
                cellResourceInfo varResourceInfo = varCellLessonsInfo.getCellResourceInfo();
                Log.d(TAG, "doWork: --" + varCellLessonsInfo.getTitle() + " *" + varResourceInfo.getResMediaType() + " *" + varCellLessonsInfo.getForumId() + " *" + varCellLessonsInfo.getTestId());

                if (isShuke_zt) {
                    stringBuffer.append("\n" + Tool.getCurrentData() + " ->已停止");//自我总结
                    mHandler.sendEmptyMessage(100);
                    Log.d(TAG, "doWork: stop");

                    return;
                }
                if (!cqooc_shuke_sk.isChecked()) break;
                curCt++;
                String jd = curCt + "/" + pageCt;
                stringBuffer.append("\n" + userInfo.getName() + " " + courseIfno.getTitle() + " 进度->" + jd);
                mHandler.sendEmptyMessage(100);
                shuake(userInfo, courseIfno, varCellLessonsInfo, varFinishaLessons );
                if (stringBuffer.length() == 1000) stringBuffer.delete(0, stringBuffer.length());
            }
            //作业考试
        }
    }

    public void shuake(userInfo UserInfo, cqoocCourseInfo varCqoocCourseInfo, cellLessonsInfo varCellLessonsInfo, ArrayList<String> varFinishaLessons  ) {
        cellResourceInfo varResourceInfo = varCellLessonsInfo.getCellResourceInfo();
        stringBuffer.append("\n--" + varCellLessonsInfo.getTitle() + " *" + varResourceInfo.getResMediaType());
        mHandler.sendEmptyMessage(100);

        if (!cqooc_shuke_sk.isChecked()) {
            Log.d(TAG, "doWork: stop");

            return;
        }
        if (cqooc_shuke_tgys.isChecked()) {
            if (varFinishaLessons.contains(varCellLessonsInfo.getId())) {
                stringBuffer.append("\n 100% 跳过 " + UserInfo.getName() + "->" + varCellLessonsInfo.getTitle());
                mHandler.sendEmptyMessage(100);
                return;
            }
        }
        stringBuffer.append("\n" + UserInfo.getName() + " StudyTime->" + cqApi.getStudyTime(varCqoocCourseInfo.getCourseId()));
        mHandler.sendEmptyMessage(100);

        cqApi.getLearnLogs2(varCqoocCourseInfo.getCourseId(), varCellLessonsInfo.getId(), UserInfo.getUsername());

        String ResMediaType = varCellLessonsInfo.getCellResourceInfo().getResMediaType();
        if (ResMediaType != null && !ResMediaType.equals("null") && !ResMediaType.isEmpty()) {
            String resID = varCellLessonsInfo.getResId();
            String resp = cqApi.getRes(varCqoocCourseInfo.getCourseId(), resID);
            if (ResMediaType.contains("video")) {
                //String durationTime = Tool.parseJsonS(resp, "durationTime");
                //System.out.println(durationTime);
                //System.out.println(cqApi.getCurTimeInit(varCqoocCourseInfo.getCourseId(), varCellLessonsInfo.getId()));
                //视频
            } else {

                //其他类型
            }

        } else {
            String testID = varCellLessonsInfo.getTestId();
            if (testID != null && !testID.equals("null")) {
                //测验
                doTest();
                // return;
            }
            String fourmID = varCellLessonsInfo.getForumId();
            if (fourmID != null && !fourmID.equals("null")) {
                //讨论
                String ct = cqoocMain.getOtherFourmCt(varCqoocCourseInfo.getCourseId(), fourmID);
                if (ct.isEmpty()) {
                    ct = "无";
                }
                if (mCqooc_tlnr.size() != 0) {
                    ct = Tool.getRandomStr(mCqooc_tlnr);
                    Log.d(TAG, "shuake: tlnr");
                }
                Log.d(TAG, "shuake: " + ct);
                stringBuffer.append("\n" + UserInfo.getName() + " 讨论->" + cqApi.getAddForum(varCellLessonsInfo, varCqoocCourseInfo, "187.135.1.2", ct));
                stringBuffer.append("\n" + UserInfo.getName() + " 讨论log->" + cqApi.getForumLog(varCqoocCourseInfo.getCourseId(), UserInfo.getUsername()));
                mHandler.sendEmptyMessage(100);
            }


        }
        waitTime(cqooc_shuke_jg);
        stringBuffer.append("\n" + UserInfo.getName() + " LearnLog->" + cqApi.getLearnLogs3(varCqoocCourseInfo.getCourseId(), varCellLessonsInfo.getId(), UserInfo.getUsername()));
        mHandler.sendEmptyMessage(100);

        String resp = cqApi.getAddLearnLog(UserInfo, varCqoocCourseInfo, varCellLessonsInfo);
        stringBuffer.append("\n" + UserInfo.getName() + "->" + varCellLessonsInfo.getTitle() + " 刷课->" + resp);
        mHandler.sendEmptyMessage(100);
        Log.d(TAG, "shuake: " + resp);

        for (int i = 1; i <= cqooc_shuke_cscs; i++) {
            if (resp == null || (!resp.contains("No error") && !resp.contains("添加"))) {
                stringBuffer.append("\n 失败 正在尝试重试第 " + i + "次");
                waitTime(cqooc_shuke_csjg);
                resp = cqApi.getAddLearnLog(UserInfo, varCqoocCourseInfo, varCellLessonsInfo);
                stringBuffer.append("\n" + UserInfo.getName() + "->" + varCellLessonsInfo.getTitle() + " 刷课->" + resp);
            } else {
                break;
            }
        }
    }

    private void doTest() {
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

        AlertDialog.Builder setDeBugDialog =new AlertDialog.Builder(mContext);

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
                cqooc_shuke_jg = (but_pjjg.getText().toString().trim().isEmpty() ? 30 : Integer.parseInt(but_pjjg.getText().toString()));
                cqooc_shuke_csjg = (but_csjg.getText().toString().trim().isEmpty() ? 5 : Integer.parseInt(but_csjg.getText().toString()));
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
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
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
        }
    };

}
