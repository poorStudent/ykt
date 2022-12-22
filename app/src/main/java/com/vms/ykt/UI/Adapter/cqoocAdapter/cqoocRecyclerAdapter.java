package com.vms.ykt.UI.Adapter.cqoocAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.cqoocActivity.cqooc_shuakeActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.cqooc.cqoocUserDao;
import com.vms.ykt.yktStuWeb.Cqooc.cqApi;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocCourseInfo;
import com.vms.ykt.yktStuWeb.Cqooc.cqoocMain;
import com.vms.ykt.yktStuWeb.Cqooc.userInfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class cqoocRecyclerAdapter extends baseRecyclerAdapter<RecyclerView.ViewHolder> {

    List<cqoocCourseInfo> mCqoocCourseInfo;
    private Context mContext;
    private yktMainActivity mActivity;
    private userInfo mUserInfo;
    private String TAG = this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private cqoocRecyclerAdapter.OnItemClickListener onItemClickListener;

    private cqoocRecyclerAdapter.initRcView mInitRcView;

    public cqoocRecyclerAdapter(List<cqoocCourseInfo> data) {
        this.mCqoocCourseInfo = data;
        this.mUserInfo = cqoocUserDao.sUserInfo;
        Log.d(TAG, "cqoocRecyclerAdapter: " + mUserInfo.getStaytime());
    }

    public void updateData(List<cqoocCourseInfo> data) {
        this.mCqoocCourseInfo = data;
        notifyDataSetChanged();
    }

    /**
     * 添加新的Item
     */
    public void addNewItem(cqoocCourseInfo cqoocCourseInfo, int position) {
        if (mCqoocCourseInfo == null) {
            mCqoocCourseInfo = new ArrayList<>();
        }
        mCqoocCourseInfo.add(cqoocCourseInfo);
        notifyItemInserted(position);
    }

    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if (mCqoocCourseInfo == null || mCqoocCourseInfo.isEmpty()) {
            return;
        }
        mCqoocCourseInfo.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(cqoocRecyclerAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(cqoocRecyclerAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    @Override
    public int getItemViewType(int position) {

        return mCqoocCourseInfo.get(position).getType();

    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        if (mContext == null)
            mContext = parent.getContext();
        if (mActivity == null)
            mActivity = (yktMainActivity) mContext;

        View v;
        RecyclerView.ViewHolder viewHolder;
        if (viewType < 10) {
            //课
            // 实例化展示的view
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cqooc_recycler_item, parent, false);
            // 实例化viewholder
            viewHolder = new ViewHolder1(v);

            return viewHolder;
        } else {
            //头view

            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.cqooc_recycler_heard_item, parent, false);
            viewHolder = new ViewHolder2(v);
            return viewHolder;
        }


    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        // 绑定数据

        if (holder.getItemViewType() > 10) {
            String tag = "";
            switch (holder.getItemViewType()) {
                case 11:
                    tag = "-----公开课-----";
                    break;
                case 22:
                    tag = "-----在线课-----";
                    break;
                case 33:
                    tag = "-----spoc课-----";
                    break;
                case 44:
                    tag = "-----独立云班课-----";
                    break;
                default:
                    break;
            }
            ((ViewHolder2) holder).mTextView1.setText(tag);
            return;
        }


        final ViewHolder1 vHolder1 = (cqoocRecyclerAdapter.ViewHolder1) holder;

        cqoocCourseInfo vCqoocCourseInfo = mCqoocCourseInfo.get(position);
        String kcmc = vCqoocCourseInfo.getTitle();
        String xxdf = vCqoocCourseInfo.getScore();
        String jksj = vCqoocCourseInfo.getEndDate();
        String cyfs = vCqoocCourseInfo.getCourseManager();
        String tlcs = vCqoocCourseInfo.getForumNum();
        String lsmc = vCqoocCourseInfo.getClassTitle();
        String kctp = vCqoocCourseInfo.getCoursePicUrl();


        if (!Tool.isDestroy(mActivity) || vHolder1.mImageView != null || vHolder1.mImageView.getContext() != null) {
            Glide.with(vHolder1.mImageView.getContext())
                    .load(kctp)
                    .error(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(vHolder1.mImageView);
        }


        vHolder1.mTextView1.setText(kcmc);

        vHolder1.mTextView2.setText("讨论: " + tlcs + "次 ");
        vHolder1.mTextView3.setText("班级: " + lsmc);
        vHolder1.mTextView4.setText("得分: " + xxdf + "分 ");
        vHolder1.mTextView5.setText("老师: " + cyfs);
        if (jksj != null) {
            long endTime = Long.valueOf(jksj);
            jksj = Tool.parseDataTime(endTime);
            if (endTime < System.currentTimeMillis()) {
                jksj = jksj + "(已结束)";
                vHolder1.mTextView4.setText("");
                vHolder1.mTextView5.setText("各分: " + vCqoocCourseInfo.getScoreBody());
            }
        }
        vHolder1.mTextView6.setText("结课时间: " + jksj);
        if (holder.getItemViewType() != 2) return;
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                //  if (holder.mTextView6.getText().toString().contains("已结束"))return;
                if (vCqoocCourseInfo.getId() != null) {
                    cqoocUserDao.sCqoocCourseInfo = vCqoocCourseInfo;
                    showSetDialog(vCqoocCourseInfo);
                }

                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemClick(holder.itemView, pos);
                }
            }

        });

        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if (onItemClickListener != null) {
                    int pos = holder.getLayoutPosition();
                    onItemClickListener.onItemLongClick(holder.itemView, pos);
                }

                //表示此事件已经消费，不会触发单击事件
                return true;
            }
        });
    }


    private void showSetDialog(cqoocCourseInfo CourseIfno) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.ykt_cqooc_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        TextView but_shuake = dialogView.findViewById(R.id.cqooc_bt_shuke);
        TextView but_cy = dialogView.findViewById(R.id.cqooc_bt_cy);
        TextView but_zy = dialogView.findViewById(R.id.cqooc_bt_zy);
        TextView but_ks = dialogView.findViewById(R.id.cqooc_bt_ks);
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
        but_shuake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent i = new Intent(mActivity, cqooc_shuakeActivity.class);
                mActivity.startActivity(i);
            }
        });
        but_cy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        but_zy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
        but_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }


    public void initListener(final ViewHolder1 holder) {

    }

    public void pauseRequests() {
        Glide.with(mContext).pauseRequests();
    }

    ;

    @Override
    public int getItemCount() {
        return mCqoocCourseInfo == null ? 0 : mCqoocCourseInfo.size();
    }

    static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView1, mTextView2, mTextView3, mTextView4, mTextView5, mTextView6;
        ImageView mImageView;

        public ViewHolder1(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.cqooc_kctp);
            mTextView1 = itemView.findViewById(R.id.cqooc_kcmc);
            mTextView2 = itemView.findViewById(R.id.cqooc_lsmc);
            mTextView3 = itemView.findViewById(R.id.cqooc_tlcs);
            mTextView4 = itemView.findViewById(R.id.cqooc_zfs);
            mTextView5 = itemView.findViewById(R.id.cqooc_cyfs);
            mTextView6 = itemView.findViewById(R.id.cqooc_jksj);
        }
    }

    static class ViewHolder2 extends RecyclerView.ViewHolder {

        TextView mTextView1;

        public ViewHolder2(View itemView) {
            super(itemView);
            mTextView1 = itemView.findViewById(R.id.cqooc_head);

        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);

        void onItemLongClick(View view, int position);
    }

    public interface initRcView {
        String getTitle(int position);

        String getCourseId(int position);

        String getEndTime(int position);

        String getParentId(int position);
    }


}
