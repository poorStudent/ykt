package com.vms.ykt.UI.Activity.icveActivity;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.AnswersInfo;
import com.vms.ykt.yktStuWeb.icve.SectionInfo;
import com.vms.ykt.yktStuWeb.icve.ViewInfo;
import com.vms.ykt.yktStuWeb.icve.cellInfo;
import com.vms.ykt.yktStuWeb.icve.chapterInfo;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;
import com.vms.ykt.yktStuWeb.icve.knowlegeInfo;
import com.vms.ykt.yktStuWeb.icve.workExamListInfo;

import com.vms.ykt.yktUtil.zjy.zjyMobileLogin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class icve_moreUserSkActivity extends AppCompatActivity {

    private String TAG = this.getClass().getSimpleName();

    private ExecutorService MExecutorService = Executors.newFixedThreadPool(3);
    private Context mContext;

    private SwitchCompat icve_shuke_zdtl, mooc_shuke_zdcy, mooc_shuke_zdzy, mooc_shuke_zdks,
            mooc_shuke_tgys, mooc_shuke_sk;
    private Button mooc_shuke_sz, mooc_shuke_ks, mooc_shuke_zt;
    private TextView mooc_shuake_jd, mooc_shuke_skrz;

    private boolean isShuake_zt = false;
    private long mShuake_spkjys = 1000, mShuake_fspkjys = 800,
            mShuake_pjys = 500, mShuake_kjxxsc = 0, mShuake_zyzdjg = 500, mShuake_zyzdsj = 1200;
    private ArrayList<String> mShuake_tl;

    private final String dp = Tool.getSD().getAbsolutePath() + File.separator + "vmsykt" + File.separator;

    StringBuilder stringBuffer;

    private Thread mThread;
    private boolean isDowork = true;

    ArrayList<String> zjyUsers;

    private volatile int zhzs=0,yszhs=0;

    private final ArrayList<View> mViews=new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.icve_shuke_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        mContext = icve_moreUserSkActivity.this;
        mShuake_tl = new ArrayList<>();


        mShuake_tl.add("无");

    }

    private void initView() {
        mooc_shuke_sz = findViewById(R.id.mooc_shuke_sz);
        mooc_shuke_ks = findViewById(R.id.mooc_ks);
        mooc_shuke_zt = findViewById(R.id.mooc_zt);
        icve_shuke_zdtl = findViewById(R.id.icve_zdtl);
        mooc_shuke_zdcy = findViewById(R.id.mooc_zdcy);
        mooc_shuke_zdzy = findViewById(R.id.mooc_zdzy);
        mooc_shuke_zdks = findViewById(R.id.mooc_zdks);
        mooc_shuke_tgys = findViewById(R.id.mooc_tgys);
        mooc_shuke_sk = findViewById(R.id.mooc_sk);
        mooc_shuake_jd = findViewById(R.id.mooc_shuake_jd);
        mooc_shuke_skrz = findViewById(R.id.mooc_skrz);

        mooc_shuke_sk.setChecked(true);


        mooc_shuke_skrz.setMovementMethod(ScrollingMovementMethod.getInstance());

    }

    private void initListener() {
        mooc_shuke_sz.setOnClickListener((view) -> {
            showSetDialog();
        });

        mooc_shuke_ks.setOnClickListener((view) -> {
            isShuake_zt = false;
            stringBuffer.delete(0,stringBuffer.length());
            Tool.toast(mContext, "开始获取课件");
            zhzs=0;
            yszhs=0;
            String jd="账号个数: "+yszhs+"/"+zhzs;
            mooc_shuake_jd.setText(jd);
            mThread = new Thread(() -> {
                if (isDowork) {
                    isDowork = false;
                    Work();
                    isDowork = true;
                }
            });
            mThread.start();

        });

        mooc_shuke_zt.setOnClickListener((view) -> {
            isShuake_zt = true;
        });

    }

    private void Work() {
        if (!mooc_shuke_sk.isChecked())return;
        zjyUsers = Tool.readLineFile(dp, "icveUser.txt");

        for (String s : zjyUsers) {
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
                    Tool.waitTime(Tool.getRandomInt(1000, 3000));
                    goWork(info[0], info[1], course);
                }

            };
            MExecutorService.execute(vRunnable);
        }
    }

    private void goWork(String username, String password, ArrayList<String> course) {

        final String[] info = zjyMobileLogin.login(username, password);
        final zjyUser mZjyUser;
        String ck = info[1];
        Log.d(TAG, "goWork: " + ck);
        if (ck != null && !ck.isEmpty()) {
            mZjyUser = JSONObject.parseObject(info[0], zjyUser.class);
            mZjyUser.setCookie(ck);
        } else {
            mZjyUser = null;
        }
        if (mZjyUser == null || mZjyUser.getUserId() == null) {
            stringBuffer.append("\n" + Tool.getCurrentData() + " " + username + " ->登录失败");
            mHandler.sendEmptyMessage(100);
            return;
        }
        stringBuffer.append("\n" + Tool.getCurrentData() + " " + username + " ->登录成功");
        mHandler.sendEmptyMessage(100);


        synchronized (this) {
            icveHttpW.restCookie("");
            icveHttpW.restCookie(mZjyUser.getCookie());
            for (icveCourseInfo vIcveCourseInfo : icveMainW.getMyCourseList(mZjyUser)) {
                if (vIcveCourseInfo.getType() == 2) continue;

                if (course.contains(vIcveCourseInfo.getTitle())) {

                    doWork(mZjyUser, vIcveCourseInfo);

                }
            }
            String jd = "账号个数: " + yszhs + "/" + zhzs;
            runOnUiThread(() -> mooc_shuake_jd.setText(jd));
        }
    }



    private void doWork(zjyUser zjyUser, icveCourseInfo mCourseIfno ) {
        List<cellInfo> vCellInfoList = new ArrayList<>();
        if (mCourseIfno.getType() == 0) {
            //正常课
            for (SectionInfo vSectionInfo : icveMainW.getSectionList(mCourseIfno)) {
                if (!mooc_shuke_sk.isChecked())break;
                for (chapterInfo vChapterInfo : icveMainW.getChaptersList(vSectionInfo.getChapters())) {
                    if (!vChapterInfo.getResId().isEmpty()) {

                    }
                    vCellInfoList.addAll(icveMainW.getCellsList(vChapterInfo.getCells()));
                    for (knowlegeInfo vKnowlegeInfo : icveMainW.getKnowlegesList(vChapterInfo.getKnowleges())) {
                        vCellInfoList.addAll(icveMainW.getCellsList(vKnowlegeInfo.getCells()));
                    }
                }
            }
        } else {
            //微课
            if (mooc_shuke_sk.isChecked())
                vCellInfoList=icveMainW.getWkCellsList(mCourseIfno.getId());
        }
        int pageCt = vCellInfoList.size();
        int curCt = 0;
        for (cellInfo vCellInfo : vCellInfoList) {
            if (isShuake_zt) {
                Log.d(TAG, "shuake: " + isShuake_zt);
                stringBuffer.append("\n" + Tool.getCurrentData() + " ->刷课已停止");
                mHandler.sendEmptyMessage(100);
            }
            if (mThread.isInterrupted()) {
                return;
            }
            if (!mooc_shuke_sk.isChecked())break;

            curCt++;

            stringBuffer.append("进度: " + curCt + "/" + pageCt);
            mHandler.sendEmptyMessage(100);
            if (stringBuffer.length()==1000) stringBuffer.delete(0,stringBuffer.length());
            shuke(zjyUser, mCourseIfno, vCellInfo);
        }

        if (mCourseIfno.getType() == 0) {

            GetDoWork( mCourseIfno);

            GetDoExam( mCourseIfno);
        }
    }

    private void shuke(zjyUser zjyUser, icveCourseInfo mCourseIfno, cellInfo varCellInfo ) {

        String kjmc;
        if (mCourseIfno.getType() == 0) {
            //正常课
            kjmc = "\n----" + varCellInfo.getTitle() + " *" + varCellInfo.getCellType() + " *" + varCellInfo.getStatus();
        } else {
            //微课
            kjmc = "\n----" + varCellInfo.getTitle() + " *" + varCellInfo.getCellType();
        }
        stringBuffer.append(kjmc);
        Log.d(TAG, "shuke: " + kjmc);
        mHandler.sendEmptyMessage(100);
        if (stringBuffer.length() == 500) {
            stringBuffer.delete(0, 500);
        }

        if (mooc_shuke_sk.isChecked()) {

            if (mooc_shuke_tgys.isChecked() && varCellInfo.getStatus().contains("1") && mCourseIfno.getType() == 0) {
                //正常课
                stringBuffer.append("\n 已刷跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }

            ViewInfo vViewInfo = null;
            if (mCourseIfno.getType() == 0) {
                //正常课
                vViewInfo = icveMainW.getView(mCourseIfno.getId(), varCellInfo.getId());
            } else {
                //微课
                vViewInfo = icveMainW.getMicroView(varCellInfo.getId());
            }
            if (vViewInfo == null) {
                stringBuffer.append("\n未知异常->跳过");
                mHandler.sendEmptyMessage(100);
            }
            stringBuffer.append("\n刷课->" + vViewInfo.getWorks());
            mHandler.sendEmptyMessage(100);

            if (varCellInfo.getCellType().contains("video")) {
                Tool.waitTime(mShuake_spkjys);
                String resp;
                if (mCourseIfno.getType() == 0) {
                    //正常课
                    resp = icveApiW.getUpdateStatus(varCellInfo.getId());
                } else {
                    //微课
                    resp = icveApiW.getMicroUpdateStatus(varCellInfo.getId());
                    if (resp.isEmpty()) {
                        resp = "---ok";
                    }
                }
                stringBuffer.append("\n刷课->" + resp);
                mHandler.sendEmptyMessage(100);
            }

            if (varCellInfo.getCellType().contains("discuss")) {
                if (varCellInfo.getStatus()==null||varCellInfo.getStatus().contains("0"))
                doDiscuss(zjyUser, mCourseIfno, vViewInfo, varCellInfo);
            }

            if (varCellInfo.getCellType().contains("question")) {
                if (varCellInfo.getStatus()==null||varCellInfo.getStatus().contains("0"))
                doTest(mCourseIfno, vViewInfo);
            }


        } else {
            stringBuffer.append("\n刷课->跳过");
            mHandler.sendEmptyMessage(100);
        }
    }

    private void doDiscuss(zjyUser zjyUser, icveCourseInfo mCourseIfno , ViewInfo viewInfo, cellInfo varCellInfo) {
        if (icve_shuke_zdtl.isChecked()) {
            String resp = "";
            String ct = Tool.getRandomStr(mShuake_tl);
            if (mCourseIfno.getType() == 0) {
                String cts = icveApiW.getReplyContext(mCourseIfno.getId(), varCellInfo.getResId());
                Log.d(TAG, "doDiscuss: " + cts);
                System.out.println(icveApiW.addReply(mCourseIfno.getId(), varCellInfo.getResId(), cts.isEmpty() ? ct : cts));
            } else {
                String ResId = Tool.parseJsonS(viewInfo.getCell(), "ResId");
                resp = icveApiW.getMicroAddReply(ResId, ct);
            }
            stringBuffer.append("\n 讨论->" + resp);
            mHandler.sendEmptyMessage(100);
        }
    }

    private void doTest(icveCourseInfo mCourseIfno , ViewInfo viewInfo) {


        if (mooc_shuke_zdcy.isChecked()) {
            String resp = "";

            String data = viewInfo.getData();
            if (data == null || data.isEmpty()) {
                return;
            }


            String worksId = Tool.parseJsonS(viewInfo.getWorks(), "Id");
            if (worksId == null || worksId.isEmpty()) {
                stringBuffer.append("\n 测验-> 获取id失败 跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }

            if (mCourseIfno.getType() == 0) {
                //正常课

                List<AnswersInfo> vAnswersInfoList = icveMainW.getAnswPapers(data);
                if (vAnswersInfoList.size() == 0) {
                    vAnswersInfoList = icveMainW.getAnswArrays(data);
                }
                if (vAnswersInfoList.size() == 0) {
                    return;
                }

                for (AnswersInfo ans : vAnswersInfoList) {
                    String id = ans.getId();

                    Log.d(TAG, ans.getContentText());
                    for (String answ : ans.getAnswersList()) {
                        Log.d(TAG, "doTest: " + answ);
                        resp = icveApiW.answerpaper(worksId, id, answ);
                        stringBuffer.append("\n " + id + "->" + resp);
                        mHandler.sendEmptyMessage(100);
                    }
                }
                resp = icveApiW.subPaper(worksId);

            } else {
                //微课

                List<AnswersInfo> vAnswersInfoList = icveMainW.getAnswPaper(data);
                if (vAnswersInfoList.size() == 0) {
                    vAnswersInfoList = icveMainW.getAnswArray(data);
                }
                if (vAnswersInfoList.size() == 0) {
                    return;
                }

                List<HashMap<Object, Object>> answList = new ArrayList<>();
                for (AnswersInfo vAnswersInfo : vAnswersInfoList) {
                    HashMap<Object, Object> answHashMap = new HashMap<>();
                    answHashMap.put("paperItemId", vAnswersInfo.getId());
                    answHashMap.put("answer", vAnswersInfo.getAnswersContent());
                    answList.add(answHashMap);
                }
                String asnwJson = JSONArray.toJSONString(answList);
                Log.d(TAG, "doTest: " + asnwJson);
                resp = icveApiW.getMicroSubPaper(worksId, asnwJson);

            }

            stringBuffer.append("\n 测验->" + resp);
            mHandler.sendEmptyMessage(100);
        }

    }

    private void GetDoWork(icveCourseInfo mCourseIfno ) {
        if (mooc_shuke_zdzy.isChecked()) {
            if (stringBuffer.length()==1000) stringBuffer.delete(0,stringBuffer.length());
            List<workExamListInfo> WorkList = icveMainW.getWorksList(mCourseIfno.getId());
            int curCt = 0;
            int pageCt = WorkList.size();
            for (workExamListInfo WorkInfo : WorkList) {

                if (isShuake_zt) {
                    Log.d(TAG, "shuake: " + isShuake_zt);
                    stringBuffer.append("\n" + Tool.getCurrentData() + "作业 ->已停止");
                    mHandler.sendEmptyMessage(100);
                }
                if (mThread.isInterrupted()) {
                    return;
                }

                curCt++;

                stringBuffer.append("作业进度: " + curCt + "/" + pageCt);
                mHandler.sendEmptyMessage(100);
                if (WorkInfo.getStatus().contains("0"))
                DoWork(mCourseIfno, WorkInfo);
            }
        }
    }

    private void DoWork(icveCourseInfo mCourseIfno , workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());

        String data = icveApiW.getWorkPerview(mCourseIfno.getId(), WorkInfo.getId());
        String answerId = JSONObject.parseObject(data).getJSONObject("answer").getString("Id");
        if (answerId == null || answerId.isEmpty()) {
            stringBuffer.append("\n 作业->获取id失败 跳过");
            mHandler.sendEmptyMessage(100);
            return;
        }
        if (data == null || data.isEmpty()) {
            return;
        }
        List<AnswersInfo> vAnswersInfoList = icveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = icveMainW.getAnswArrays(data);
        }
        if (vAnswersInfoList.size() == 0) {
            return;
        }
        List<HashMap<Object, Object>> answList = new ArrayList<>();
        for (AnswersInfo vAnswersInfo : vAnswersInfoList) {
            HashMap<Object, Object> answHashMap = new HashMap<>();
            answHashMap.put("paperItemId", vAnswersInfo.getId());
            answHashMap.put("answer", vAnswersInfo.getAnswersContent());
            answList.add(answHashMap);
        }
        String asnwJson = JSONArray.toJSONString(answList);
        Log.d(TAG, "doTest: " + asnwJson);
        String resp = icveApiW.getAnswerOnlineWorks(answerId, asnwJson);
        stringBuffer.append("\n 作业->" + resp);
        mHandler.sendEmptyMessage(100);
    }

    private void GetDoExam(icveCourseInfo mCourseIfno ) {
        if (mooc_shuke_zdks.isChecked()) {
            if (stringBuffer.length()==1000) stringBuffer.delete(0,stringBuffer.length());
            List<workExamListInfo> ExamLis = icveMainW.getExamList(mCourseIfno.getId());
            int curCt = 0;
            int pageCt = ExamLis.size();
            for (workExamListInfo ExamInfo : ExamLis) {

                if (isShuake_zt) {
                    Log.d(TAG, "shuake: " + isShuake_zt);
                    stringBuffer.append("\n" + Tool.getCurrentData() + "作业 ->已停止");
                    mHandler.sendEmptyMessage(100);
                }
                if (mThread.isInterrupted()) {
                    return;
                }

                curCt++;

                stringBuffer.append("考试进度: " + curCt + "/" + pageCt);
                mHandler.sendEmptyMessage(100);
                if (ExamInfo.getStatus().contains("0"))
                DoExam(mCourseIfno, ExamInfo);
            }
        }
    }

    private void DoExam(icveCourseInfo mCourseIfno , workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());

        String data = icveApiW.getExamPerview(mCourseIfno.getId(), WorkInfo.getId());
        String answerId = JSONObject.parseObject(data).getJSONObject("answer").getString("Id");
        if (answerId == null || answerId.isEmpty()) {
            stringBuffer.append("\n 作业->获取id失败 跳过");
            mHandler.sendEmptyMessage(100);
            return;
        }
        if (data == null || data.isEmpty()) {
            return;
        }
        List<AnswersInfo> vAnswersInfoList = icveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = icveMainW.getAnswArrays(data);
        }
        if (vAnswersInfoList.size() == 0) {
            return;
        }
        List<HashMap<Object, Object>> answList = new ArrayList<>();
        for (AnswersInfo vAnswersInfo : vAnswersInfoList) {
            HashMap<Object, Object> answHashMap = new HashMap<>();
            answHashMap.put("paperItemId", vAnswersInfo.getId());
            answHashMap.put("answer", vAnswersInfo.getAnswersContent());
            answList.add(answHashMap);
        }
        String asnwJson = JSONArray.toJSONString(answList);
        Log.d(TAG, "doTest: " + asnwJson);
        String resp = icveApiW.getAnswerExam(answerId, asnwJson);
        stringBuffer.append("\n 作业->" + resp);
        mHandler.sendEmptyMessage(100);
    }


    private void doWorkWk(zjyUser zjyUser, icveCourseInfo courseIfno ) {
        List<cellInfo> vCellInfoList = icveMainW.getWkCellsList(courseIfno.getId());
        for (cellInfo vCellInfo : vCellInfoList) {
            Log.d(TAG, "doWorkWk: " + "----" + vCellInfo.getTitle() + "*" + vCellInfo.getCellType());

        }
        int curCt = 0;
        int pageCt = 0;
        pageCt = vCellInfoList.size();
        for (cellInfo vCellInfo : vCellInfoList) {
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

            shukeWk(zjyUser, courseIfno, vCellInfo);

            stringBuffer.append("进度: " + curCt + "/" + pageCt);
        }
    }

    private void shukeWk(zjyUser zjyUser, icveCourseInfo CourseInfo, cellInfo varCellInfo ) {
        String kjmc = "\n----" + varCellInfo.getTitle() + " *" + varCellInfo.getCellType();
        stringBuffer.append(kjmc);
        Log.d(TAG, "shuke: " + kjmc);
        mHandler.sendEmptyMessage(100);
        if (mooc_shuke_sk.isChecked()) {

            ViewInfo vMicroView = icveMainW.getMicroView(varCellInfo.getId());
            if (vMicroView == null) {
                stringBuffer.append("\n未知异常->跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }
            stringBuffer.append("\n刷课->" + vMicroView.getWorks());
            mHandler.sendEmptyMessage(100);
            if (varCellInfo.getCellType().contains("video")) {
                Tool.waitTime(mShuake_spkjys);
                String resp = icveApiW.getMicroUpdateStatus(varCellInfo.getId());
                stringBuffer.append("\n刷课->" + resp);
                mHandler.sendEmptyMessage(100);
            }

            if (varCellInfo.getCellType().contains("discuss")) {

            }

            if (varCellInfo.getCellType().contains("question")) {

            }

        } else {
            stringBuffer.append("\n刷课->跳过");
            mHandler.sendEmptyMessage(100);
        }
    }

    private void GetDoWorks(icveCourseInfo mCourseIfno,icveMainW icveMainW,icveApiW icveApiW) {
        if (mooc_shuke_zdzy.isChecked()) {
            List<workExamListInfo> WorkList = icveMainW.getWorksList(mCourseIfno.getId());
            GetDoEw(WorkList, "作业", mCourseIfno   );
        }
    }

    private void GetDoExams(icveCourseInfo mCourseIfno  ) {
        if (mooc_shuke_zdks.isChecked()) {
            List<workExamListInfo> ExamLis = icveMainW.getExamList(mCourseIfno.getId());
            GetDoEw(ExamLis, "考试", mCourseIfno   );
        }
    }

    private void GetDoEw(List<workExamListInfo> WorkList, String tn,icveCourseInfo mCourseIfno  ) {
        if (stringBuffer.length() == 1000) stringBuffer.delete(0, stringBuffer.length());
        int curCt = 0;
        int pageCt = WorkList.size();
        for (workExamListInfo WorkInfo : WorkList) {

            if (isShuake_zt) {
                Log.d(TAG, "shuake: " + isShuake_zt);
                stringBuffer.append("\n" + tn + " ->已停止");
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (mThread.isInterrupted()) {
                return;
            }

            curCt++;
            String jd = tn + "进度: " + curCt + "/" + pageCt;
            stringBuffer.append(jd);
            mHandler.sendEmptyMessage(100);
            GetDoEws(tn, WorkInfo, mCourseIfno  );
        }
    }

    private void GetDoEws(String tn, workExamListInfo workInfo, icveCourseInfo mCourseIfno  ) {

        if (workInfo.getStatus().contains("0")) {
            if (tn.contains("作业")) {
                DoWorks(workInfo, mCourseIfno  );
            } else {
                DoExams(workInfo, mCourseIfno  );
            }
            return;
        }
        stringBuffer.append("\n" + tn + " ->ok");
        mHandler.sendEmptyMessage(100);

    }

    private void DoWorks(workExamListInfo WorkInfo, icveCourseInfo mCourseIfno  ) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 作业->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = icveApiW.getWorkPerview(mCourseIfno.getId(), WorkInfo.getId());
        DoEw(data  );
    }

    private void DoExams(workExamListInfo WorkInfo, icveCourseInfo mCourseIfno  ) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 考试->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = icveApiW.getExamPerview(mCourseIfno.getId(), WorkInfo.getId());
        DoEw(data  );
    }

    private void DoEw(String data) {
        if (data == null || !data.contains("answer")) {
            stringBuffer.append("\n ->");
            stringBuffer.append(data);
            mHandler.sendEmptyMessage(100);
            return;
        }
        String answerId = JSONObject.parseObject(data).getJSONObject("answer").getString("Id");
        if (answerId == null || answerId.isEmpty()) {
            stringBuffer.append("\n ->获取id失败 跳过");
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
        List<HashMap<Object, Object>> answList = new ArrayList<>();
        for (AnswersInfo vAnswersInfo : vAnswersInfoList) {
            HashMap<Object, Object> answHashMap = new HashMap<>();
            answHashMap.put("paperItemId", vAnswersInfo.getId());
            answHashMap.put("answer", vAnswersInfo.getAnswersContent());
            answList.add(answHashMap);
        }
        String asnwJson = JSONArray.toJSONString(answList);
        Log.d(TAG, "doTest: " + asnwJson);
        String resp = icveApiW.getAnswerExam(answerId, asnwJson);
        stringBuffer.append("\n ->" + resp);
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
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.icve_shuake_setdialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        EditText but_spys = dialogView.findViewById(R.id.zjy_et_spys);
        EditText but_fspys = dialogView.findViewById(R.id.zjy_et_fspys);
        EditText but_pjys = dialogView.findViewById(R.id.zjy_et_pjys);
        EditText but_pj = dialogView.findViewById(R.id.zjy_et_pj);
        EditText but_qtpj = dialogView.findViewById(R.id.zjy_et_qtpj);
        EditText mooc_zyzdsj = dialogView.findViewById(R.id.mooc_zyzdsj);
        EditText mooc_zyzdjg = dialogView.findViewById(R.id.mooc_zyzdjg);
        EditText but_xxsc = dialogView.findViewById(R.id.zjy_et_xxsc);
        Button but_qd = dialogView.findViewById(R.id.zjy_bt_qd);
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

        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShuake_spkjys = (but_spys.getText().toString().trim().isEmpty() ? 1000 : Integer.parseInt(but_spys.getText().toString()));
                mShuake_fspkjys = (but_fspys.getText().toString().trim().isEmpty() ? 800 : Integer.parseInt(but_fspys.getText().toString()));
                mShuake_pjys = (but_pjys.getText().toString().trim().isEmpty() ? 500 : Integer.parseInt(but_pjys.getText().toString()));
                mShuake_kjxxsc = (but_xxsc.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(but_xxsc.getText().toString()));
                mShuake_zyzdjg = (mooc_zyzdjg.getText().toString().trim().isEmpty() ? 500 : Integer.parseInt(mooc_zyzdjg.getText().toString()));
                mShuake_zyzdsj = (mooc_zyzdsj.getText().toString().trim().isEmpty() ? 1200 : Integer.parseInt(mooc_zyzdsj.getText().toString()));
                Log.d(TAG, "onClick: " + mShuake_spkjys);
                String[] pj = but_pj.getText().toString().split(" ");
                for (String p : pj) {
                    if (!p.trim().isEmpty())
                        mShuake_tl.add(p);
                    Log.d(TAG, "onClick: " + p);
                }
                String[] qtpj = but_qtpj.getText().toString().split(" ");
                for (String p : qtpj) {
                    if (!p.trim().isEmpty())
                        mShuake_tl.add(p);
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

    public static void main(String[] args) {
        System.out.println("66666");
    }
}
