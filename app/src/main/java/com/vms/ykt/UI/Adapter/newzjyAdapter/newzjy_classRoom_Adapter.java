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
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_classRoomActivity;
import com.vms.ykt.UI.Activity.newZjyActivity.newzjy_courseHdActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktDao.newZjy.newZjyUserDao;
import com.vms.ykt.yktStuMobile.newZJY.ClassRoom;
import com.vms.ykt.yktStuMobile.newZJY.classActivity;
import com.vms.ykt.yktStuMobile.newZJY.newZjyApi;
import com.vms.ykt.yktStuMobile.newZJY.newZjyMain;

import java.util.List;
import java.util.Objects;

public class newzjy_classRoom_Adapter extends baseRecyclerAdapter<newzjy_classRoom_Adapter.ViewHolder1> {

    private newzjy_classRoomActivity mActivity;
    private Context mContext;

    private String TAG=this.getClass().getSimpleName();

    private List<ClassRoom> mClassRoomList;

    public newzjy_classRoom_Adapter(List<ClassRoom> data) {
        this.mClassRoomList = data;

    }

    public void updateData(List<ClassRoom> data) {
        this.mClassRoomList = data;
        notifyDataSetChanged();
    }


    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }



    @Override
    public newzjy_classRoom_Adapter.ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.newzjy_classroom_recycler_item, parent, false);

        if (mContext==null) {
            mContext = parent.getContext();
            mActivity=(newzjy_classRoomActivity)mContext;
        }
        // 实例化viewholder
        newzjy_classRoom_Adapter.ViewHolder1 viewHolder = new newzjy_classRoom_Adapter.ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final newzjy_classRoom_Adapter.ViewHolder1 holder, int position) {
        // 绑定数据

        ClassRoom vClassRoom = mClassRoomList.get(position);

        String title=vClassRoom.getTitle();
        String className = vClassRoom.getClassName();
        String courseName = vClassRoom.getCourseName();
        String activityNum = vClassRoom.getActivityNum();
        String StartDate=vClassRoom.getStartDate();

        holder.mTextView1.setText(title);
        holder.mTextView2.setText(className+"-"+courseName);
        holder.mTextView4.setText("活动: "+activityNum);

        String zt = "未知";
        if(StartDate!=null && !StartDate.isEmpty()){
            long startDate = Long.valueOf(vClassRoom.getStartDate());

            if (System.currentTimeMillis()>(startDate+24*60*60*1000)){
                zt = "结束";
                holder.mTextView3.setTextColor(mContext.getColor(R.color.color4));
            }else if(System.currentTimeMillis()<(startDate-24*60*60*1000)){
                zt = "待开始";
            }else {
                zt = "进行中";
                holder.mTextView3.setTextColor(mContext.getColor(R.color.colorPrimary));
            }

        }
        holder.mTextView3.setText("状态: "+zt);

        Log.d(TAG, "onBindViewHolder: ");
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                showSetDialog(vClassRoom);

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


    private void showSetDialog(ClassRoom vClassRoom) {
        AlertDialog.Builder setDeBugDialog = new AlertDialog.Builder(mContext);
        //获取界面
        View dialogView = LayoutInflater.from(mContext).inflate(R.layout.newzjy_classroom_dialog, null);
        //将界面填充到AlertDiaLog容器并去除边框
        setDeBugDialog.setView(dialogView);
        //初始化控件
        Button newzjy_bt_qd = dialogView.findViewById(R.id.newzjy_bt_qd);
        Button newzjy_bt_sckt = dialogView.findViewById(R.id.newzjy_bt_sckt);
        Button newzjy_bt_jrkt = dialogView.findViewById(R.id.newzjy_bt_jrkt);
        Button newzjy_bt_cjkt = dialogView.findViewById(R.id.newzjy_bt_cjkt);

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
        newzjy_bt_qd.setEnabled(false);
        newzjy_bt_qd.setOnClickListener((View view)-> {
            new Thread(()->{
                sign(vClassRoom);
            }).start();

        });

        newzjy_bt_sckt.setOnClickListener((View view)-> {
            new Thread(()->{
                DelClassroom(vClassRoom);
            }).start();
        });

        newzjy_bt_cjkt.setOnClickListener((View view)-> {
            new Thread(()->{
                creatClassroom(vClassRoom);
            }).start();
        });



        newzjy_bt_jrkt.setOnClickListener((View view)-> {
            if (vClassRoom.getCourseId()!=null) {

                newZjyUserDao.sClassRoom=vClassRoom;

                Intent i = new Intent(mActivity, newzjy_courseHdActivity.class);

                //i.putExtra("vClassRoom", vClassRoom);

                mActivity.startActivity(i);
            }
        });

    }

    private void DelClassroom(ClassRoom vClassRoom) {
       String resp=newZjyApi.getDelClassroom(vClassRoom.getId());
        mActivity.runOnUiThread(()->{
            Tool.toast(mContext, resp);
        });
    }

    private void sign(ClassRoom vClassRoom) {
        List<classActivity> ClassActivities = newZjyMain.getClassActivityZ(vClassRoom);
        for (classActivity vActivity : ClassActivities) {
            newZjyMain.Sign(mActivity, vActivity);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            }
        }

    private void creatClassroom(ClassRoom vClassRoom){
        String resp=newZjyMain.SaveClassroom(vClassRoom.getCourseId(),Tool.getRandomInt(100,999)+"",newZjyUserDao.sNewZjyUser.getToken());
        mActivity.runOnUiThread(()->{
            Tool.toast(mContext, resp);
        });
    }


    @Override
    public int getItemCount() {
        return mClassRoomList == null ? 0 : mClassRoomList.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView1,mTextView2,mTextView3,mTextView4;

        public ViewHolder1(View itemView) {
            super(itemView);

            mTextView1=itemView.findViewById(R.id.zjy_kcmc);
            mTextView2=itemView.findViewById(R.id.zjy_bjmc);
            mTextView3=itemView.findViewById(R.id.zjy_kt);
            mTextView4=itemView.findViewById(R.id.zjy_hd);
        }
    }



}
