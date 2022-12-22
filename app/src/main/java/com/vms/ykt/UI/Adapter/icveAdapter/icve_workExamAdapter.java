package com.vms.ykt.UI.Adapter.icveAdapter;

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
import com.vms.ykt.UI.Activity.icveActivity.icve_AnswActivity;
import com.vms.ykt.UI.Activity.icveActivity.icve_DoAnswActivity;
import com.vms.ykt.UI.Activity.icveActivity.icve_workExamActivity;

import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.yktStuWeb.icve.workExamListInfo;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class icve_workExamAdapter extends baseRecyclerAdapter<icve_workExamAdapter.ViewHolder1> {

    List<workExamListInfo> mWorkExamListInfos;
    private icve_workExamActivity mActivity;

    private String TAG=this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private icve_workExamAdapter.OnItemClickListener onItemClickListener;

    private icve_workExamAdapter.initRcView mInitRcView;

    public icve_workExamAdapter(List<workExamListInfo> data) {
        this.mWorkExamListInfos = data;
 
    }

    public void updateData(List<workExamListInfo> data) {

        if (mWorkExamListInfos !=null){
            mWorkExamListInfos.clear();
            mWorkExamListInfos.addAll(data);
        }else {
            mWorkExamListInfos =new ArrayList<>();
            mWorkExamListInfos.addAll(data);
        }

        notifyDataSetChanged();
    }


    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if(mWorkExamListInfos == null || mWorkExamListInfos.isEmpty()) {
            return;
        }
        mWorkExamListInfos.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(icve_workExamAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(icve_workExamAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.icve_workexam_recycler_item, parent, false);
        // 实例化viewholder

        if(mActivity==null)
        mActivity=(icve_workExamActivity) parent.getContext();

        ViewHolder1 viewHolder = new ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final ViewHolder1 holder, int position) {
        // 绑定数据

       final workExamListInfo vWorkExamListInfo = mWorkExamListInfos.get(position);
        String zymc=vWorkExamListInfo.getTitle();
        holder.mTextView0.setText(zymc);
        holder.mTextView1.setText("EndDate："+vWorkExamListInfo.getEndDate());
        holder.mTextView3.setText("state："+vWorkExamListInfo.getStatus());
        holder.mTextView2.setText("TotalScore："+vWorkExamListInfo.getTotalScore());
        holder.mTextView4.setText("LimitTime："+vWorkExamListInfo.getLimitTime());
        if(vWorkExamListInfo.getType()==2){

            holder.mTextView5.setText("考试 ");
        }
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (!vWorkExamListInfo.getId().isEmpty()){
                    showSetDialog(vWorkExamListInfo);
                }
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

    private void showSetDialog(workExamListInfo vWorkExamListInfo) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mActivity);
        //获取界面
        View dialogView = LayoutInflater.from(mActivity).inflate(R.layout.icve_work_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件

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
        but_ks.setOnClickListener((View view)-> {
            Intent i = new Intent(mActivity, icve_DoAnswActivity.class);
            i.putExtra("flag", "1");
            i.putExtra("Workflag", "1");
            mActivity.startActivity(i);

        });
        but_sjd.setOnClickListener((View view)-> {
            Intent i = new Intent(mActivity, icve_AnswActivity.class);
            i.putExtra("flag", "1");
            i.putExtra("Workflag", "2");
            mActivity.startActivity(i);

        });




    }

    public void initListener(final ViewHolder1 holder){

    }


    @Override
    public int getItemCount() {
        return mWorkExamListInfos == null ? 0 : mWorkExamListInfos.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView0, mTextView1,mTextView2,mTextView3,mTextView4, mTextView5;


        public ViewHolder1(View itemView) {
            super(itemView);

            mTextView0=itemView.findViewById(R.id.zjy_work_zymc);
            mTextView1=itemView.findViewById(R.id.zjy_work_zyms);
            mTextView2=itemView.findViewById(R.id.zjy_work_zysj);
            mTextView3=itemView.findViewById(R.id.zjy_work_zycs);
            mTextView4=itemView.findViewById(R.id.zjy_work_zylx);
            mTextView5 =itemView.findViewById(R.id.icve_work_exam);
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
