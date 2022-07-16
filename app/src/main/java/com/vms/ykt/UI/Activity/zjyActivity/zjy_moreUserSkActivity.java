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
import com.vms.ykt.Util.EncrypDES;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyCellChildList;
import com.vms.ykt.yktStuMobile.zjy.zjyCellList;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyInfoByCelld;
import com.vms.ykt.yktStuMobile.zjy.zjyMain;
import com.vms.ykt.yktStuMobile.zjy.zjyModuleListInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyTopicList;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class zjy_moreUserSkActivity extends AppCompatActivity {

    private final String TAG = this.getClass().getSimpleName();

    private zjyCourseIfno mCourseIfno;

    private Button mShuake_sz, mShuake_ks, mShuake_zt;
    private SwitchCompat mShuake_zdpj, mShuake_zdwd, mShuake_zdbj, mShuake_zdjc, mShuake_istgys, mShuake_sk,mShuake_web;
    private TextView mShuake_rz, mShuake_jd;
    public boolean isShuake_zt;
    private static long mShuake_spkjys = 6000, mShuake_fspkjys = 2000, mShuake_pjys = 600, mShuake_kjxxsc = 0;
    private final static ArrayList<String> mShuake_kjpj = new ArrayList<>();
    private final static ArrayList<String> mShuake_qtkjpj = new ArrayList<>();
    private zjyUser mZjyUser;
    private Context mContext;
    int curCt = 0;
    int pageCt = 0;
    StringBuffer stringBuffer;

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
        this.mContext = zjy_moreUserSkActivity.this;
        this.mCourseIfno = (zjyCourseIfno) i.getSerializableExtra("Course");
        this.mZjyUser = (zjyUser) i.getSerializableExtra("ZjyUser");

        mShuake_kjpj.add("课件清晰明了");
        mShuake_kjpj.add("无");
        mShuake_kjpj.add("讲得非常棒");
        mShuake_kjpj.add("好");

        mShuake_qtkjpj.add("无");

        EncrypDES des1 ;// 使用默认密钥
        try {
            des1 = new EncrypDES();
            String msg1 = "111";
            Log.d(TAG, "initData: 加密前的字符：" + msg1);
            Log.d(TAG, "initData: 加密后的字符：" + des1.encrypt(msg1));
            Log.d(TAG, "initData: 解密后的字符：" + des1.decrypt(des1.encrypt(msg1)));
        } catch (Exception e) {
            e.printStackTrace();
        }


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

                isShuake_zt = false;
                pageCt=0;
                curCt=0;
                stringBuffer = new StringBuffer();
                mShuake_rz.setText("获取课件中请等待......");
                Tool.toast(zjy_moreUserSkActivity.this, "开始获取课件");
                new Thread(()-> {
                    if (mShuake_web.isChecked()) {
                        doWorkW(mZjyUser, mCourseIfno);
                    }else {
                        doWork(mZjyUser, mCourseIfno);

                    }
                }).start();

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
                mShuake_rz.setText(stringBuffer.toString());
                tvShowBottom();
            }
        }
    };


    private void doWork(zjyUser ZjyUser, zjyCourseIfno zjyCourseInfos) {
        if (AppStatus.getAll()==null||!AppStatus.getZjysk().equals("zjyok"))return;
        List<zjyCellList> varZjyCells = new ArrayList<>();
        StringBuffer vStringBuffer = new StringBuffer();
        for (zjyModuleListInfo varModuleListInfo : zjyMain.getModuleList(ZjyUser, zjyCourseInfos)) {
            vStringBuffer.append("\n-" + varModuleListInfo.getModuleName());
            for (zjyTopicList varTopicList : zjyMain.getTopicList(ZjyUser, zjyCourseInfos, varModuleListInfo)) {
                vStringBuffer.append("\n--" + varTopicList.getTopicName());
                for (zjyCellList varZjyCellList :zjyMain.getCellList(ZjyUser, zjyCourseInfos, varTopicList)) {
                    vStringBuffer.append("\n---" + varZjyCellList.getCellName() + " *" + varZjyCellList.getCategoryName());
                    if (varZjyCellList.getCellChildNodeList() != null) {
                        for (zjyCellChildList varCellChildList : varZjyCellList.getCellChildNodeList()) {
                           /** if (!isShuake_zt) {
                                shuake(ZjyUser, zjyCourseInfos, varCellChildList);
                            } else {
                                return;
                            }**/
                            varZjyCells.add(varCellChildList);

                        }
                    } else {
                       /** if (!isShuake_zt) {
                            shuake(ZjyUser, zjyCourseInfos, varZjyCellList);
                        } else {
                            return;
                        }**/
                        varZjyCells.add(varZjyCellList);

                    }
                }
            }
        }
        pageCt = varZjyCells.size();
        runOnUiThread(()-> {
                mShuake_rz.setText(vStringBuffer.toString());
                tvShowBottom();
                mShuake_jd.setText("进度: " + curCt + "/" + pageCt);
        });

        for (zjyCellList varCellList : varZjyCells) {
            if (isShuake_zt) {
                return;}
                shuake(ZjyUser, zjyCourseInfos, varCellList);
            }
    }

    public void shuake(zjyUser zjyUser, zjyCourseIfno zjyAllCourseIfno, zjyCellList zjyCellList) {

        if (isShuake_zt) {
            return;}

        String kjmc = "\n ----" + zjyCellList.getCellName() + "* " + zjyCellList.getCategoryName() + "* " + zjyCellList.getCellType() +  "* " + zjyCellList.getStudyCellPercent();
        stringBuffer.append(kjmc);
        mHandler.sendEmptyMessage(100);
        Log.d(TAG, "shuake: "+zjyCellList.getStudyCellPercent());
        if (mShuake_sk.isChecked()) {

            if (zjyCellList.getStudyCellPercent().contains("100") && mShuake_istgys.isChecked()) {
                stringBuffer.append("\n 100% 已刷跳过");
                mHandler.sendEmptyMessage(100);
                return;
            }

            zjyInfoByCelld zjyInfoByCelld = zjyMain.getCellInfoByCelld(zjyUser, zjyAllCourseIfno, zjyCellList);
            if (zjyInfoByCelld!=null) {
                if (zjyCellList.getCategoryName().contains("视频")) {
                    Tool.waitTime(mShuake_spkjys);
                } else {
                    Tool.waitTime(mShuake_fspkjys);
                }

                Log.d(TAG, "shuake: " + zjyInfoByCelld.getToken());
                String ret = zjyMain.getStuProcessCellLog(zjyUser, zjyAllCourseIfno, zjyCellList, zjyInfoByCelld, mShuake_kjxxsc);
                stringBuffer.append("\n" + Tool.getCurrentData() + "刷课->" + ret);
                mHandler.sendEmptyMessage(100);
                Tool.waitTime(500);
                zjyApi.getCellListU(zjyUser, zjyAllCourseIfno, zjyCellList.getTopicId());
            }else {
                stringBuffer.append("\n 异常");
                mHandler.sendEmptyMessage(100);
            }
        } else {
            stringBuffer.append("\n 跳过");
            mHandler.sendEmptyMessage(100);
        }



        if (mShuake_zdpj.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() + " 评价->" + zjyApi.getAddCellComment(zjyUser, zjyAllCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_kjpj)));//评价
            mHandler.sendEmptyMessage(100);
        }

        if (mShuake_zdwd.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() + " 问答->" + zjyApi.getAddCellAskAnswer(zjyUser, zjyAllCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_qtkjpj)));//问答
            mHandler.sendEmptyMessage(100);
        }

        if (mShuake_zdbj.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() + " 笔记->" + zjyApi.getAddCellNote(zjyUser, zjyAllCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_qtkjpj)));//笔记
            mHandler.sendEmptyMessage(100);
        }

        if (mShuake_zdjc.isChecked()) {
            Tool.waitTime(mShuake_pjys);
            stringBuffer.append("\n" + Tool.getCurrentData() + " 纠错->" + zjyApi.getAddCellError(zjyUser, zjyAllCourseIfno, zjyCellList, Tool.getRandomStr(mShuake_qtkjpj)));//纠错
            mHandler.sendEmptyMessage(100);
        }

        curCt++;
        runOnUiThread(()-> {
            mShuake_jd.setText("进度: " + curCt + "/" + pageCt);
        });

    }

    private void doWorkW(zjyUser zjyUser, zjyCourseIfno courseIfno) {

    }


    public void tvShowBottom(){
        int offset=mShuake_rz.getLineCount()*mShuake_rz.getLineHeight();
        if (offset > mShuake_rz.getHeight()) {
            mShuake_rz.scrollTo(0, offset +mShuake_rz.getLineHeight()-mShuake_rz.getHeight());
        }
    }

}
