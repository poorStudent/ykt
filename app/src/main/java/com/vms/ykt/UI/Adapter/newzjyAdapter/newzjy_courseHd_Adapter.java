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
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;

import java.util.List;
import java.util.Objects;

public class newzjy_courseHd_Adapter extends baseRecyclerAdapter<newzjy_courseHd_Adapter.ViewHolder1> {

    private newzjy_courseHdActivity mActivity;
    private Context mContext;

    private String TAG = this.getClass().getSimpleName();

    private List<classActivity> mClassActivityList;

    public newzjy_courseHd_Adapter(List<classActivity> data) {
        this.mClassActivityList = data;

    }

    public void updateData(List<classActivity> data) {
        this.mClassActivityList = data;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public newzjy_courseHd_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_coursehd_recycler_item, parent, false);

        if (mContext == null) {
            mContext = parent.getContext();
            mActivity = (newzjy_courseHdActivity) mContext;
        }
        // 实例化viewholder
        newzjy_courseHd_Adapter.ViewHolder1 viewHolder = new newzjy_courseHd_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final newzjy_courseHd_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        classActivity vClassActivity = mClassActivityList.get(position);

        String tn = vClassActivity.getTypeName();
        String RecordId = vClassActivity.getRecordId();
        String ExamName = vClassActivity.getExamName();
        String TypeCode = vClassActivity.getTypeCode();
        String Status = vClassActivity.getStatus();

        holder.mTextView0.setText(tn);
        holder.mTextView1.setText("RecordId: " + RecordId);
        holder.mTextView2.setText("ExamName: " + ExamName);

        holder.mTextView4.setText("TypeCode: " + TypeCode);

        String zt = "未知";
        if (Status != null && !Status.isEmpty()) {
            if (Status.contains("2")) {
                zt = "结束";
                holder.mTextView3.setTextColor(mContext.getColor(R.color.color4));
            } else if (Status.contains("1")) {
                zt = "进行中";
                holder.mTextView3.setTextColor(mContext.getColor(R.color.colorPrimary));
            }
        }
        holder.mTextView3.setText("状态: " + zt);

        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showSetDialog(vClassActivity);

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


    private void showSetDialog(classActivity vClassActivity) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_coursehd_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_sckt = dialogView.findViewById(R.id.newzjy_bt_sckt);
        Button newzjy_bt_jrkt = dialogView.findViewById(R.id.newzjy_bt_jrkt);

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
        newzjy_bt_qd.setEnabled(false);
        if (vClassActivity.getTypeName().contains("签到")) {
            newzjy_bt_qd.setEnabled(true);
            newzjy_bt_qd.setOnClickListener((View view) -> {
                new Thread(() -> {
                    sign(vClassActivity);
                }).start();

            });
        }
        newzjy_bt_sckt.setOnClickListener((View view) -> {
            new Thread(() -> {

            }).start();
        });


        newzjy_bt_jrkt.setOnClickListener((View view) -> {
            if (vClassActivity.getId() != null) {


                Intent i = new Intent(mActivity, newzjy_courseHdActivity.class);

                //i.putExtra("vClassRoom", vClassRoom);

                mActivity.startActivity(i);
            }
        });

    }


    private void sign(classActivity vActivity) {

        String name = vActivity.getTypeName();
        String RecordId = vActivity.getRecordId();
        String id = vActivity.getId();
        if (name.contains("签到")) {
            System.out.println("---" + id + " * name " + name + " * getStatus " +
                    vActivity.getStatus() + " * RecordId " + RecordId + " * DetailTypeCode " + vActivity.getDetailTypeCode());
            String resp = vActivity.getTypeName() + "\n"
                    + newZjyMain.Sign(vActivity, RecordId);
            mActivity.runOnUiThread(() -> {
                Tool.toast(mContext, resp);
            });
        }


    }


    @Override
    public int getItemCount() {
        return mClassActivityList == null ? 0 : mClassActivityList.size();
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
