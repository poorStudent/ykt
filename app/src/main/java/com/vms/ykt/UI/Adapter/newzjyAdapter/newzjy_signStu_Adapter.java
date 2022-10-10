package com.vms.ykt.UI.Adapter.newzjyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_courseHdActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_signStudentActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.SignAndQuestionStu;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;

import java.util.List;
import java.util.Objects;

public class newzjy_signStu_Adapter extends baseRecyclerAdapter<newzjy_signStu_Adapter.ViewHolder1> {

    private newzjy_signStudentActivity mActivity;
    private Context mContext;

    private String TAG = this.getClass().getSimpleName();

    private List<SignAndQuestionStu> mSignAndQuestionStuList;

    public newzjy_signStu_Adapter(List<SignAndQuestionStu> data) {
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
    public newzjy_signStu_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_signstu_recycler_item, parent, false);

        if (mContext == null) {
            mContext = parent.getContext();
            mActivity = (newzjy_signStudentActivity) mContext;
        }
        // 实例化viewholder
        newzjy_signStu_Adapter.ViewHolder1 viewHolder = new newzjy_signStu_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final newzjy_signStu_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        SignAndQuestionStu vSignAndQuestionStu = mSignAndQuestionStuList.get(position);

        String StuName = vSignAndQuestionStu.getStuName();
        String RecordId = vSignAndQuestionStu.getId();
        String SignStatus = vSignAndQuestionStu.getSignStatus();
        String LoginId = vSignAndQuestionStu.getLoginId();


        holder.mTextView0.setText("成员");
        holder.mTextView1.setText("name: " + StuName);
        holder.mTextView2.setText("RecordId: " + RecordId);
        holder.mTextView3.setText("状态: " + SignStatus);
        holder.mTextView4.setText("LoginId: " + LoginId);



        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (vSignAndQuestionStu.getId()!=null) {
                    showSetDialog(vSignAndQuestionStu);
                }else {
                    Tool.toast(mActivity, "请重新加载");
                }
            }

        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }


    private void showSetDialog( SignAndQuestionStu vSignAndQuestionStu) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.newzjy_signstu_fragment_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_qd2 = dialogView.findViewById(R.id.newzjy_bt_qd2);
        Button newzjy_bt_qbcd = dialogView.findViewById(R.id.newzjy_bt_qbcd);
        Button newzjy_bt_qbsj = dialogView.findViewById(R.id.newzjy_bt_qbsj);
        Button newzjy_bt_qbgj = dialogView.findViewById(R.id.newzjy_bt_qbgj);
        Button newzjy_bt_qbzt = dialogView.findViewById(R.id.newzjy_bt_qbzt);
        Button newzjy_bt_qbbj = dialogView.findViewById(R.id.newzjy_bt_qbbj);
        Button newzjy_bt_qbqq = dialogView.findViewById(R.id.newzjy_bt_qbqq);

        newzjy_bt_qd.setText("签到(备用不建议用)");
        newzjy_bt_qd2.setText("签到");
        newzjy_bt_qbcd.setText("迟到");
        newzjy_bt_qbsj.setText("事假");
        newzjy_bt_qbgj.setText("公家");
        newzjy_bt_qbzt.setText("早退");
        newzjy_bt_qbbj.setText("病假");
        newzjy_bt_qbqq.setText("缺勤");

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
            new Thread(()->{
                sign(vSignAndQuestionStu);

            }).start();
        });
        newzjy_bt_qd2.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,1);
        });

        newzjy_bt_qbcd.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,2);
        });

        newzjy_bt_qbsj.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,3);
        });

        newzjy_bt_qbgj.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,4);
        });

        newzjy_bt_qbzt.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,5);
        });

        newzjy_bt_qbbj.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,6);
        });

        newzjy_bt_qbqq.setOnClickListener((View view) -> {
            patchSign(vSignAndQuestionStu,7);
        });




    }


    private void sign(SignAndQuestionStu vSignAndQuestionStu) {
      newZjyMain.Sign(mActivity, newZjyUserDao.sClassActivity,vSignAndQuestionStu);
    }

    private void patchSign(SignAndQuestionStu vSignAndQuestionStu,int type){
        new Thread(()->{
            newZjyMain.patchSign(mActivity,vSignAndQuestionStu,type);
        }).start();

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
