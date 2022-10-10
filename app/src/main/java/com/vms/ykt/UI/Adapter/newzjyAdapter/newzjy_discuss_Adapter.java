package com.vms.ykt.UI.Adapter.newzjyAdapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_DiscussActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_signStudentActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.SignAndQuestionStu;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;

import java.util.List;
import java.util.Objects;

public class newzjy_discuss_Adapter extends baseRecyclerAdapter<newzjy_discuss_Adapter.ViewHolder1> {

    private newzjy_DiscussActivity mActivity;
    private Context mContext;

    private String TAG = this.getClass().getSimpleName();

    private List<SignAndQuestionStu> mSignAndQuestionStuList;

    public newzjy_discuss_Adapter(List<SignAndQuestionStu> data) {
        this.mSignAndQuestionStuList = data;

    }

    public void updateData(List<SignAndQuestionStu> data) {
        this.mSignAndQuestionStuList = data;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public newzjy_discuss_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_discuss_recycler_item, parent, false);

        if (mContext == null) {
            mContext = parent.getContext();
            mActivity = (newzjy_DiscussActivity) mContext;
        }
        // 实例化viewholder
        newzjy_discuss_Adapter.ViewHolder1 viewHolder = new newzjy_discuss_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final newzjy_discuss_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        SignAndQuestionStu vSignAndQuestionStu = mSignAndQuestionStuList.get(position);

        String StuName = vSignAndQuestionStu.getStuName();
        String Contents = vSignAndQuestionStu.getContent();
        String score = vSignAndQuestionStu.getScore();
        String LoginId = vSignAndQuestionStu.getLoginId();


        holder.mTextView0.setText("成员");
        holder.mTextView1.setText("name: " + StuName);
        holder.mTextView2.setText("回答内容: " + Contents);
        holder.mTextView3.setText("score: " + score);
        holder.mTextView4.setText("LoginId: " + LoginId);


        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (vSignAndQuestionStu.getId() != null) {
                    showSetDialog(vSignAndQuestionStu);
                } else {
                    Tool.toast(mActivity, "请重新加载");
                }
            }

        });


    }


    private void showSetDialog(SignAndQuestionStu vSignAndQuestionStu) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_discuss_too_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_sckt = dialogView.findViewById(R.id.newzjy_bt_sckt);

        newzjy_bt_qd.setText("修改分数");
        newzjy_bt_sckt.setVisibility(View.INVISIBLE);
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

        newzjy_bt_qd.setOnClickListener((View view) -> {
            updateStudentSignStatus(vSignAndQuestionStu);
        });


    }

    private void updateStudentSignStatus(SignAndQuestionStu vSignAndQuestionStu) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_discuss_too_dialog_xgfs, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        Button newzjy_bt_xgfs = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_fs = dialogView.findViewById(R.id.newzjy_et_fzs);
        TextView newzjy_tv_fsts = dialogView.findViewById(R.id.newzjy_tv_fzs);


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
        newzjy_tv_fsts.setText("分数： ");
        newzjy_et_fs.setHint("5");

        newzjy_bt_xgfs.setOnClickListener((View view) -> {
            String fs = newzjy_et_fs.getText().toString();
            if (!fs.isEmpty()) {
                new Thread(() -> {

                    String resp = newZjyApi.getUpdateStudentSignStatus(fs, vSignAndQuestionStu.getId());
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mContext, vSignAndQuestionStu.getStuName() + "\n" + resp);

                    });


                }).start();
            }
        });
    }


    @Override
    public int getItemCount() {
        return mSignAndQuestionStuList == null ? 0 : mSignAndQuestionStuList.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView0, mTextView1, mTextView2, mTextView3, mTextView4;

        public ViewHolder1(View itemView) {
            super(itemView);

            mTextView0 = itemView.findViewById(R.id.typename);
            mTextView1 = itemView.findViewById(R.id.zjy_kcmc);
            mTextView2 = itemView.findViewById(R.id.zjy_bjmc);
            mTextView3 = itemView.findViewById(R.id.zjy_kt);
            mTextView4 = itemView.findViewById(R.id.zjy_hd);
        }
    }


}
