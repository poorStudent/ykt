package com.vms.ykt.UI.Adapter.zjyAdapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_DayTeachActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_courseHdActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.util.ArrayList;
import java.util.List;

public class zjy_dayTeachAdapter extends baseRecyclerAdapter<zjy_dayTeachAdapter.ViewHolder1> {

    List<zjyTeachInfo> mDayTeachInfo;
    private zjy_DayTeachActivity mActivity;

    private String TAG=this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private zjy_dayTeachAdapter.OnItemClickListener onItemClickListener;

    private zjy_dayTeachAdapter.initRcView mInitRcView;

    public zjy_dayTeachAdapter(List<zjyTeachInfo> data) {
        this.mDayTeachInfo = data;
    }

    public void updateData(List<zjyTeachInfo> data) {

        if (mDayTeachInfo !=null){
            mDayTeachInfo.clear();
            mDayTeachInfo.addAll(data);
        }else {
            mDayTeachInfo =new ArrayList<>();
            mDayTeachInfo.addAll(data);
        }

        notifyDataSetChanged();
    }


    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if(mDayTeachInfo == null || mDayTeachInfo.isEmpty()) {
            return;
        }
        mDayTeachInfo.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(zjy_dayTeachAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(zjy_dayTeachAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zjy_dayteach_recycler_item, parent, false);
        // 实例化viewholder

        if(mActivity==null)
        mActivity=(zjy_DayTeachActivity) parent.getContext();

        ViewHolder1 viewHolder = new ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final ViewHolder1 holder, int position) {
        // 绑定数据

        zjyTeachInfo vZjyTeachInfo = mDayTeachInfo.get(position);
        String zymc=vZjyTeachInfo.getCourseName();
        holder.mTextView0.setText(zymc);
        holder.mTextView1.setText("Title："+vZjyTeachInfo.getTitle());
        holder.mTextView2.setText("班级："+vZjyTeachInfo.getClassName());
        holder.mTextView3.setText("teachDate："+vZjyTeachInfo.getTeachDate());
        holder.mTextView4.setText("state："+vZjyTeachInfo.getState());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (vZjyTeachInfo.getId()!=null) {
                    zjyUserDao.sZjyTeachInfo = vZjyTeachInfo;
                    Intent i = new Intent(mActivity, zjy_courseHdActivity.class);
                    mActivity.startActivity(i);
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


    public void initListener(final ViewHolder1 holder){

    }


    @Override
    public int getItemCount() {
        return mDayTeachInfo == null ? 0 : mDayTeachInfo.size();
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
