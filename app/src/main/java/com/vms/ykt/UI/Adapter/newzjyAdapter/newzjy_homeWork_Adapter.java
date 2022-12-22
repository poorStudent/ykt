package com.vms.ykt.UI.Adapter.newzjyAdapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_ExamWorkActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_ExamWorkAnswActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_HomeWorkActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.UI.LoginActivity;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ExamWork;
import com.vms.ykt.yktStuMobile.newZJY.Homework;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;

import java.util.List;
import java.util.Objects;

import Help.NetworkVerHelp;

public class newzjy_homeWork_Adapter extends baseRecyclerAdapter<newzjy_homeWork_Adapter.ViewHolder1> {

    private newzjy_HomeWorkActivity mActivity;
    private Context mContext;

    private String TAG = this.getClass().getSimpleName();

    private List<Homework> mHomeworkList;
    private int type = 4;
    private String typeName;

    public newzjy_homeWork_Adapter(List<Homework> data) {
        this.mHomeworkList = data;
        initData();
    }

    private void initData() {
        typeName = "附件";
    }

    public void updateData(List<Homework> data) {
        this.mHomeworkList = data;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public newzjy_homeWork_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_classroom_recycler_item, parent, false);

        if (mContext == null) {
            mContext = parent.getContext();
            mActivity = (newzjy_HomeWorkActivity) mContext;
        }
        // 实例化viewholder
        newzjy_homeWork_Adapter.ViewHolder1 viewHolder = new newzjy_homeWork_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final newzjy_homeWork_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        holder.mTextView0.setText(typeName);
        holder.mTextView3.setTextColor(mContext.getColor(R.color.color4));
        Homework vHomework = mHomeworkList.get(position);

        String title = vHomework.getTitle();
        String zt = vHomework.getHwStatus();
        String Exam_num = vHomework.getEndDate();
        holder.mTextView1.setText(title + " " + Exam_num);
        holder.mTextView2.setText(vHomework.getNote());
        holder.mTextView4.setText("分数: " + vHomework.getTotalScore());
        holder.mTextView3.setText("状态: " + zt);

        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                if (vHomework.getHwId() != null) {
                    newZjyUserDao.sHomework = vHomework;
                    showSetDialog(vHomework);
                } else {

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


    private void showSetDialog(Homework vHomework) {

        View dialogView=Tool.creatDialog(mContext, R.layout.newzjy_homework_dialog);


        Button newzjy_bt_gf = dialogView.findViewById(R.id.newzjy_bt_gf);
        Button newzjy_bt_sz = dialogView.findViewById(R.id.newzjy_bt_sz);
        Button newzjy_bt_xq = dialogView.findViewById(R.id.newzjy_bt_xq);


        newzjy_bt_gf.setOnClickListener((View view) -> {
            if(!vHomework.getHwStatus().contains("1")){
                gf(vHomework.getHwId());
            };
        });

        newzjy_bt_sz.setOnClickListener((View view) -> {

        });
        newzjy_bt_xq.setOnClickListener((View view) -> {

        });

    }


    private void gf(String hwid){
        View dialogView=Tool.creatDialog(mContext,R.layout.newzjy_hwork_dialog_xgfs);
        LinearLayoutCompat linearLayout = dialogView.findViewById(R.id.linearLayout);
        TextView newzjy_tv_fzs = dialogView.findViewById(R.id.newzjy_tv_fzs);
        newzjy_tv_fzs.setText("分数");
        EditText newzjy_et_fzs = dialogView.findViewById(R.id.newzjy_et_fzs);
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);

        newzjy_bt_qd.setOnClickListener((view)->{
            String fs=newzjy_et_fzs.getText().toString();
            if (fs.isEmpty())return;
            new Thread(()->{
                String hsid = newZjyApi.getHwStudent(hwid);
                Log.d(TAG, "gf: "+hsid);
                String resp=newZjyMain.getSaveCheckHomework(newZjyUserDao.sNewZjyUser.getOtNewZjyUser(),hsid,fs,"","0");
                ((Activity)mContext).runOnUiThread(()->{
                    Tool.toast(mContext,resp);
                });
                Log.d(TAG, "gf: "+resp);
            }).start();

        });

    }

    @Override
    public int getItemCount() {
        return mHomeworkList == null ? 0 : mHomeworkList.size();
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
