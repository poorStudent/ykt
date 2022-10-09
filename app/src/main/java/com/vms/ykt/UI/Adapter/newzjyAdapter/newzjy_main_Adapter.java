package com.vms.ykt.UI.Adapter.newzjyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_classRoomActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newZjy_mainActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.List;
import java.util.Objects;

public class newzjy_main_Adapter extends baseRecyclerAdapter<newzjy_main_Adapter.ViewHolder1> {


    private List<newZjyCourse> mNewZjyCourseList;
    private newZjyUser mNewZjyUser;
    private Context mContext;
    private newZjy_mainActivity mActivity;
    private String TAG = this.getClass().getSimpleName();


    public newzjy_main_Adapter(List<newZjyCourse> data, newZjyUser userInfo) {
        this.mNewZjyCourseList = data;
        this.mNewZjyUser = userInfo;
    }

    public void updateData(List<newZjyCourse> data) {
        this.mNewZjyCourseList = data;
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public newzjy_main_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_main_recycler_item, parent, false);
        // 实例化viewholder
        if (mContext == null)
            mContext = parent.getContext();
        if (mActivity == null)
            mActivity = (newZjy_mainActivity) mContext;

        newzjy_main_Adapter.ViewHolder1 viewHolder = new newzjy_main_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final newzjy_main_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        newZjyCourse vNewZjyCourse = mNewZjyCourseList.get(position);
        String kctp = vNewZjyCourse.getClassPhoto();
        String kcmc = vNewZjyCourse.getCourseName();
        String bjmc = vNewZjyCourse.getClassName();
        String kt = vNewZjyCourse.getClassroomNum();
        String hd = vNewZjyCourse.getActivityNum();


        if (!Tool.isDestroy(mActivity) || holder.mImageView != null) {
            Glide.with(holder.mImageView.getContext())
                    .load(kctp)
                    .error(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(holder.mImageView);
        }


        holder.mTextView1.setText(kcmc);
        holder.mTextView2.setText("班级: " + bjmc);
        holder.mTextView3.setText("课堂: " + kt);
        holder.mTextView4.setText("活动: " + hd);

        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (vNewZjyCourse.getCourseId() != null) {
                    showSetDialog(vNewZjyCourse);
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


    private void showSetDialog(newZjyCourse vNewZjyCourse) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_main_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button but_kt = dialogView.findViewById(R.id.zjy_bt_kt);
        Button zjy_bt_cjzb = dialogView.findViewById(R.id.zjy_bt_cjzb);

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

        but_kt.setOnClickListener((View view) -> {
                Intent i = new Intent(mActivity, newzjy_classRoomActivity.class);
                newZjyUserDao.sNewZjyCourse = vNewZjyCourse;
                mActivity.startActivity(i);

        });

        zjy_bt_cjzb.setOnClickListener((View view) -> {

        });


    }


    public void pauseRequests() {
        Glide.with(mContext).pauseRequests();
    }


    @Override
    public int getItemCount() {
        return mNewZjyCourseList == null ? 0 : mNewZjyCourseList.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView1, mTextView2, mTextView3, mTextView4;
        ImageView mImageView;

        public ViewHolder1(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.zjy_kctp);
            mTextView1 = itemView.findViewById(R.id.zjy_kcmc);
            mTextView2 = itemView.findViewById(R.id.zjy_bjmc);
            mTextView3 = itemView.findViewById(R.id.zjy_kt);
            mTextView4 = itemView.findViewById(R.id.zjy_hd);
        }
    }


}
