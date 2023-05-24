package com.vms.ykt.UI.Activity.newZjyActivity;

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
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyCellChildList;
import com.vms.ykt.yktStuMobile.zjy.zjyCellList;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyInfoByCelld;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyModuleListInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyTopicList;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.ViewDirectory;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class newZjy_shukeActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private zjyCourseIfno mCourseIfno;

    private Button mShuake_sz, mShuake_ks, mShuake_zt;
    private SwitchCompat mShuake_zdpj, mShuake_zdwd, mShuake_zdbj, mShuake_zdjc, mShuake_istgys, mShuake_sk, mShuake_web, mShuake_websp;
    private TextView mShuake_rz, mShuake_jd;
    private boolean isShuake_zt = false;
    private long mShuake_spkjys = 6000, mShuake_webspkjys = 6000, mShuake_fspkjys = 2000, mShuake_pjys = 600, mShuake_kjxxsc = 0;
    private final ArrayList<String> mShuake_kjpj = new ArrayList<>();
    private final ArrayList<String> mShuake_qtkjpj = new ArrayList<>();
    private zjyUser mZjyUser;
    private Context mContext;
    int curCt = 0;
    int pageCt = 0;
    StringBuilder stringBuffer;
    
    private Thread mThread = null;
    private Thread mThread2 = null;


    private boolean isDowork=true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zjy_shuke_activity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = newZjy_shukeActivity.this;
        this.mCourseIfno = zjyUserDao.sZjyCourseIfno;
        this.mZjyUser = zjyUserDao.sZjyUser;
        zjyHttpW.restCookie(mZjyUser.getCookie());

        mShuake_kjpj.add("课件清晰明了");
        mShuake_kjpj.add("无");
        mShuake_kjpj.add("讲得非常棒");
        mShuake_kjpj.add("好");

        mShuake_qtkjpj.add("无");


        Log.d(TAG, "initData: " + mCourseIfno.getCourseName());
        Log.d(TAG, "initData: " + mZjyUser.getUserId());
    }

    private void initView() {
        mShuake_sz = findViewById(R.id.zjy_shuake_sz);
        mShuake_zdpj = findViewById(R.id.zjy_shuake_pj);
        mShuake_zdwd = findViewById(R.id.zjy_shuake_wd);
        mShuake_zdbj = findViewById(R.id.zjy_shuake_bj);
        mShuake_zdjc = findViewById(R.id.zjy_shuake_jc);
        mShuake_istgys = findViewById(R.id.zjy_shuake_tgys);
        mShuake_sk = findViewById(R.id.zjy_shuake_sk);
        mShuake_web = findViewById(R.id.zjy_shuake_web);
        mShuake_websp = findViewById(R.id.zjy_shuake_webspkj);
        mShuake_ks = findViewById(R.id.zjy_shuake_ks);
        mShuake_zt = findViewById(R.id.zjy_shuake_zt);
        mShuake_jd = findViewById(R.id.zjy_shuake_jd);
        mShuake_rz = findViewById(R.id.zjy_shuake_rz);
        mShuake_istgys.setChecked(true);
        mShuake_sk.setChecked(true);
        mShuake_rz.setMovementMethod(ScrollingMovementMethod.getInstance());
        mShuake_jd.setText("进度: 0/0");
        Log.d(TAG, "initView: " + mShuake_spkjys);
    }

    private void initListener() {

        mShuake_sz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showSetDialog();
            }
        });

        mShuake_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mHandler.sendEmptyMessage(0);
                isShuake_zt = false;
                pageCt = 0;
                curCt = 0;
               stringBuffer.delete(0,stringBuffer.length());

                Tool.toast(newZjy_shukeActivity.this, "开始获取课件");
                mShuake_rz.setText("获取课件中请等待......");
                String jd = "进度: " + 0 + "/" + 0;
                mShuake_jd.setText(jd);
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

        mShuake_zt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isShuake_zt = true;
            }
        });


    }

    private void showSetDialog() {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.zjy_shuake_setdialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        EditText but_spys = dialogView.findViewById(R.id.zjy_et_spys);
        EditText but_webspys = dialogView.findViewById(R.id.zjy_et_webspys);
        EditText but_fspys = dialogView.findViewById(R.id.zjy_et_fspys);
        EditText but_pjys = dialogView.findViewById(R.id.zjy_et_pjys);
        EditText but_pj = dialogView.findViewById(R.id.zjy_et_pj);
        EditText but_qtpj = dialogView.findViewById(R.id.zjy_et_qtpj);
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
        // 移除dialog的decorview背景色
        // Objects.requireNonNull(customAlert.getWindow()).getDecorView().setBackground(null);
        //设置自定义界面的点击事件逻辑

        but_qd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mShuake_spkjys = (but_spys.getText().toString().trim().isEmpty() ? 6000 : Integer.parseInt(but_spys.getText().toString()));
                mShuake_webspkjys = (but_webspys.getText().toString().trim().isEmpty() ? 6000 : Integer.parseInt(but_webspys.getText().toString()));
                mShuake_fspkjys = (but_fspys.getText().toString().trim().isEmpty() ? 2000 : Integer.parseInt(but_fspys.getText().toString()));
                mShuake_pjys = (but_pjys.getText().toString().trim().isEmpty() ? 600 : Integer.parseInt(but_pjys.getText().toString()));
                mShuake_kjxxsc = (but_xxsc.getText().toString().trim().isEmpty() ? 0 : Integer.parseInt(but_xxsc.getText().toString()));
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

    private final Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == 100) {
                Tool.tvShowBottom(mShuake_rz);
                mShuake_rz.setText(stringBuffer.toString());
                Tool.tvShowBottom(mShuake_rz);
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

    private void doWork() {
        AppStatus.AppStatusInit();

        if (AppStatus.getYunbai() != null && (AppStatus.getYunbai().contains(mZjyUser.getDisplayName()) || AppStatus.getYunbai().contains(mZjyUser.getUserName()))) {

            Log.d(TAG, "doWork: " + mZjyUser.getUserName());
        } else {
            if (AppStatus.getZjysk() == null || !AppStatus.getZjysk().equals("zjyok"))
                //return
                ;


            if (AppStatus.getYunhei() != null) {
                if (AppStatus.getYunhei().contains(mZjyUser.getDisplayName()) || AppStatus.getYunhei().contains(mZjyUser.getUserName()))
                    // return
                    ;
            }
        }
        List<zjyCellList> mCellLists ;
        mCellLists = new ArrayList<>();

        zjyCellList vCellList ;

     
            for (zjyModuleListInfo varModuleListInfo : zjyMain.getModuleList(mZjyUser, mCourseIfno)) {
                for (zjyTopicList varTopicList : zjyMain.getTopicList(mZjyUser, mCourseIfno, varModuleListInfo)) {
                    for (zjyCellList varZjyCellList : zjyMain.getCellList(mZjyUser, mCourseIfno, varTopicList)) {
                        if (varZjyCellList.getCellChildNodeList() != null) {
                            for (zjyCellChildList varCellChildList : varZjyCellList.getCellChildNodeList()) {

                           /*if (!isShuake_zt) {
                                shuake(ZjyUser, mCourseIfno, varCellChildList);
                            } else {
                                return;
                            }*/

                                varCellChildList.setModID(varModuleListInfo.getModuleId());
                                mCellLists.add(varCellChildList);

                            }
                        } else {

                       /* if (!isShuake_zt) {
                            shuake(ZjyUser, mCourseIfno, varZjyCellList);
                        } else {
                            return;
                        }*/

                            varZjyCellList.setModID(varModuleListInfo.getModuleId());
                            mCellLists.add(varZjyCellList);
                            if (varZjyCellList.getCategoryName().contains("ppt")) {
                                vCellList = varZjyCellList;
                            }
                        }
                    }
                }
            }
        
        pageCt = mCellLists.size();

        //ShuakeW_xxsc(mCourseIfno,vCellList);
        for (zjyCellList varCellList : mCellLists) {
            if (isShuake_zt) {
                Log.d(TAG, "shuake: " + isShuake_zt);
                stringBuffer.append("\n" + Tool.getCurrentData());//自我总结
                stringBuffer.append(" ->已停止");//自我总结
                mHandler.sendEmptyMessage(100);
                return;
            }
            if (mThread.isInterrupted()) {
                return;
            }
            curCt++;
            runOnUiThread(() -> {
                String jd ="进度: " + curCt + "/" + pageCt;
                mShuake_jd.setText(jd);
            });
            shuake(varCellList);
            if (stringBuffer.length()==1000) stringBuffer.delete(0,stringBuffer.length());

        }
    }

    public void shuake(zjyCellList zjyCellList) {

        String kjmc = "\n ----" + zjyCellList.getCellName() + "* " + zjyCellList.getCategoryName() + "* " + zjyCellList.getCellType() + "* " + zjyCellList.getStudyCellPercent();
        stringBuffer.append(kjmc);
        mHandler.sendEmptyMessage(100);
        Log.d(TAG, "shuake: " + zjyCellList.getStudyCellPercent());
        if (mShuake_sk.isChecked()) {

            if (zjyCellList.getStudyCellPercent().contains("100") && mShuake_istgys.isChecked()) {
                stringBuffer.append("\n 100% 已刷跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }

            if (mShuake_web.isChecked()) {
                shukeW(zjyCellList);
            } else {
                zjyInfoByCelld zjyInfoByCelld = zjyMain.getCellInfoByCelld(mZjyUser, mCourseIfno, zjyCellList);
                if (zjyInfoByCelld != null) {
                    if (zjyCellList.getCategoryName().contains("视频")) {
                        Tool.waitTime(mShuake_spkjys);
                    } else {
                        Tool.waitTime(mShuake_fspkjys);
                    }
                    Log.d(TAG, "shuake: " + zjyInfoByCelld.getToken());


                    String ret = zjyMain.getStuProcessCellLog(mZjyUser, mCourseIfno, zjyCellList, zjyInfoByCelld, mShuake_kjxxsc);
                    if (ret.contains("刷课记录")) {
                        mHandler.sendEmptyMessage(0);
                    }
                    Tool.waitTime(500);
                    zjyApi.getCellListU(mZjyUser, mCourseIfno, zjyCellList.getTopicId());
                    stringBuffer.append("\n" + Tool.getCurrentData());
                    stringBuffer.append( "刷课->" + ret);
                    mHandler.sendEmptyMessage(100);

                } else {
                    stringBuffer.append("\n 异常");
                    mHandler.sendEmptyMessage(100);
                }
            }
        } else {
            stringBuffer.append("\n 跳过");
            mHandler.sendEmptyMessage(100);
        }


        if (mShuake_zdpj.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData());//评价
            stringBuffer.append(" 评价->" + zjyApi.getAddCellComment(mZjyUser, mCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_kjpj)));//评价
            mHandler.sendEmptyMessage(100);
        }

        if (mShuake_zdwd.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() );//问答
            stringBuffer.append(" 问答->" + zjyApi.getAddCellAskAnswer(mZjyUser, mCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_qtkjpj)));//问答
            mHandler.sendEmptyMessage(100);
        }

        if (mShuake_zdbj.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() );//笔记
            stringBuffer.append(" 笔记->" + zjyApi.getAddCellNote(mZjyUser, mCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_qtkjpj)));//笔记
            mHandler.sendEmptyMessage(100);
        }

        if (mShuake_zdjc.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() );//纠错
            stringBuffer.append(" 纠错->" + zjyApi.getAddCellError(mZjyUser, mCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_qtkjpj)));//纠错
            mHandler.sendEmptyMessage(100);
        }


    }

    private void shukeW( zjyCellList zjyCellList) {


        if (zjyCellList.getCategoryName().contains("视频")) {
            if (!mShuake_websp.isChecked()) {
                stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->视频跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }
            String ret = zjyApiW.getChangeData(mCourseIfno, zjyCellList);
            Tool.waitTime(500);
            ViewDirectory varViewDirectory = zjyMainW.getViewDirectory(mCourseIfno, zjyCellList);
            Log.d(TAG, "shukeW: " + ret);
            if (varViewDirectory.getAudioVideoLong() == null) {
                stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->异常");
                stringBuffer.append("刷课->异常");
                mHandler.sendEmptyMessage(100);
                return;
            }

            double VideoLong = Double.parseDouble(varViewDirectory.getAudioVideoLong());
            String Percent = varViewDirectory.getCellPercent();
            if (Percent == null) Percent = "0";
            double cellPercent = Double.parseDouble(Percent);
            VideoLong = VideoLong - (VideoLong * cellPercent / 100);
            int count = (int) VideoLong / 10;
            if (VideoLong < 10) {
                shuekeW_UI(zjyCellList, varViewDirectory, mShuake_webspkjys, VideoLong);
            } else {
                double time = 10.654;
                for (int j = 0; j <= count; j++) {
                    if (isShuake_zt) {
                        return;
                    }
                    shuekeW_UI(zjyCellList, varViewDirectory, mShuake_webspkjys, time);
                    if (j == count) {
                        shuekeW_UI(zjyCellList, varViewDirectory, mShuake_webspkjys, VideoLong);
                    }
                    time = time + 10.1534;
                }
            }

        } else {
            String ret = zjyApiW.getChangeData(mCourseIfno, zjyCellList);
            Tool.waitTime(500);
            ViewDirectory varViewDirectory = zjyMainW.getViewDirectory(mCourseIfno, zjyCellList);
            Log.d(TAG, "shukeW: " + ret);
            shuekeW_UI(zjyCellList, varViewDirectory, mShuake_fspkjys, 666);
        }


    }

    private void shuekeW_UI(zjyCellList zjyCellList, ViewDirectory varViewDirectory, long time, double VideoLong) {
        if (varViewDirectory == null) {
            stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->503");
            mHandler.sendEmptyMessage(100);
            return;
        }
        if (isShuake_zt) {
            stringBuffer.append("\n" + Tool.getCurrentData() + " ->已停止");//自我总结
            mHandler.sendEmptyMessage(100);
            return;
        }
        String ret;
        Tool.waitTime(time);
        ret = zjyApiW.getProcessCellLog(varViewDirectory, zjyCellList, String.valueOf(VideoLong));
        if (ret != null) {
            if (ret.contains("刷课记录")) {
                mHandler.sendEmptyMessage(0);
            }
        } else {
            ret = "503";
        }
        stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->" + ret);
        mHandler.sendEmptyMessage(100);
    }

    private void ShuakeW_xxsc(zjyCellList vCellList) {
        if (vCellList == null) return;
        //单刷学习时间
        mThread2 = new Thread(() -> {
            Log.d(TAG, "shukeW: " + zjyApiW.getChangeData(mCourseIfno, vCellList));
            Tool.waitTime(500);
            ViewDirectory varViewDirectory = zjyMainW.getViewDirectory(mCourseIfno, vCellList);
            if (varViewDirectory == null) return;
            while (true) {
                if (mThread2.isInterrupted()) {
                    return;
                }
                if (isShuake_zt) {
                    stringBuffer.append("\n" + Tool.getCurrentData() + " ->已停止");//自我总结
                    mHandler.sendEmptyMessage(100);
                    return;
                }

                if (!mShuake_sk.isChecked()) {
                    Tool.waitTime(2000);
                    Log.d(TAG, "ShuakeW_xxsc: " + zjyApiW.getProcessCellLog(varViewDirectory, "666"));
                }
            }
        });
        mThread2.start();
    }

    @Override
    protected void onDestroy() {
        if (mThread != null) {
            mThread.interrupt();
        }
        if (mThread2 != null) {
            mThread2.interrupt();
        }
        Log.d(TAG, "onDestroy: ");

        super.onDestroy();
    }
}
