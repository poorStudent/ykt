package com.vms.ykt.UI.Adapter.zjyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_examActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_keHouActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_shukeActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_workActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.UI.yktMainActivity;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class zjyRecyclerAdapter extends baseRecyclerAdapter<zjyRecyclerAdapter.ViewHolder1> {

    List<zjyCourseIfno> mZjyAllCourseInfos;
    private final zjyUser mZjyUser;
    private Context mContext;
    private yktMainActivity mActivity;

    private String TAG=this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private zjyRecyclerAdapter.OnItemClickListener onItemClickListener;

    private zjyRecyclerAdapter.initRcView mInitRcView;

    private final zjyApiW mZjyApiW;
    private final zjyMainW mZjyMainW;

    public zjyRecyclerAdapter(List<zjyCourseIfno> data, zjyUser zjyUsers, zjyMainW zjyMainW, zjyApiW zjyApiW) {
        this.mZjyAllCourseInfos = data;
        this.mZjyUser=zjyUsers;
        this.mZjyMainW=zjyMainW;
        this.mZjyApiW=zjyApiW;
    }

    public void updateData(List<zjyCourseIfno> data) {

        if (mZjyAllCourseInfos!=null){
            mZjyAllCourseInfos.clear();
            mZjyAllCourseInfos.addAll(data);
        }else {
            mZjyAllCourseInfos=new ArrayList<>();
            mZjyAllCourseInfos.addAll(data);
        }

        notifyDataSetChanged();
    }

    /**
     * 添加新的Item
     */
    public void addNewItem(zjyCourseIfno zjyCourseIfno, int position) {
        if(mZjyAllCourseInfos == null) {
            mZjyAllCourseInfos=new ArrayList<>();
        }
        mZjyAllCourseInfos.add(position,zjyCourseIfno);
        notifyItemInserted(position);
    }

    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if(mZjyAllCourseInfos == null || mZjyAllCourseInfos.isEmpty()) {
            return;
        }
        mZjyAllCourseInfos.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(zjyRecyclerAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(zjyRecyclerAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zjy_recycler_item, parent, false);
        // 实例化viewholder
        if (mContext==null)
        mContext=parent.getContext();
        if (mActivity==null)
        mActivity=(yktMainActivity)mContext;

        ViewHolder1 viewHolder = new ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final ViewHolder1 holder, int position) {
        // 绑定数据

        zjyCourseIfno vZjyCourseIfno = mZjyAllCourseInfos.get(position);
        String kcmc= vZjyCourseIfno.getCourseName();
        String lsmc= vZjyCourseIfno.getMainTeacherName();
        String bjmc= vZjyCourseIfno.getOpenClassName();
        String kcjd= vZjyCourseIfno.getProcess();
        String kctp= vZjyCourseIfno.getThumbnail();


        if (!Tool.isDestroy(mActivity)||holder.mImageView!=null||holder.mImageView.getContext()!=null) {
        Glide.with(holder.mImageView.getContext())
                .load(kctp)
                .error(R.drawable.ic_launcher_background)
                .fitCenter()
                .into(holder.mImageView);
        }


        holder.mTextView1.setText(kcmc);
        holder.mTextView2.setText("老师: "+lsmc);
        holder.mTextView3.setText("班级: "+bjmc);
        holder.mTextView4.setText("进度: "+kcjd+"%");
        holder.mProgressBar.setProgress(Integer.valueOf(kcjd));
        Log.d(TAG, "onBindViewHolder: ");
            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                showSetDialog(mZjyAllCourseInfos.get(position));
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


    private void showSetDialog(zjyCourseIfno CourseIfno) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.ykt_zjy_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        TextView but_shuake = dialogView.findViewById(R.id.zjy_bt_shuke);
        TextView but_khpj = dialogView.findViewById(R.id.zjy_bt_khpj);
        TextView but_zy = dialogView.findViewById(R.id.zjy_bt_zy);
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
        but_shuake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CourseIfno.getCourseOpenId()!=null) {
                    Intent i = new Intent(mActivity, zjy_shukeActivity.class);
                    i.putExtra("Course", CourseIfno);
                    i.putExtra("ZjyUser", mZjyUser);

                    mActivity.startActivity(i);
                }
            }
        });
        but_khpj.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CourseIfno.getCourseOpenId()!=null) {
                    Intent i = new Intent(mActivity, zjy_keHouActivity.class);
                    i.putExtra("Course", CourseIfno);
                    i.putExtra("ZjyUser", mZjyUser);
                    i.putExtra("mZjyApiW", mZjyApiW);
                    i.putExtra("mZjyMainW", mZjyMainW);
                    mActivity.startActivity(i);
                }
            }
        });
        but_zy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CourseIfno.getCourseOpenId()!=null) {
                    Intent i = new Intent(mActivity, zjy_workActivity.class);
                    i.putExtra("Course", CourseIfno);
                    i.putExtra("ZjyUser", mZjyUser);
                    i.putExtra("mZjyApiW", mZjyApiW);
                    i.putExtra("mZjyMainW", mZjyMainW);
                    mActivity.startActivity(i);
                }
            }
        });
        but_ks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (CourseIfno.getCourseOpenId()!=null) {
                    Intent i = new Intent(mActivity, zjy_examActivity.class);
                    i.putExtra("Course", CourseIfno);
                    i.putExtra("ZjyUser", mZjyUser);
                    i.putExtra("mZjyApiW", mZjyApiW);
                    i.putExtra("mZjyMainW", mZjyMainW);
                    mActivity.startActivity(i);
                }
            }
        });
    }

    public void initListener(final ViewHolder1 holder){

    }

    public void pauseRequests(){
        Glide.with(mContext).pauseRequests();
    };

    @Override
    public int getItemCount() {
        return mZjyAllCourseInfos == null ? 0 : mZjyAllCourseInfos.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView1,mTextView2,mTextView3,mTextView4;
        ProgressBar mProgressBar;
        ImageView mImageView;

        public ViewHolder1(View itemView) {
            super(itemView);
            mImageView=itemView.findViewById(R.id.zjy_kctp);
            mTextView1=itemView.findViewById(R.id.zjy_kcmc);
            mTextView2=itemView.findViewById(R.id.zjy_lsmc);
            mTextView3=itemView.findViewById(R.id.zjy_bjmc);
            mTextView4=itemView.findViewById(R.id.zjy_jd);
            mProgressBar = itemView.findViewById(R.id.zjy_kcjd);
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
