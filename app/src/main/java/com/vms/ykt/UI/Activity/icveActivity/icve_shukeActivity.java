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
import androidx.databinding.ViewDataBinding;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;

import com.vms.ykt.Util.Tool;

import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.AnswersInfo;
import com.vms.ykt.yktStuWeb.icve.SectionInfo;
import com.vms.ykt.yktStuWeb.icve.cellInfo;
import com.vms.ykt.yktStuWeb.icve.chapterInfo;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;
import com.vms.ykt.yktStuWeb.icve.knowlegeInfo;
import com.vms.ykt.yktStuWeb.icve.ViewInfo;
import com.vms.ykt.yktStuWeb.icve.workExamListInfo;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;



public class icve_shukeActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();
    private icveCourseInfo mCourseIfno;

    private icveMainW mIcveMainW;
    private icveApiW mIcveApiW;
    private zjyUser mZjyUser;

    private Context mContext;

    private SwitchCompat icve_shuke_zdtl, mooc_shuke_zdcy, mooc_shuke_zdzy, mooc_shuke_zdks,
            mooc_shuke_tgys, mooc_shuke_sk;
    private Button mooc_shuke_sz, mooc_shuke_ks, mooc_shuke_zt;
    private TextView mooc_shuake_jd, mooc_shuke_skrz;

    private boolean isShuake_zt = false;
    private long mShuake_spkjys = 1000, mShuake_fspkjys = 800,
            mShuake_pjys = 500, mShuake_kjxxsc = 0, mShuake_zyzdjg = 500, mShuake_zyzdsj = 1200;
    private ArrayList<String> mShuake_tl;

    int curCt = 0;
    int pageCt = 0;
    final StringBuilder stringBuffer = new StringBuilder();

    private Thread mThread;
    private boolean isDowork = true;


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
        mCourseIfno = (icveCourseInfo) i.getSerializableExtra("Course");
        mZjyUser = (zjyUser) i.getSerializableExtra("mZjyUser");
        icveHttpW mIcveHttpW = new icveHttpW();
        mIcveHttpW.setUserCookie(mZjyUser.getCookie());
        this.mIcveApiW = new icveApiW();
        mIcveApiW.setIcveHttpW(mIcveHttpW);
        this.mIcveMainW = new icveMainW();
        mIcveMainW.setIcveApiW(mIcveApiW);
        mContext = icve_shukeActivity.this;
        mShuake_tl = new ArrayList<>();

        mShuake_tl.add("无");


        Log.d(TAG, "initData: " + mCourseIfno.getTitle());
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
        icve_shuke_zdtl.setChecked(true);

        if (mCourseIfno.getType() == 0) {
            mooc_shuke_tgys.setChecked(true);
        } else {
            mooc_shuke_tgys.setEnabled(false);
            mooc_shuke_zdzy.setEnabled(false);
            mooc_shuke_zdks.setEnabled(false);
        }
        mooc_shuke_skrz.setMovementMethod(ScrollingMovementMethod.getInstance());
        mooc_shuke_skrz.setScrollbarFadingEnabled(false);
        mooc_shuake_jd.setText("进度: 0/0");

        /*

<EditText
            android:id="@+id/redmin_text"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_marginLeft="@dimen/dp15"
            android:background="@android:color/transparent"
            android:cursorVisible="false"
            android:focusable="false"
            android:singleLine="true"
            android:text="横向滑动。横向滑动。横向滑动。横向滑动"
            android:textColor="@color/app_red_color"
            android:textSize="14sp" />


        <EditText
android:layout_width="fill_parent"
android:layout_height="260px"
android:id="@+id/chats_view"
android:textColor="#000000"
android:padding="5px"
android:scrollbars="vertical"<!--垂直滚动条-->
android:singleLine="false"<!--多行-->
android:maxLines="14"<!--最多显示14行-->
android:focusable="false"
android:clickable="true"
android:background="#ff87CEEB"
/>
要在java代码中设置滚动的方法：

et.setMovementMethod(ScrollingMovementMethod.getInstance());

下面是设置显示最新的内容：

et.setSelection(chatsView.getText().length(), chatsView.getText().length());

        android:fadeScrollbars="false"
        edtMsg.setHorizontallyScrolling(true);

        etContent.setOnTouchListener(new View.OnTouchListener() {
    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        if(motionEvent.getAction()==MotionEvent.ACTION_DOWN){
            //通知父控件不要干扰
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if(motionEvent.getAction()==MotionEvent.ACTION_MOVE){
            //通知父控件不要干扰
            view.getParent().requestDisallowInterceptTouchEvent(true);
        }
        if(motionEvent.getAction()==MotionEvent.ACTION_UP){
            view.getParent().requestDisallowInterceptTouchEvent(false);
        }
        return false;
    }
});
        设置ScrollView滚动到顶部
        scrollView.fullScroll(ScrollView.FOCUS_DOWN);

         */
    }

    private void initListener() {
        mooc_shuke_sz.setOnClickListener((view) -> showSetDialog());

        mooc_shuke_ks.setOnClickListener((view) -> {

            Tool.toast(mContext, "开始获取课件");
            mooc_shuke_skrz.setText("获取课件中请等待......");

            isShuake_zt = false;
            pageCt = 0;
            curCt = 0;
            String jd = "进度: " + curCt + "/" + pageCt;
            mooc_shuake_jd.setText(jd);


            stringBuffer.delete(0, stringBuffer.length());

            mThread = new Thread(() -> {
                if (isDowork) {
                    isDowork = false;
                    doWork();
                    //doWorkWk();

                    isDowork = true;
                }
            });
            mThread.start();

        });

        mooc_shuke_zt.setOnClickListener((view) -> isShuake_zt = true);

    }

    private void doWorkWk() {
        List<cellInfo> vCellInfoList = new ArrayList<>();
        for (cellInfo vCellInfo : mIcveMainW.getWkCellsList(mCourseIfno.getId())) {
            Log.d(TAG, "doWorkWk: " + "----" + vCellInfo.getTitle() + "*" + vCellInfo.getCellType());
            vCellInfoList.add(vCellInfo);

        }
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
            runOnUiThread(() -> {
                mooc_shuake_jd.setText("进度: " + curCt + "/" + pageCt);
            });
            shukeWk(vCellInfo);
        }
    }

    private void shukeWk(cellInfo varCellInfo) {
        String kjmc = "\n----" + varCellInfo.getTitle() + " *" + varCellInfo.getCellType();
        stringBuffer.append(kjmc);
        Log.d(TAG, "shuke: " + kjmc);
        mHandler.sendEmptyMessage(100);
        if (mooc_shuke_sk.isChecked()) {

            ViewInfo vMicroView = mIcveMainW.getMicroView(varCellInfo.getId());
            if (vMicroView == null) {
                stringBuffer.append("\n未知异常->跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }
            stringBuffer.append("\n刷课->" + vMicroView.getWorks());
            mHandler.sendEmptyMessage(100);
            if (varCellInfo.getCellType().contains("video")) {
                Tool.waitTime(mShuake_spkjys);
                String resp = mIcveApiW.getMicroUpdateStatus(varCellInfo.getId());
                stringBuffer.append("\n刷课->" + resp);
                mHandler.sendEmptyMessage(100);
            }

            if (varCellInfo.getCellType().contains("discuss")) {

            }

            if (varCellInfo.getCellType().contains("question")) {
                doTest(vMicroView);
            }

        } else {
            stringBuffer.append("\n刷课->跳过");
            mHandler.sendEmptyMessage(100);
        }
    }

    private void doWork() {
        List<cellInfo> vCellInfoList = new ArrayList<>();
        if (mCourseIfno.getType() == 0) {
            //正常课
            for (SectionInfo vSectionInfo : mIcveMainW.getSectionList(mCourseIfno)) {
                if (!mooc_shuke_sk.isChecked()) break;
                for (chapterInfo vChapterInfo : mIcveMainW.getChaptersList(vSectionInfo.getChapters())) {
                    if (!vChapterInfo.getResId().isEmpty()) {

                    }
                    vCellInfoList.addAll(mIcveMainW.getCellsList(vChapterInfo.getCells()));
                    for (knowlegeInfo vKnowlegeInfo : mIcveMainW.getKnowlegesList(vChapterInfo.getKnowleges())) {
                        vCellInfoList.addAll(mIcveMainW.getCellsList(vKnowlegeInfo.getCells()));
                    }
                }
            }
        } else {
            //微课
            if (mooc_shuke_sk.isChecked())
                vCellInfoList = mIcveMainW.getWkCellsList(mCourseIfno.getId());
        }

        pageCt = vCellInfoList.size();
        for (cellInfo vCellInfo : vCellInfoList) {
            if (isShuake_zt) {
                Log.d(TAG, "shuake: " + isShuake_zt);
                stringBuffer.append("\n" + Tool.getCurrentData() + " ->刷课已停止");
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (mThread.isInterrupted()) {
                return;
            }
            if (!mooc_shuke_sk.isChecked()) break;
            curCt++;
            String jd = "进度: " + curCt + "/" + pageCt;
            runOnUiThread(() -> {
                mooc_shuake_jd.setText(jd);
            });

            shuke(vCellInfo);
            if (stringBuffer.length() == 1000) stringBuffer.delete(0, stringBuffer.length());
        }
        if (mCourseIfno.getType() == 0) {

            GetDoWork();

            GetDoExam();
        }
    }

    private void shuke(cellInfo varCellInfo) {

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
                vViewInfo = mIcveMainW.getView(mCourseIfno.getId(), varCellInfo.getId());
            } else {
                //微课
                vViewInfo = mIcveMainW.getMicroView(varCellInfo.getId());
            }
            if (vViewInfo == null) {
                stringBuffer.append("\n未知异常->跳过");
                mHandler.sendEmptyMessage(100);
            }
            String resp = vViewInfo.getWorks();
            stringBuffer.append("\n刷课-> ");
            stringBuffer.append(resp);
            mHandler.sendEmptyMessage(100);

            if (varCellInfo.getCellType().contains("video")) {
                Tool.waitTime(mShuake_spkjys);
                if (mCourseIfno.getType() == 0) {
                    //正常课
                    resp = mIcveApiW.getUpdateStatus(varCellInfo.getId());
                } else {
                    //微课
                    resp = mIcveApiW.getMicroUpdateStatus(varCellInfo.getId());
                    if (resp.isEmpty()) {
                        resp = "---ok";
                    }
                }
                stringBuffer.append("\n刷课->");
                stringBuffer.append(resp);
                mHandler.sendEmptyMessage(100);
            }

            if (varCellInfo.getCellType().contains("discuss")) {
                if (varCellInfo.getStatus() == null || !varCellInfo.getStatus().contains("1"))
                    doDiscuss(vViewInfo, varCellInfo);
            }

            if (varCellInfo.getCellType().contains("question")) {
                if (varCellInfo.getStatus() == null || !varCellInfo.getStatus().contains("1"))
                    doTest(vViewInfo);
            }


        } else {
            stringBuffer.append("\n刷课->跳过");
            mHandler.sendEmptyMessage(100);
        }
    }

    private void doDiscuss(ViewInfo viewInfo, cellInfo varCellInfo) {
        if (icve_shuke_zdtl.isChecked()) {
            String resp = "";
            String ct = Tool.getRandomStr(mShuake_tl);
            if (mCourseIfno.getType() == 0) {
                String cts = mIcveApiW.getReplyContext(mCourseIfno.getId(), varCellInfo.getResId());
                Log.d(TAG, "doDiscuss: " + cts);
                System.out.println(mIcveApiW.addReply(mCourseIfno.getId(), varCellInfo.getResId(), cts.isEmpty() ? ct : cts));
            } else {
                String ResId = Tool.parseJsonS(viewInfo.getCell(), "ResId");
                resp = mIcveApiW.getMicroAddReply(ResId, ct);
            }
            stringBuffer.append("\n 讨论->" + resp);
            mHandler.sendEmptyMessage(100);
        }
    }

    private void doTest(ViewInfo viewInfo) {


        if (mooc_shuke_zdcy.isChecked()) {
            String resp;

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

                List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPapers(data);
                if (vAnswersInfoList.size() == 0) {
                    vAnswersInfoList = mIcveMainW.getAnswArrays(data);
                }
                if (vAnswersInfoList.size() == 0) {
                    return;
                }

                for (AnswersInfo ans : vAnswersInfoList) {
                    String id = ans.getId();

                    Log.d(TAG, ans.getContentText());
                    for (String answ : ans.getAnswersList()) {
                        Log.d(TAG, "doTest: " + answ);
                        resp = mIcveApiW.answerpaper(worksId, id, answ);
                        stringBuffer.append("\n " + id + "->" + resp);
                        mHandler.sendEmptyMessage(100);
                    }
                }
                resp = mIcveApiW.subPaper(worksId);

            } else {
                //微课

                List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPaper(data);
                if (vAnswersInfoList.size() == 0) {
                    vAnswersInfoList = mIcveMainW.getAnswArray(data);
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
                resp = mIcveApiW.getMicroSubPaper(worksId, asnwJson);

            }

            stringBuffer.append("\n 测验->" + resp);
            mHandler.sendEmptyMessage(100);
        }

    }

    private void GetDoWorks() {
        if (mooc_shuke_zdzy.isChecked()) {
            List<workExamListInfo> WorkList = mIcveMainW.getWorksList(mCourseIfno.getId());
            GetDoEw(WorkList, "作业");
        }
    }

    private void GetDoExams() {
        if (mooc_shuke_zdks.isChecked()) {
            List<workExamListInfo> ExamLis = mIcveMainW.getExamList(mCourseIfno.getId());
            GetDoEw(ExamLis, "考试");
        }
    }

    private void GetDoEw(List<workExamListInfo> WorkList, String tn) {
        if (stringBuffer.length() == 1000) stringBuffer.delete(0, stringBuffer.length());
        curCt = 0;
        pageCt = WorkList.size();
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
            runOnUiThread(() -> mooc_shuake_jd.setText(jd));
            GetDoEws(tn, WorkInfo);
        }
    }

    private void GetDoEws(String tn, workExamListInfo workInfo) {

        if (workInfo.getStatus().contains("0")) {
            if (tn.contains("作业")) {
                DoWorks(workInfo);
            } else {
                DoExams(workInfo);
            }
            return;
        }
        stringBuffer.append("\n" + tn + " ->ok");
        mHandler.sendEmptyMessage(100);

    }

    private void DoWorks(workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 作业->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = mIcveApiW.getWorkPerview(mCourseIfno.getId(), WorkInfo.getId());
        DoEw(data);
    }

    private void DoExams(workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 考试->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = mIcveApiW.getExamPerview(mCourseIfno.getId(), WorkInfo.getId());
        DoEw(data);
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
        List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = mIcveMainW.getAnswArrays(data);
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
        String resp = mIcveApiW.getAnswerExam(answerId, asnwJson);
        stringBuffer.append("\n ->" + resp);
        mHandler.sendEmptyMessage(100);
    }

    private void GetDoWork() {
        if (mooc_shuke_zdzy.isChecked()) {
            if (stringBuffer.length() == 1000) stringBuffer.delete(0, stringBuffer.length());
            List<workExamListInfo> WorkList = mIcveMainW.getWorksList(mCourseIfno.getId());
            curCt = 0;
            pageCt = WorkList.size();
            for (workExamListInfo WorkInfo : WorkList) {

                if (isShuake_zt) {
                    Log.d(TAG, "shuake: " + isShuake_zt);
                    stringBuffer.append("\n" + Tool.getCurrentData() + "作业 ->已停止");
                    mHandler.sendEmptyMessage(100);
                    return;
                }
                if (mThread.isInterrupted()) {
                    return;
                }

                curCt++;
                String jd = "作业进度: " + curCt + "/" + pageCt;
                runOnUiThread(() -> mooc_shuake_jd.setText(jd));
                if (WorkInfo.getStatus().contains("0"))
                    DoWork(WorkInfo);
            }
        }
    }

    private void DoWork(workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 作业->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = mIcveApiW.getWorkPerview(mCourseIfno.getId(), WorkInfo.getId());
        if (data == null || !data.contains("answer")) {
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

        List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = mIcveMainW.getAnswArrays(data);
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
        String resp = mIcveApiW.getAnswerOnlineWorks(answerId, asnwJson);
        stringBuffer.append("\n 作业->" + resp);
        mHandler.sendEmptyMessage(100);
    }

    private void GetDoExam() {
        if (mooc_shuke_zdks.isChecked()) {
            if (stringBuffer.length() == 1000) stringBuffer.delete(0, stringBuffer.length());
            List<workExamListInfo> ExamLis = mIcveMainW.getExamList(mCourseIfno.getId());
            curCt = 0;
            pageCt = ExamLis.size();
            for (workExamListInfo ExamInfo : ExamLis) {

                if (isShuake_zt) {
                    Log.d(TAG, "shuake: " + isShuake_zt);
                    stringBuffer.append("\n" + Tool.getCurrentData() + "作业 ->已停止");
                    mHandler.sendEmptyMessage(100);
                    return;
                }
                if (mThread.isInterrupted()) {
                    return;
                }

                curCt++;
                String jd = "考试进度: " + curCt + "/" + pageCt;
                runOnUiThread(() -> mooc_shuake_jd.setText(jd));
                if (ExamInfo.getStatus().contains("0"))
                    DoExam(ExamInfo);
            }
        }
    }

    private void DoExam(workExamListInfo WorkInfo) {
        Log.d(TAG, "DoExam: " + WorkInfo.getTitle() + " *" + WorkInfo.getId());
        stringBuffer.append("\n 考试->----");
        stringBuffer.append(WorkInfo.getTitle());
        mHandler.sendEmptyMessage(100);
        String data = mIcveApiW.getExamPerview(mCourseIfno.getId(), WorkInfo.getId());
        if (data == null || !data.contains("answer")) {
            stringBuffer.append("\n 考试->");
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
        List<AnswersInfo> vAnswersInfoList = mIcveMainW.getAnswPapers(data);
        if (vAnswersInfoList.size() == 0) {
            vAnswersInfoList = mIcveMainW.getAnswArrays(data);
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
        String resp = mIcveApiW.getAnswerExam(answerId, asnwJson);
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

        but_qd.setOnClickListener(view -> {
            mShuake_spkjys = (but_spys.getText().toString().trim().isEmpty() ? 1000 : Integer.parseInt(but_spys.getText().toString()));
            mShuake_fspkjys = (but_fspys.getText().toString().trim().isEmpty() ? 800 : Integer.parseInt(but_fspys.getText().toString()));
            mShuake_pjys = (but_pjys.getText().toString().trim().isEmpty() ? 500 : Integer.parseInt(but_pjys.getText().toString()));
            mShuake_kjxxsc = (but_xxsc.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(but_xxsc.getText().toString()));
            mShuake_zyzdjg = (mooc_zyzdjg.getText().toString().trim().isEmpty() ? 500 : Integer.parseInt(mooc_zyzdjg.getText().toString()));
            mShuake_zyzdsj = (mooc_zyzdsj.getText().toString().trim().isEmpty() ? 1200 : Integer.parseInt(mooc_zyzdsj.getText().toString()));
            Log.d(TAG, "onClick: " + mShuake_spkjys);
            String[] qtpj = but_qtpj.getText().toString().split(" ");
            for (String p : qtpj) {
                if (!p.trim().isEmpty())
                    mShuake_tl.add(p);
            }
            customAlert.cancel();

        });
    }

    @Override
    protected void onDestroy() {
        Log.d(TAG, "onDestroy: ");
        super.onDestroy();
    }



}



