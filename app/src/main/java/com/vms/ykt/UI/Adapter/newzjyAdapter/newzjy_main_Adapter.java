package com.vms.ykt.UI.Adapter.newzjyAdapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_ExamWorkActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_HomeWorkActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_classRoomActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newZjy_mainActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyCourse;
import com.vms.ykt.yktStuMobile.newZJY.newZjyUser;

import java.util.List;
import java.util.Objects;

public class newzjy_main_Adapter extends baseRecyclerAdapter<newzjy_main_Adapter.ViewHolder1> {


    private List<newZjyCourse> mNewZjyCourseList;
    private Context mContext;
    private newZjy_mainActivity mActivity;
    private String TAG = this.getClass().getSimpleName();


    public newzjy_main_Adapter(List<newZjyCourse> data) {
        this.mNewZjyCourseList = data;
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
                    newZjyUserDao.sNewZjyCourse=vNewZjyCourse;
                    showSetDialog(vNewZjyCourse);
                } else {
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

        View dialogView=Tool.creatDialog(mContext,R.layout.newzjy_main_dialog);
        //初始化控件
        Button but_kt = dialogView.findViewById(R.id.zjy_bt_kt);
        Button zjy_bt_cjzb = dialogView.findViewById(R.id.zjy_bt_cjzb);
        Button zjy_bt_xggg = dialogView.findViewById(R.id.zjy_bt_xggg);
        Button zjy_bt_jkxq = dialogView.findViewById(R.id.zjy_bt_jkxq);


        Button zjy_bt_cy = dialogView.findViewById(R.id.zjy_bt_cy);
        Button zjy_bt_zy = dialogView.findViewById(R.id.zjy_bt_zy);
        Button zjy_bt_ks = dialogView.findViewById(R.id.zjy_bt_ks);
        Button zjy_bt_fjzy = dialogView.findViewById(R.id.zjy_bt_fjzy);




        but_kt.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_classRoomActivity.class);
            i.putExtra("type", 3);
            mActivity.startActivity(i);

        });

        zjy_bt_cjzb.setOnClickListener((View view) -> {
            SaveAssessment();
        });

        zjy_bt_xggg.setOnClickListener((View view) -> {
            SaveCourseNotice(vNewZjyCourse);
        });


        zjy_bt_jkxq.setOnClickListener((View view) -> {
            ModifyClassAuditStatus(vNewZjyCourse);
        });


        zjy_bt_cy.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_ExamWorkActivity.class);
            i.putExtra("type", 0);
            mActivity.startActivity(i);

        });


        zjy_bt_zy.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_ExamWorkActivity.class);
            i.putExtra("type", 1);
            mActivity.startActivity(i);

        });


        zjy_bt_ks.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_ExamWorkActivity.class);
            i.putExtra("type", 2);
            mActivity.startActivity(i);

        });


        zjy_bt_fjzy.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_HomeWorkActivity.class);
            mActivity.startActivity(i);

        });


    }


    private void SaveAssessment(){
        View dialogView=Tool.creatDialog(mContext,R.layout.newzjy_main_cjzb_dialog);

    }

    private void SaveCourseNotice(newZjyCourse vNewZjyCourse){
        View dialogView=Tool.creatDialog(mContext,R.layout.newzjy_main_xggg_dialog);
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        EditText newzjy_et_bt = dialogView.findViewById(R.id.newzjy_et_bt);
        EditText newzjy_et_nr = dialogView.findViewById(R.id.newzjy_et_nr);

        newzjy_bt_qd.setOnClickListener((view)->{
            String bt=newzjy_et_bt.getText().toString();
            String nr=newzjy_et_nr.getText().toString();
            new Thread(()->{
                String resp=newZjyApi.getSaveCourseNotice(vNewZjyCourse.getCourseId(),bt,nr);
                mActivity.runOnUiThread(()->{
                    Tool.toast(mContext,resp);
                });
            }).start();
        });
    }

    private void ModifyClassAuditStatus(newZjyCourse vNewZjyCourse){

        View dialogView=Tool.creatDialog(mContext,R.layout.newzjy_main_jkxq_dialog);
        Button newzjy_bt_yxjk = dialogView.findViewById(R.id.newzjy_bt_yxjk);
        Button newzjy_bt_jzjk = dialogView.findViewById(R.id.newzjy_bt_jzjk);
        ImageView newzjy_img_jkm = dialogView.findViewById(R.id.newzjy_img_jkm);
        newzjy_bt_yxjk.setOnClickListener((view)->{
            new Thread(()->{
                String resp=newZjyApi.getModifyClassAuditStatus(vNewZjyCourse.getClassId(),"1","0");//允许
                mActivity.runOnUiThread(()->{
                    Tool.toast(mContext,resp);
                });
            }).start();
        });
        newzjy_bt_jzjk.setOnClickListener((view)->{
            new Thread(()->{
                String resp=newZjyApi.getModifyClassAuditStatus(vNewZjyCourse.getClassId(),"0","0");//不允许
                mActivity.runOnUiThread(()->{
                    Tool.toast(mContext,resp);
                });
            }).start();
        });
        new Thread(()->{
            String resp=newZjyApi.getQrCode(vNewZjyCourse.getClassId());
            if(resp!=null){
                byte[] img=Base64.decode(resp,Base64.DEFAULT);
                Bitmap vBitmap=BitmapFactory.decodeByteArray(img, 0, img.length);
                mActivity.runOnUiThread(()->{
                    newzjy_img_jkm.setImageBitmap(vBitmap);
                });

            }
        }).start();

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
