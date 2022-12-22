package com.vms.ykt.UI.Adapter.moocAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.moocActivity.mooc_examActivity;
import com.vms.ykt.UI.Activity.moocActivity.mooc_shuakeActivity;
import com.vms.ykt.UI.Activity.moocActivity.mooc_testActivity;
import com.vms.ykt.UI.Activity.moocActivity.mooc_workActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.yktDao.mooc.moocUserDao;
import com.vms.ykt.yktDao.zjy.zjyUserDao;
import com.vms.ykt.yktStuMobile.mooc.DiscussInfo;
import com.vms.ykt.yktStuMobile.mooc.DiscussTopInfo;
import com.vms.ykt.yktStuMobile.mooc.courseInfoForTeach;
import com.vms.ykt.yktStuMobile.mooc.moocApi;
import com.vms.ykt.yktStuMobile.mooc.moocCourseInfo;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.mooc.moocMianM;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class moocRecyclerAdapter extends baseRecyclerAdapter<moocRecyclerAdapter.ViewHolder2> {

    List<moocCourseInfo> mMoocCourseInfos;
    List<courseInfoForTeach> mForTeachList;
    private Context mContext;
    private yktMainActivity mActivity;
    private zjyUser mZjyUser;

    private String TAG = this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private moocRecyclerAdapter.OnItemClickListener onItemClickListener;

    private moocRecyclerAdapter.initRcView mInitRcView;

    public moocRecyclerAdapter(List<courseInfoForTeach> forTeachList, List<moocCourseInfo> data) {
        this.mMoocCourseInfos = data;
        this.mForTeachList = forTeachList;
        this.mZjyUser = zjyUserDao.sZjyUser;
    }

    public void updateData(List<moocCourseInfo> data) {
        if (mMoocCourseInfos != null) {
            mMoocCourseInfos.clear();
            mMoocCourseInfos.addAll(data);
        } else {
            mMoocCourseInfos = new ArrayList<>();
            mMoocCourseInfos.addAll(data);
        }
        notifyDataSetChanged();
    }

    /**
     * 添加新的Item
     */
    public void addNewItem(moocCourseInfo moocCourseInfo, int position) {
        if (mMoocCourseInfos == null) {
            mMoocCourseInfos = new ArrayList<>();
        }
        mMoocCourseInfos.add(position, moocCourseInfo);
        notifyItemInserted(position);
    }

    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if (mMoocCourseInfos == null || mMoocCourseInfos.isEmpty()) {
            return;
        }
        mMoocCourseInfos.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(moocRecyclerAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(moocRecyclerAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder2 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.mooc_recycler_item, parent, false);
        // 实例化viewholder

        if (mContext==null)
            mContext=parent.getContext();
        if (mActivity==null)
            mActivity=(yktMainActivity)mContext;

        ViewHolder2 viewHolder = new ViewHolder2(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder2 holder, int position) {
        // 绑定数据

        moocCourseInfo vMoocCourseInfo = mMoocCourseInfos.get(position);
        String kcmc = vMoocCourseInfo.getCourseName();
        String xxrs = vMoocCourseInfo.getStudentCount();
        String xxm = vMoocCourseInfo.getSchoolName();
        String kcjd = vMoocCourseInfo.getProcess();
        String kctp = vMoocCourseInfo.getThumbnail();


        if (!Tool.isDestroy(mActivity) || holder.mImageView != null || holder.mImageView.getContext() != null) {
            Glide.with(holder.mImageView.getContext())
                    .load(kctp)
                    .error(R.drawable.ic_launcher_background)
                    .fitCenter()
                    .into(holder.mImageView);
        }


        holder.mTextView1.setText(kcmc);
        holder.mTextView2.setText("学习人数: " + xxrs);
        holder.mTextView3.setText("学校名: " + xxm);
        holder.mTextView4.setText("进度: " + kcjd + "%");
        holder.mProgressBar.setProgress(Integer.valueOf(kcjd));
        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (vMoocCourseInfo.getCourseOpenId()!= null) {
                    moocUserDao.sMoocCourseInfo=vMoocCourseInfo;
                    showSetDialog(vMoocCourseInfo);
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


    private void showSetDialog(moocCourseInfo CourseIfno) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.ykt_mooc_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框mooc
        setDeBugDialog.setView(dialogView);
        //初始化控件
        TextView but_shuake = dialogView.findViewById(R.id.mooc_bt_shuke);
        TextView but_khpj = dialogView.findViewById(R.id.mooc_bt_tqlcz);
        TextView but_cy = dialogView.findViewById(R.id.mooc_bt_cy);
        TextView but_zy = dialogView.findViewById(R.id.mooc_bt_zy);
        TextView but_ks = dialogView.findViewById(R.id.mooc_bt_ks);
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

        moocMianM.getTeachId3(mZjyUser, CourseIfno, mForTeachList);

        but_shuake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    Intent i = new Intent(mActivity, mooc_shuakeActivity.class);
                    mActivity.startActivity(i);
            }
        });
        but_khpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    new Thread(() -> {
                        tlqpl(mZjyUser, CourseIfno);
                    }).start();

            }
        });
        but_cy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(mActivity, mooc_testActivity.class);
                    Log.d(TAG, "onClick: " + mZjyUser.getCookie());
                    mActivity.startActivity(i);
            }
        });
        but_zy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(mActivity, mooc_workActivity.class);
                    mActivity.startActivity(i);

            }
        });
        but_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Intent i = new Intent(mActivity, mooc_examActivity.class);
                    mActivity.startActivity(i);
            }
        });
    }


    private void tlqpl(zjyUser zjyUser, moocCourseInfo CourseInfo) {
        ArrayList<DiscussInfo> varDiscusslist = moocApi.getDiscuss(zjyUser, CourseInfo);

        if (varDiscusslist.size() != 0) {
            for (DiscussInfo varDiscussInfo : varDiscusslist) {
                String finalResp1 = moocApi.getaddPublishTopic(zjyUser, CourseInfo, "无", "无", varDiscussInfo.getId());
                mActivity.runOnUiThread(() -> {

                    Tool.toast(mContext, "-" + varDiscussInfo.getTitle());

                    Tool.toast(mContext, finalResp1);//讨论区添加信息
                });
                ArrayList<DiscussTopInfo> varDiscussTopInfos = moocApi.getDiscussTopicNew1(zjyUser, CourseInfo, varDiscussInfo.getId());
                int ct = 1;
                for (DiscussTopInfo varDiscussTopInfo : varDiscussTopInfos) {
                    String finalResp =moocApi.getaddTopicReplyb(zjyUser, CourseInfo, varDiscussTopInfo.getTopicId(), "无");
                    Tool.waitTime(500);
                    mActivity.runOnUiThread(() -> {
                        Tool.toast(mContext, "--" + varDiscussTopInfo.getDisplayName() + " *-" + varDiscussTopInfo.getTitle());
                        //System.out.println(moocApi.deleteTopic(varDiscussTopInfo.getUserId(), varDiscussTopInfo.getTopicId()));
                        Tool.toast(mContext, finalResp);//讨论区信息评价
                    });
                    if (ct == 10) break;
                    ct++;
                }
            }
        }
    }

    public void initListener(final ViewHolder2 holder) {

    }

    public void pauseRequests() {
        Glide.with(mContext).pauseRequests();
    }

    ;

    @Override
    public int getItemCount() {
        return mMoocCourseInfos == null ? 0 : mMoocCourseInfos.size();
    }

    public static class ViewHolder2 extends RecyclerView.ViewHolder {

        private final SparseArray<View> sparseArray;

        TextView mTextView1, mTextView2, mTextView3, mTextView4;
        ProgressBar mProgressBar;
        ImageView mImageView;

        public ViewHolder2(View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.mooc_kctp);
            mTextView1 = itemView.findViewById(R.id.mooc_kcmc);
            mTextView2 = itemView.findViewById(R.id.mooc_xxrs);
            mTextView3 = itemView.findViewById(R.id.mooc_xxm);
            mTextView4 = itemView.findViewById(R.id.mooc_jd);
            mProgressBar = itemView.findViewById(R.id.mooc_kcjd);
            this.sparseArray = new SparseArray<>(8);
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
