package com.vms.ykt.UI.Adapter.newzjyAdapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_ExamWorkActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_ExamWorkAnswActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_classRoomActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_courseHdActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.ExamWork;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;

import java.util.List;
import java.util.Objects;

public class newzjy_examWork_Adapter extends baseRecyclerAdapter<newzjy_examWork_Adapter.ViewHolder1> {

    private newzjy_ExamWorkActivity mActivity;
    private Context mContext;

    private String TAG = this.getClass().getSimpleName();

    private List<ExamWork> mExamWorkList;
    private int type = 4;
    private String typeName;

    public newzjy_examWork_Adapter(List<ExamWork> data) {
        this.mExamWorkList = data;
    }

    private void initData() {
        switch (type) {
            case 0:
                typeName = "测验";
                break;
            case 1:
                typeName = "作业";
                break;
            case 2:
                typeName = "考试";
                break;
            case 3://附件作业
                typeName = "附件";
                break;
            default:
                break;
        }
    }

    public void updateData(List<ExamWork> data) {
        this.mExamWorkList = data;
        notifyDataSetChanged();
    }

    public void setType(int type) {
        this.type = type;
        initData();
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public newzjy_examWork_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_classroom_recycler_item, parent, false);

        if (mContext == null) {
            mContext = parent.getContext();
            mActivity = (newzjy_ExamWorkActivity) mContext;
        }
        // 实例化viewholder
        newzjy_examWork_Adapter.ViewHolder1 viewHolder = new newzjy_examWork_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final newzjy_examWork_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        holder.mTextView0.setText(typeName);
        holder.mTextView3.setTextColor(mContext.getColor(R.color.color4));
        ExamWork vExamWork = mExamWorkList.get(position);


        String title = vExamWork.getExamName();
        String zt = vExamWork.getExamStatus();
        String Exam_num = vExamWork.getExam_num();
        holder.mTextView1.setText(title + " " + Exam_num);
        holder.mTextView2.setText(vExamWork.getExam_general_sub());
        holder.mTextView4.setText("分数: " + vExamWork.getMy_score());
        holder.mTextView3.setText("状态: " + zt);

        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {


                if (type != 3 && vExamWork.getExamId() != null) {
                    newZjyUserDao.sExamWork = vExamWork;
                    showSetDialog(vExamWork);
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


    private void showSetDialog(ExamWork vExamWork) {

        View dialogView = Tool.creatDialog(mContext,R.layout.newzjy_examwork_dialog);

        //初始化控件
        Button newzjy_bt_da1 = dialogView.findViewById(R.id.newzjy_bt_da1);
        Button newzjy_bt_da2 = dialogView.findViewById(R.id.newzjy_bt_da2);
        Button newzjy_bt_gf = dialogView.findViewById(R.id.newzjy_bt_gf);
        Button newzjy_bt_sz = dialogView.findViewById(R.id.newzjy_bt_sz);
        Button newzjy_bt_xq = dialogView.findViewById(R.id.newzjy_bt_xq);

        newzjy_bt_da1.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_ExamWorkAnswActivity.class);
            i.putExtra("type", 1);
            mActivity.startActivity(i);
        });

        newzjy_bt_da2.setOnClickListener((View view) -> {
            Intent i = new Intent(mActivity, newzjy_ExamWorkAnswActivity.class);
            i.putExtra("type", 2);
            mActivity.startActivity(i);
        });

    }


    @Override
    public int getItemCount() {
        return mExamWorkList == null ? 0 : mExamWorkList.size();
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
