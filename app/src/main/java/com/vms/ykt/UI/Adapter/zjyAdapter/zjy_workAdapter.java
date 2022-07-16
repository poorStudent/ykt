package com.vms.ykt.UI.Adapter.zjyAdapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import com.vms.ykt.R;

import com.vms.ykt.UI.Activity.zjyActivity.zjy_examDoActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_workActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_workAnswActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.yktStuMobile.zjy.HomeworkInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class zjy_workAdapter extends baseRecyclerAdapter<zjy_workAdapter.ViewHolder1> {

    private final zjyCourseIfno mCourseIfno;
    List<HomeworkInfo> mHomeworkInfoList;
    private zjyUser mZjyUser;
    private zjy_workActivity mActivity;

    private String TAG=this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private zjy_workAdapter.OnItemClickListener onItemClickListener;

    private zjy_workAdapter.initRcView mInitRcView;

    public zjy_workAdapter(List<HomeworkInfo> data, zjyUser zjyUsers,zjyCourseIfno courseIfno) {
        this.mHomeworkInfoList = data;
        this.mCourseIfno=courseIfno;
        this.mZjyUser=zjyUsers;
    }

    public void updateData(List<HomeworkInfo> data) {

        if (mHomeworkInfoList!=null){
            mHomeworkInfoList.clear();
            mHomeworkInfoList.addAll(data);
        }else {
            mHomeworkInfoList=new ArrayList<>();
            mHomeworkInfoList.addAll(data);
        }

        notifyDataSetChanged();
    }

    /**
     * 添加新的Item
     */
    public void addNewItem(HomeworkInfo HomeworkInfo, int position) {
        if(mHomeworkInfoList == null) {
            mHomeworkInfoList=new ArrayList<>();
        }
        mHomeworkInfoList.add(position,HomeworkInfo);
        notifyItemInserted(position);
    }

    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if(mHomeworkInfoList == null || mHomeworkInfoList.isEmpty()) {
            return;
        }
        mHomeworkInfoList.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(zjy_workAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(zjy_workAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zjy_work_recycler_item, parent, false);
        // 实例化viewholder

if (mActivity==null)
        mActivity=(zjy_workActivity) parent.getContext();

        ViewHolder1 viewHolder = new ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final ViewHolder1 holder, int position) {
        // 绑定数据

        HomeworkInfo vHomeworkInfo = mHomeworkInfoList.get(position);
        String zymc=vHomeworkInfo.getTitle();
        holder.mTextView0.setText(zymc);
        String zysj=vHomeworkInfo.getStartDate()+"-"+vHomeworkInfo.getEndDate();
        holder.mTextView1.setText("描述："+vHomeworkInfo.getRemark());
        holder.mTextView2.setText("时间："+zysj);
        holder.mTextView3.setText("次数："+vHomeworkInfo.getStuAnwerHomeworkCount()+"/"+vHomeworkInfo.getReplyCount());
        String zylx=vHomeworkInfo.getHomeworkType().contains("1")?"题库":"其他";
        holder.mTextView4.setText("类型："+zylx);
        holder.mTextView5.setText(vHomeworkInfo.getStuHomeworkState());
        Log.d(TAG, "onBindViewHolder: ");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                showSetDialog(mHomeworkInfoList.get(position));
                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }

        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }

                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }


    private void showSetDialog(HomeworkInfo HomeworkInfo) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.zjy_work_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

        TextView but_zy = dialogView.findViewById(R.id.zjy_bt_zy);
        TextView but_sjd = dialogView.findViewById(R.id.zjy_bt_sjd);
        TextView but_ks = dialogView.findViewById(R.id.zjy_bt_ks);
        //取消点击外部消失弹窗
        setDeBugDialog.setCancelable(true);
        //创建AlertDiaLog
        setDeBugDialog.create();
        //AlertDiaLog显示
        final AlertDialog customAlert = setDeBugDialog.show();
        //设置AlertDiaLog宽高属性
        WindowManager.LayoutParams params = Objects.requireNonNull(customAlert.getWindow()).getAttributes();
        params.width = 900;
        params.height = 850 ;
        customAlert.getWindow().setAttributes(params);
        // 移除dialog的decorview背景色
        // Objects.requireNonNull(customAlert.getWindow()).getDecorView().setBackground(null);
        //设置自定义界面的点击事件逻辑
        but_zy.setOnClickListener((View view)-> {
            Intent i = new Intent(mActivity, zjy_workAnswActivity.class);
            i.putExtra("Course", mCourseIfno);
            i.putExtra("ZjyUser", mZjyUser);
            i.putExtra("HomeworkInfo", HomeworkInfo);
            i.putExtra("flag", "1");
            mActivity.startActivity(i);

        });
        but_sjd.setOnClickListener((View view)-> {
            Intent i = new Intent(mActivity, zjy_workAnswActivity.class);
            i.putExtra("Course", mCourseIfno);
            i.putExtra("ZjyUser", mZjyUser);
            i.putExtra("HomeworkInfo", HomeworkInfo);
            i.putExtra("flag", "0");
            mActivity.startActivity(i);

        });

        but_ks.setOnClickListener((View view) ->{
                    Intent i = new Intent(mActivity, zjy_examDoActivity.class);
                    i.putExtra("Course", mCourseIfno);
                    i.putExtra("ZjyUser", mZjyUser);
                    i.putExtra("HomeworkInfo", HomeworkInfo);
                    mActivity.startActivity(i);
                }
        );



    }

    public void initListener(final ViewHolder1 holder){

    }


    @Override
    public int getItemCount() {
        return mHomeworkInfoList == null ? 0 : mHomeworkInfoList.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView0, mTextView1,mTextView2,mTextView3,mTextView4,mTextView5;


        public ViewHolder1(View itemView) {
            super(itemView);
            mTextView0=itemView.findViewById(R.id.zjy_work_zymc);
            mTextView1=itemView.findViewById(R.id.zjy_work_zyms);
            mTextView2=itemView.findViewById(R.id.zjy_work_zysj);
            mTextView3=itemView.findViewById(R.id.zjy_work_zycs);
            mTextView4=itemView.findViewById(R.id.zjy_work_zylx);
            mTextView5=itemView.findViewById(R.id.zjy_work_zyzt);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
        void onItemLongClick(View view, int position);
    }

    public interface initRcView{
        String getTitle(int position);
        String getCourseId(int position);
        String getEndTime(int position);
        String getParentId(int position);
    }



}
