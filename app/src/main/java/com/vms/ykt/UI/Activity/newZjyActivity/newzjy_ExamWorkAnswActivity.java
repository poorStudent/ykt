package com.vms.ykt.UI.Activity.newZjyActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.alibaba.fastjson.JSONObject;
import com.vms.ykt.R;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ExamWork;
import com.vms.ykt.yktStuMobile.newZJY.ExamWorkInfo;
import com.vms.ykt.yktStuMobile.newZJY.examAnsw;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyHttp;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;



import java.util.List;
import java.util.Objects;

public class newzjy_ExamWorkAnswActivity extends AppCompatActivity {
    private static final String TAG = newzjy_ExamWorkAnswActivity.class.getSimpleName();
    private Context mContext;

    private View root = null;
    private TextView mButton2, zjy_exam_answ;
    private Button mButton, zjy_bt_html, zjy_bt_zdzd;

    private ProgressBar mProgressBar;
    private Thread mThread;
    private boolean isDoWork = true;

    private int type;

    private newZjyUser mNewZjyUser;
    private ExamWork mExamWork;
    private newZjyCourse mNewZjyCourse;
    private examAnsw mExamAnsw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.newzjy_examwork_answactivity);
        initData();
        initView();
        initListener();
        Log.d(TAG, "onCreate: ");
    }

    private void initData() {
        Intent i = getIntent();
        this.mContext = newzjy_ExamWorkAnswActivity.this;
        this.type = i.getIntExtra("type", 0);
        this.mExamWork = newZjyUserDao.sExamWork;
        this.mNewZjyUser = newZjyUserDao.sNewZjyUser;
        Log.d(TAG, "initData: " + mExamWork.getExamName());
        Log.d(TAG, "initData: " + mExamWork.getExamId());

    }

    private void initView() {
        mButton = findViewById(R.id.zjy_bt_djjz);
        mButton2 = findViewById(R.id.zjy_bt_jrkt);
        mProgressBar = findViewById(R.id.zjy_load_Bar);
        zjy_exam_answ = findViewById(R.id.zjy_exam_answ);
        zjy_bt_html = findViewById(R.id.zjy_bt_html);
        zjy_bt_zdzd = findViewById(R.id.zjy_bt_zdzd);

    }

    private void initListener() {


        mButton.setOnClickListener((View v) -> {
            if (isDoWork) {
                if(mNewZjyUser.getUSERSESSIONID()==null || mNewZjyUser.getUSERSESSIONID().isEmpty()){
                    Tool.toast(mContext,"getUSERSESSIONID no");
                    return;
                }
                isDoWork = false;
                mProgressBar.setVisibility(View.VISIBLE);
                loadData();
            }

        });
        zjy_bt_html.setOnClickListener((View v) -> {
            String AnswHtml = mExamAnsw.getAnswHtml();
            if (mExamAnsw != null && AnswHtml != null && !AnswHtml.isEmpty()) {
                showHtml(AnswHtml);
            }

        });
        zjy_bt_zdzd.setOnClickListener((View v) -> {


        });
        mButton.performClick();
    }

    private void loadData() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                // if (AppStatus.getAll()==null||!AppStatus.getZjyzy().equals("zjyok"))return;
                mExamAnsw = doWork();
                Log.d(TAG, "run: " + mExamAnsw.getAnswStr());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        String answ = mExamAnsw.getAnswStr();
                        if (mExamAnsw != null && answ != null && !answ.isEmpty()) {
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

    private examAnsw doWork() {
        String ExamId= mExamWork.getExamId();
        ExamWorkInfo  vExamWorkInfo=newZjyMain.getExamConfirm(ExamId);
        String PaperId = vExamWorkInfo.getPaperId();
        System.out.println(PaperId);
        System.out.println(JSONObject.toJSONString( newZjyHttp.getHeader()));
        if (type == 1) {
            return newZjyMain.getPaperAnsw1(PaperId);
        } else if (type == 2) {
            return newZjyMain.getPaperAnsw2(PaperId);
        }
        return null;
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void showHtml(String answHtml) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_examansw_html_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);


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

        //初始化控件
        WebView answHtmlWeb = dialogView.findViewById(R.id.answHtml);
        WebSettings webSettings = answHtmlWeb.getSettings();
        //如果访问的页面中要与Javascript交互，则webview必须设置支持Javascript
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true); //将图片调整到适合webview的大小
        webSettings.setLoadWithOverviewMode(true); // 缩放至屏幕的大小
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        webSettings.setSupportZoom(true); //支持缩放，默认为true。是下面那个的前提。
        webSettings.setBuiltInZoomControls(true); //设置内置的缩放控件。若为false，则该WebView不可缩放
        webSettings.setDisplayZoomControls(false); //隐藏原生的缩放控件
        answHtmlWeb.loadData(answHtml,"text/html","utf-8");
        answHtmlWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                //设定加载开始的操作

            }

        });


        answHtmlWeb.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                //设定加载结束的操作

            }

        });
    }

}


