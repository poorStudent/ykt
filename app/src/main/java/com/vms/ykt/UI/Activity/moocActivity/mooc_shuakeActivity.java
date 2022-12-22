package com.vms.ykt.UI.Activity.moocActivity;

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

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.mooc.moocUserDao;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.mooc.homeWorkAnswInfo;
import com.vms.ykt.yktStuMobile.mooc.moocApi;
import com.vms.ykt.yktStuMobile.mooc.moocCellInfo;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.yktStuMobile.mooc.moocMianM;
import com.vms.ykt.yktStuMobile.mooc.moocModInfo;
import com.vms.ykt.yktStuMobile.mooc.moocTopicInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.mooc.WorkExamList;
import com.vms.ykt.yktStuWeb.mooc.moocApiW;
import com.vms.ykt.yktStuWeb.mooc.moocHttpW;
import com.vms.ykt.yktStuWeb.mooc.moocMianW;
import com.vms.ykt.yktUtil.zjy.zjyMobileLogin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class mooc_shuakeActivity extends AppCompatActivity {

    private final String TAG = mooc_shuakeActivity.class.getSimpleName();
    private moocCourseInfo mCourseIfno;
    private zjyUser mZjyUser;
    private Context mContext;

    private SwitchCompat mooc_shuke_zdpj, mooc_shuke_zdwd, mooc_shuke_zdbj, mooc_shuke_zdfk,
            mooc_shuke_zdcy, mooc_shuke_zdzy, mooc_shuke_zdks, mooc_shuke_websk, mooc_shuke_tgys, mooc_shuke_sk;
    private Button mooc_shuke_sz, mooc_shuke_ks, mooc_shuke_zt;
    private TextView mooc_shuake_jd, mooc_shuke_skrz;

    private boolean isShuake_zt = false;
    private long mShuake_spkjys = 1000, mShuake_webspkjys = 1000, mShuake_fspkjys = 800,
            mShuake_pjys = 500, mShuake_kjxxsc = 0, mShuake_zyzdjg = 500, mShuake_zyzdsj = 1200;

    private ArrayList<String> mShuake_kjpj;
    private ArrayList<String> mShuake_qtkjpj;

    int curCt = 0;
    int pageCt = 0;
    StringBuilder stringBuffer;


    private Thread mThread;
    private boolean isDowork = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mooc_shuke_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }


    private void initData() {
        Intent i = getIntent();
        this.mCourseIfno = moocUserDao.sMoocCourseInfo;
        this.mZjyUser = zjyUserDao.sZjyUser;
        mContext = mooc_shuakeActivity.this;
        
        mShuake_kjpj = new ArrayList<>();
        mShuake_qtkjpj = new ArrayList<>();

        mShuake_kjpj.add("课件清晰明了");
        mShuake_kjpj.add("无");
        mShuake_kjpj.add("讲得非常棒");
        mShuake_kjpj.add("好");

        mShuake_qtkjpj.add("无");
        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
    }

    private void initView() {
        mooc_shuke_sz = findViewById(R.id.mooc_shuke_sz);
        mooc_shuke_ks = findViewById(R.id.mooc_ks);
        mooc_shuke_zt = findViewById(R.id.mooc_zt);
        mooc_shuke_zdpj = findViewById(R.id.mooc_zdpj);
        mooc_shuke_zdwd = findViewById(R.id.mooc_zdwd);
        mooc_shuke_zdbj = findViewById(R.id.mooc_zdbj);
        mooc_shuke_zdfk = findViewById(R.id.mooc_zdfk);
        mooc_shuke_zdcy = findViewById(R.id.mooc_zdcy);
        mooc_shuke_zdzy = findViewById(R.id.mooc_zdzy);
        mooc_shuke_zdks = findViewById(R.id.mooc_zdks);
        mooc_shuke_websk = findViewById(R.id.mooc_websk);
        mooc_shuke_tgys = findViewById(R.id.mooc_tgys);
        mooc_shuke_sk = findViewById(R.id.mooc_sk);
        mooc_shuake_jd = findViewById(R.id.mooc_shuake_jd);
        mooc_shuke_skrz = findViewById(R.id.mooc_skrz);

        mooc_shuke_tgys.setChecked(true);
        mooc_shuke_sk.setChecked(true);
        mooc_shuke_skrz.setMovementMethod(ScrollingMovementMethod.getInstance());
        mooc_shuake_jd.setText("进度: 0/0");
    }

    private void initListener() {
        mooc_shuke_sz.setOnClickListener((view) -> {
            showSetDialog();
        });

        mooc_shuke_ks.setOnClickListener((view) -> {
            isShuake_zt = false;
            pageCt = 0;
            curCt = 0;
            stringBuffer.delete(0, stringBuffer.length());
            Tool.toast(mContext, "开始获取课件");
            mooc_shuke_skrz.setText("获取课件中请等待......");
            String jd = "进度: " + curCt + "/" + pageCt;
            mooc_shuake_jd.setText(jd);
            mThread = new Thread(() -> {
                if (isDowork) {
                    isDowork = false;
                    doWork();
                    isDowork = true;
                }
            });
            mThread.start();

        });

        mooc_shuke_zt.setOnClickListener((view) -> {
            isShuake_zt = true;
        });

    }

    private void doWork() {
        ArrayList<moocCellInfo> mMoocCellList = new ArrayList<>();
        for (moocModInfo vMoocModInfo : moocMianM.getProcessList(mZjyUser, mCourseIfno)) {
            if (vMoocModInfo.getModuleType().contains("2")) {
                moocCellInfo vInfo = new moocCellInfo();
                vInfo.setResId(vMoocModInfo.getResId());
                vInfo.setId(vMoocModInfo.getId());
                vInfo.setModId(vMoocModInfo.getId());
                vInfo.setCellName(vMoocModInfo.getName());
                vInfo.setCellType("7");
                vInfo.setCategoryName("7");
                mMoocCellList.add(vInfo);
                continue;
            }
            for (moocTopicInfo vMoocTopicInfo : moocMianM.getTopicList(mZjyUser, mCourseIfno, vMoocModInfo)) {
                for (moocCellInfo vMoocCellInfo : moocMianM.getCellList(mZjyUser, mCourseIfno, vMoocTopicInfo)) {
                    if (vMoocCellInfo.getChildNodeList().size() == 0) {
                        vMoocCellInfo.setModId(vMoocModInfo.getId());
                        mMoocCellList.add(vMoocCellInfo);
                    } else {
                        for (moocCellInfo vMoocCellInfo1 : vMoocCellInfo.getChildNodeList()) {
                            vMoocCellInfo.setModId(vMoocModInfo.getId());
                            mMoocCellList.add(vMoocCellInfo1);
                        }
                    }
                }
            }
        }
        pageCt = mMoocCellList.size();
        for (moocCellInfo vMoocCellInfo : mMoocCellList) {
            if (isShuake_zt) {
                Log.d(TAG, "shuake: " + isShuake_zt);
                stringBuffer.append("\n" + Tool.getCurrentData() + " ->已停止");
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (mThread.isInterrupted()) {
                return;
            }

            curCt++;
            runOnUiThread(() -> {
                mooc_shuake_jd.setText("进度: " + curCt + "/" + pageCt);
            });
            shuke(vMoocCellInfo);
        }
    }

    private void shuke(moocCellInfo varCellInfo) {
        String kjmc = "\n----" + varCellInfo.getCellName() + " *" + varCellInfo.getCategoryName() + " *" + varCellInfo.getIsStudyFinish();
        stringBuffer.append(kjmc);
        Log.d(TAG, "shuke: " + kjmc);
        mHandler.sendEmptyMessage(100);
        if (mooc_shuke_sk.isChecked()) {
            if (mooc_shuke_tgys.isChecked() && !varCellInfo.getIsStudyFinish().contains("false")) {
                stringBuffer.append("\n 已刷跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }

            if (varCellInfo.getCategoryName().contains("视频")) {
                Tool.waitTime(mShuake_spkjys);
            } else {
                Tool.waitTime(mShuake_fspkjys);
            }

            String ret = "";
            if (mooc_shuke_websk.isChecked()) {
                ret = moocApiW.viewDirectory(mCourseIfno, varCellInfo);
                String videoLong = Tool.parseJsonS(Tool.parseJsonS(ret, "courseCell"), "VideoTimeLong");
                // stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->" + ret);
                ret = moocApiW.StuProcessCell(mCourseIfno, varCellInfo, videoLong + "");
            } else {
                ret = moocApi.getStuProcessCell(mZjyUser, mCourseIfno, varCellInfo);
            }

            stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->" + ret);
            mHandler.sendEmptyMessage(100);

            if (varCellInfo.getCategoryName().contains("讨论")) {
                String ct = moocApi.getContents(mZjyUser, mCourseIfno, varCellInfo);
                if (ct.isEmpty()) ct = Tool.getRandomStr(mShuake_qtkjpj);
                ret = moocApi.getaddTopicReply(mZjyUser, mCourseIfno, varCellInfo, ct);
                stringBuffer.append("\n" + Tool.getCurrentData() + "讨论->" + ret);
                mHandler.sendEmptyMessage(100);
            }

        } else {
            stringBuffer.append("\n刷课->跳过");
            mHandler.sendEmptyMessage(100);
        }
        if (varCellInfo.getCellType().equals("9")) {
            if (varCellInfo.getCategoryName().contains("调查")) {
                //

            }
        }

        if (varCellInfo.getCategoryName().contains("视频")) {


            String ret = "";
            if (mooc_shuke_zdpj.isChecked()) {
                //评价
                Tool.waitTime(mShuake_pjys);
                ret = moocApi.getsaveAllReply1_1(mZjyUser, mCourseIfno, varCellInfo, Tool.getRandomStr(mShuake_kjpj));
                stringBuffer.append("\n" + Tool.getCurrentData() + "评价->" + ret);
                mHandler.sendEmptyMessage(100);
            }
            if (mooc_shuke_zdwd.isChecked()) {
                //问答
                Tool.waitTime(mShuake_pjys);
                ret = moocApi.getsaveAllReply1_2(mZjyUser, mCourseIfno, varCellInfo, Tool.getRandomStr(mShuake_qtkjpj));
                stringBuffer.append("\n" + Tool.getCurrentData() + "问答->" + ret);
                mHandler.sendEmptyMessage(100);
            }
            if (mooc_shuke_zdbj.isChecked()) {
                //笔记
                Tool.waitTime(mShuake_pjys);
                ret = moocApi.getsaveAllReply1_3(mZjyUser, mCourseIfno, varCellInfo, Tool.getRandomStr(mShuake_qtkjpj));
                stringBuffer.append("\n" + Tool.getCurrentData() + "笔记->" + ret);
                mHandler.sendEmptyMessage(100);
            }
            if (mooc_shuke_zdfk.isChecked()) {
                //反馈
                Tool.waitTime(mShuake_pjys);
                ret = moocApi.getsaveAllReply1_4(mZjyUser, mCourseIfno, varCellInfo, Tool.getRandomStr(mShuake_qtkjpj));
                stringBuffer.append("\n" + Tool.getCurrentData() + "反馈->" + ret);
                mHandler.sendEmptyMessage(100);
            }
        }

        if (mooc_shuke_zdcy.isChecked()) {

            if (!varCellInfo.getIsStudyFinish().contains("false")) {
                stringBuffer.append("\n 已做跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }

            if (varCellInfo.getCellType().contains("5")) {
                doWorkexam(varCellInfo, "1");
            }

            if (varCellInfo.getCategoryName().contains("测验")) {

            }

        }

        if (mooc_shuke_zdzy.isChecked()) {
            if (!varCellInfo.getIsStudyFinish().contains("false")) {
                stringBuffer.append("\n 已做跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (varCellInfo.getCellType().contains("6")) {
                doWorkexam(varCellInfo, "0");
                return;
            }
            if (varCellInfo.getCategoryName().contains("作业")) {

            }
        }

        if (mooc_shuke_zdks.isChecked()) {
            if (!varCellInfo.getIsStudyFinish().contains("false")) {
                stringBuffer.append("\n 已做跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (varCellInfo.getCellType().contains("7")) {
                doWorkexam(varCellInfo, "2");
            }
            if (varCellInfo.getCategoryName().contains("考试")) {

            }
        }

    }

    private void GetDoTest() {
        if (mooc_shuke_zdcy.isChecked()) {

            List<WorkExamList> mWorkExamLists = moocMianW.getAllTestWork(mCourseIfno.getCourseOpenId());
            GetDoTwe(mWorkExamLists, "1");

        }
    }

    private void GetDoWork() {
        if (mooc_shuke_zdzy.isChecked()) {

            List<WorkExamList> mWorkExamLists = moocMianW.getAllhomeWork(mCourseIfno.getCourseOpenId());

            GetDoTwe(mWorkExamLists, "0");

        }
    }

    private void GetDoExam() {
        if (mooc_shuke_zdks.isChecked()) {
            List<WorkExamList> mWorkExamLists = moocMianW.getAllonlineExam(mCourseIfno.getCourseOpenId());
            GetDoTwe(mWorkExamLists, "2");
        }
    }

    private void GetDoTwe(List<WorkExamList> mWorkExamLists, String workExamType) {
        ArrayList<String> TypeName=new ArrayList<>();
        TypeName.add("作业");
        TypeName.add("测验");
        TypeName.add("考试");
        String tn = TypeName.get(Integer.parseInt(workExamType))+" :";
        stringBuffer.delete(0, stringBuffer.length());
        pageCt = mWorkExamLists.size();
        curCt = 0;
        for (WorkExamList vWorkExamList : mWorkExamLists) {

            if (isShuake_zt) {
                Log.d(TAG, "shuake: " + isShuake_zt);
                stringBuffer.append("\n");
                stringBuffer.append(tn);
                stringBuffer.append(" ->已停止");
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (mThread.isInterrupted()) {
                return;
            }
            stringBuffer.append("\n ");
            stringBuffer.append(tn);
            stringBuffer.append(" ");
            stringBuffer.append(vWorkExamList.getTitle());
            mHandler.sendEmptyMessage(100);
            DoTwe(vWorkExamList, workExamType);
            String jd= tn+curCt+"/"+pageCt;
            runOnUiThread(() -> mooc_shuake_jd.setText(jd));
        }
    }

    private void DoTwe(WorkExamList vWorkExamList, String workExamType) {
        if (vWorkExamList.getIsDoExam().contains("1")){
            stringBuffer.append("\n ");
            stringBuffer.append(vWorkExamList.getTitle());
            stringBuffer.append("->ok");
            return;
        }
        moocCellInfo varCellInfo=new moocCellInfo();
        varCellInfo.setCellName(vWorkExamList.getTitle());
        varCellInfo.setResId(vWorkExamList.getId());
        doWorkexam(varCellInfo,workExamType);
    }

    private void doWorkexam(moocCellInfo varCellInfo, String workExamType) {

        String resp;
        resp = moocApi.getWorkexam(mZjyUser, mCourseIfno, varCellInfo, workExamType);
        //moocWorkexamInfo varWorkexamInfo=parseWorkexam(resp);

        String unid = moocMianM.getExamRecord(mZjyUser, mCourseIfno, varCellInfo, workExamType);
        Log.d(TAG, "doWorkexam: " + unid);
        String Preview = moocMianM.getExamPreview(mZjyUser, mCourseIfno, varCellInfo, workExamType);
        if (unid == null || unid.isEmpty())
            unid = Tool.parseJsonS(Preview, "uniqueId");
        List<homeWorkAnswInfo> StuAnsw;

        StuAnsw = moocMianM.getStuAnsw(mZjyUser, mCourseIfno, varCellInfo.getResId());
        if (unid != null && !StuAnsw.isEmpty() && !unid.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {
            postHomeWork(StuAnsw, unid, varCellInfo, workExamType);
            return;
        }

        StuAnsw = moocMianM.getStuAnsw2(mZjyUser, mCourseIfno, varCellInfo, Preview, workExamType);
        if (unid != null && !StuAnsw.isEmpty() && !unid.isEmpty() && !StuAnsw.get(0).getAnswer().isEmpty()) {
            postHomeWork(StuAnsw, unid, varCellInfo, workExamType);
            return;
        }
        stringBuffer.append("\n");
        stringBuffer.append("异常未作答");
        mHandler.sendEmptyMessage(100);
    }

    private void postHomeWork(List<homeWorkAnswInfo> StuAnsw, String unid, moocCellInfo varCellInfo, String workExamType) {
        String resp = "";
        for (homeWorkAnswInfo varInfo : StuAnsw) {


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
            Tool.waitTime(mShuake_zyzdjg);
            resp = moocApi.getonlineHomeworkAnswer(mZjyUser, unid, varInfo.getAnswer(), varInfo.getQuestionId(), workExamType);
            stringBuffer.append("\n");
            stringBuffer.append(resp);
            mHandler.sendEmptyMessage(100);
            Log.d(TAG, "postHomeWork: " + resp);
        }

        resp = moocApi.getWorkExamSave(mZjyUser, mCourseIfno, varCellInfo, unid, mShuake_zyzdsj + "", workExamType);
        stringBuffer.append("\n");
        stringBuffer.append(resp);
        mHandler.sendEmptyMessage(100);
        Log.d(TAG, "postHomeWork: " + resp);
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

                if (mThread != null) {
                    // mThread.stop();
                    Log.d(TAG, "handleMessage: " + "stop");
                    isShuake_zt = true;

                }
            }
        }
    };

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.mooc_shuake_setdialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        EditText but_spys = dialogView.findViewById(R.id.zjy_et_spys);
        EditText but_webspys = dialogView.findViewById(R.id.zjy_et_webspys);
        EditText but_fspys = dialogView.findViewById(R.id.zjy_et_fspys);
        EditText but_pjys = dialogView.findViewById(R.id.zjy_et_pjys);
        EditText but_pj = dialogView.findViewById(R.id.zjy_et_pj);
        EditText but_qtpj = dialogView.findViewById(R.id.zjy_et_qtpj);
        EditText mooc_zyzdsj = dialogView.findViewById(R.id.mooc_zyzdsj);
        EditText mooc_zyzdjg = dialogView.findViewById(R.id.mooc_zyzdjg);
        EditText mooc_zh = dialogView.findViewById(R.id.mooc_cy_zh);
        EditText mooc_mm = dialogView.findViewById(R.id.mooc_cy_mm);
        EditText but_xxsc = dialogView.findViewById(R.id.zjy_et_xxsc);
        Button but_qd = dialogView.findViewById(R.id.zjy_bt_qd);
        Button but_dl = dialogView.findViewById(R.id.mooc_cy_dl);
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


        but_dl.setOnClickListener((view) -> {
            String zh = mooc_zh.getText().toString();
            String mm = mooc_mm.getText().toString();
            if (!zh.isEmpty() && !mm.isEmpty()) {
                new Thread(() -> {
                    final String ts;
                    String[] tmp = zjyMobileLogin.login(zh, mm);
                    if (tmp[0] != null && tmp[0].contains("userId")) {
                        zjyUser tmpus = JSONObject.parseObject(tmp[0], zjyUser.class);
                        tmpus.setCookie(tmp[1]);
                        mZjyUser.setOtherZjyUser(tmpus);
                        ts = "登录成功";
                    } else {
                        ts = "登录失败";
                    }
                    runOnUiThread(() -> {
                        Tool.toast(this, ts);
                    });
                }).start();

            }
        });

        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShuake_spkjys = (but_spys.getText().toString().trim().isEmpty() ? 1000 : Integer.parseInt(but_spys.getText().toString()));
                mShuake_webspkjys = (but_webspys.getText().toString().trim().isEmpty() ? 1000 : Integer.parseInt(but_webspys.getText().toString()));
                mShuake_fspkjys = (but_fspys.getText().toString().trim().isEmpty() ? 800 : Integer.parseInt(but_fspys.getText().toString()));
                mShuake_pjys = (but_pjys.getText().toString().trim().isEmpty() ? 500 : Integer.parseInt(but_pjys.getText().toString()));
                mShuake_kjxxsc = (but_xxsc.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(but_xxsc.getText().toString()));
                mShuake_zyzdjg = (mooc_zyzdjg.getText().toString().trim().isEmpty() ? 500 : Integer.parseInt(mooc_zyzdjg.getText().toString()));
                mShuake_zyzdsj = (mooc_zyzdsj.getText().toString().trim().isEmpty() ? 1200 : Integer.parseInt(mooc_zyzdsj.getText().toString()));
                Log.d(TAG, "onClick: " + mShuake_spkjys);
                String[] pj = but_pj.getText().toString().split(" ");
                for (String p : pj) {
                    if (!p.trim().isEmpty())
                        mShuake_kjpj.add(p);
                    Log.d(TAG, "onClick: " + p);
                }
                String[] qtpj = but_qtpj.getText().toString().split(" ");
                for (String p : qtpj) {
                    if (!p.trim().isEmpty())
                        mShuake_qtkjpj.add(p);
                }
                customAlert.cancel();
            }
        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
        if (mThread != null) {
            mThread.interrupt();
        }
    }
}
