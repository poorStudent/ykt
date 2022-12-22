package com.vms.ykt.UI.Adapter.icveAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.icveActivity.icve_shukeActivity;
import com.vms.ykt.UI.Activity.icveActivity.icve_workExamActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.yktDao.icve.icveUserDao;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.icve.icveApiW;
import com.vms.ykt.yktStuWeb.icve.icveCourseInfo;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuWeb.icve.icveHttpW;
import com.vms.ykt.yktStuWeb.icve.icveMainW;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class icveRecyclerAdapter extends baseRecyclerAdapter<icveRecyclerAdapter.ViewHolder3> {

    List<icveCourseInfo> mIcveCourseInfos;
    private Context mContext;
    private yktMainActivity mActivity;

    private final String TAG = this.getClass().getSimpleName();

    private boolean isShowFt = false;
    /**
     * 事件回调监听
     */
    private icveRecyclerAdapter.OnItemClickListener onItemClickListener;

    private icveRecyclerAdapter.initRcView mInitRcView;

    public icveRecyclerAdapter(List<icveCourseInfo> data) {
        this.mIcveCourseInfos = data;
    }

    public void updateData(List<icveCourseInfo> data) {

        if (mIcveCourseInfos == null) this.mIcveCourseInfos = new ArrayList<>();
        this.mIcveCourseInfos.clear();
        this.mIcveCourseInfos.addAll(data);
        notifyDataSetChanged();
    }

    /**
     * 添加新的Item
     */
    public void addNewItem(icveCourseInfo icveCourseInfo, int position) {
        if (mIcveCourseInfos == null) {
            mIcveCourseInfos = new ArrayList<>();
        }
        mIcveCourseInfos.add(position, icveCourseInfo);
        notifyItemInserted(position);
    }

    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if (mIcveCourseInfos == null || mIcveCourseInfos.isEmpty()) {
            return;
        }
        mIcveCourseInfos.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(icveRecyclerAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(icveRecyclerAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder3 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.icve_recycler_item, parent, false);

        // 实例化viewholder

        if (mContext == null)
            mContext = parent.getContext();
        if (mActivity == null)
            mActivity = (yktMainActivity) mContext;

        ViewHolder3 viewHolder = new ViewHolder3(v);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder3 holder, int position) {
        // 绑定数据
        icveCourseInfo vIcveCourseInfo = mIcveCourseInfos.get(position);

        LinearLayout notwk = holder.getView(R.id.icve_notwk);

        LinearLayout iswk = holder.getView(R.id.icve_iswk);
        Log.d(TAG, "onBindViewHolder: " + vIcveCourseInfo.getType());
        if (vIcveCourseInfo.getType() == 2) {

            notwk.setVisibility(View.GONE);
            iswk.setVisibility(View.VISIBLE);
            return;
        } else {
            notwk.setVisibility(View.VISIBLE);
            iswk.setVisibility(View.GONE);
            Log.d(TAG, "onBindViewHolder: notFootView");
        }


        String kctp = vIcveCourseInfo.getCover();
        String kcmc = vIcveCourseInfo.getTitle();
        String cjsj = vIcveCourseInfo.getDatecreated();
        String xxsc = vIcveCourseInfo.getStudyhours();
        String kcjd = vIcveCourseInfo.getSchedule();


        ImageView mImageView = holder.getView(R.id.icve_kctp);
        TextView mTextView1 = holder.getView(R.id.icve_kcmc);
        TextView mTextView2 = holder.getView(R.id.icve_cjsj);
        TextView mTextView3 = holder.getView(R.id.icve_xxsc);
        TextView mTextView4 = holder.getView(R.id.icve_jd);
        ProgressBar mProgressBar = holder.getView(R.id.icve_kcjd);

        if (!Tool.isDestroy(mActivity)) {
            Glide.with(mImageView.getContext())
                    .load(kctp)
                    .error(R.drawable.ic_launcher_foreground)
                    .fitCenter()
                    .into(mImageView);
        }

        mTextView1.setText(kcmc);
        mTextView2.setText("创建时间: " + cjsj);
        if (xxsc == null) {
            mTextView3.setText("学分: " + vIcveCourseInfo.getStudyscore());
        } else {
            mTextView3.setText("学时: " + xxsc);
        }
        mTextView4.setText("进度: " + kcjd + "%");
        mProgressBar.setProgress((int) Double.parseDouble(kcjd));
        Log.d(TAG, "onBindViewHolder: Progress  " + kcjd);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (vIcveCourseInfo.getId() != null) {
                    icveUserDao.sIcveCourseInfo=vIcveCourseInfo;
                    showSetDialog(vIcveCourseInfo);
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


    private void showSetDialog(icveCourseInfo CourseIfno) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.ykt_icve_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button but_shuake = dialogView.findViewById(R.id.zjy_bt_shuke);
        Button but_wkpj = dialogView.findViewById(R.id.zjy_bt_wkpj);
        Button but_zy = dialogView.findViewById(R.id.zjy_bt_zy);
        Button but_ks = dialogView.findViewById(R.id.zjy_bt_ks);

        if (CourseIfno.getType() != 0) {
            but_zy.setEnabled(false);
            but_ks.setEnabled(false);
        } else {
            but_wkpj.setEnabled(false);
        }
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
        but_shuake.setOnClickListener((View view) -> {

                Intent i = new Intent(mActivity, icve_shukeActivity.class);

                mActivity.startActivity(i);

        });

        but_wkpj.setOnClickListener((View view) -> {
            new Thread(() -> {
                String resp = icveApiW.getMicrosAddComment(CourseIfno.getId());
                mActivity.runOnUiThread(() -> {
                    Tool.toast(mContext, resp);
                });
            }).start();

        });

        Intent i = new Intent(mActivity, icve_workExamActivity.class);

        but_zy.setOnClickListener((View view) -> {

                mActivity.startActivity(i);

        });

        but_ks.setOnClickListener((View view) -> {
                mActivity.startActivity(i);

        });
    }


    public void pauseRequests() {
        Glide.with(mContext).pauseRequests();
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @Override
    public int getItemCount() {
        return mIcveCourseInfos == null ? 0 : mIcveCourseInfos.size();
    }

    public  class ViewHolder3 extends RecyclerView.ViewHolder {


        private final SparseArray<View> sparseArray;

        public ViewHolder3(View itemView) {
            super(itemView);
            this.sparseArray = new SparseArray<>(20);
        }

        public <T extends View> T getView(int viewId) {
            View view = sparseArray.get(viewId);
            if (view == null) {
                view = itemView.findViewById(viewId);
                sparseArray.put(viewId, view);
            }
            return (T) view;
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
