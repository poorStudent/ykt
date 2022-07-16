package com.vms.ykt.UI.Adapter.zjyAdapter;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.vms.ykt.R;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_courseHdActivity;
import com.vms.ykt.UI.Activity.zjyActivity.zjy_testAnswActivity;
import com.vms.ykt.UI.Adapter.baseRecyclerAdapter;
import com.vms.ykt.Util.Tool;
import com.vms.ykt.yktStuMobile.zjy.zjyApi;
import com.vms.ykt.yktStuMobile.zjy.zjyCouresActivitInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyCourseIfno;
import com.vms.ykt.yktStuMobile.zjy.zjyTeachInfo;
import com.vms.ykt.yktStuMobile.zjy.zjyUser;
import com.vms.ykt.yktStuWeb.zjy.zjyApiW;
import com.vms.ykt.yktStuWeb.zjy.zjyHttpW;
import com.vms.ykt.yktStuWeb.zjy.zjyMainW;

import java.util.ArrayList;
import java.util.List;

public class zjy_courseHDAdapter extends baseRecyclerAdapter<zjy_courseHDAdapter.ViewHolder1> {

    List<zjyCouresActivitInfo> mZjyCouresActivitInfos;
    zjyCourseIfno mCourseIfno;
    private zjyUser mZjyUser;
    private zjy_courseHdActivity mActivity;
    private zjyTeachInfo mZjyTeachInfo;

    private String TAG=this.getClass().getSimpleName();
    /**
     * 事件回调监听
     */
    private zjy_courseHDAdapter.OnItemClickListener onItemClickListener;

    private zjy_courseHDAdapter.initRcView mInitRcView;
    private ArrayList<Object> signOk;

    private zjyHttpW mZjyHttpW;
    private zjyApiW mZjyApiW;
    private zjyMainW mZjyMainW;

    public zjy_courseHDAdapter(List<zjyCouresActivitInfo> data, zjyUser zjyUsers, zjyTeachInfo zjyTeachInfo) {
        this.mZjyCouresActivitInfos = data;
        this.mZjyUser=zjyUsers;
        this.mZjyTeachInfo=zjyTeachInfo;
        mZjyHttpW.setUserCookie(mZjyUser.getCookie());
        mZjyApiW.setZjyHttpW(mZjyHttpW);
        mZjyMainW.setZjyApiW(mZjyApiW);
    }

    public void updateData(List<zjyCouresActivitInfo> data) {

        if (mZjyCouresActivitInfos !=null){
            mZjyCouresActivitInfos.clear();
            mZjyCouresActivitInfos.addAll(data);
        }else {
            mZjyCouresActivitInfos =new ArrayList<>();
            mZjyCouresActivitInfos.addAll(data);
        }

        notifyDataSetChanged();
    }

    /**
     * 删除Item
     */
    public void deleteItem(int position) {
        if(mZjyCouresActivitInfos == null || mZjyCouresActivitInfos.isEmpty()) {
            return;
        }
        mZjyCouresActivitInfos.remove(position);
        notifyItemRemoved(position);
    }

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(zjy_courseHDAdapter.OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(zjy_courseHDAdapter.initRcView listener) {

        this.mInitRcView = listener;
    }

    @Override
    public ViewHolder1 onCreateViewHolder(ViewGroup parent, int viewType) {
        // 实例化展示的view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.zjy_coursehd_recycler_item, parent, false);
        // 实例化viewholder

        if(mActivity==null)
        mActivity=(zjy_courseHdActivity) parent.getContext();

        ViewHolder1 viewHolder = new ViewHolder1(v);
        Log.d(TAG, "onCreateViewHolder: ");
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (final ViewHolder1 holder, int position) {
        // 绑定数据

       final zjyCouresActivitInfo vZjyCouresActivitInfo = mZjyCouresActivitInfos.get(position);
        String zymc=vZjyCouresActivitInfo.getTitle();
        holder.mTextView0.setText(zymc);
        holder.mTextView1.setText("DateCreated："+vZjyCouresActivitInfo.getDateCreated());
        holder.mTextView2.setText("Id："+vZjyCouresActivitInfo.getId());
        holder.mTextView3.setText("DataType："+vZjyCouresActivitInfo.getDataType());
        holder.mTextView4.setText("state："+vZjyCouresActivitInfo.getState());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {

                if (vZjyCouresActivitInfo.getDataType().contains("测验")) {
                    Intent i = new Intent(mActivity, zjy_testAnswActivity.class);
                    i.putExtra("ZjyUser", mZjyUser);
                    i.putExtra("mZjyTeachInfo", mZjyTeachInfo);
                    i.putExtra("vZjyCouresActivitInfo", vZjyCouresActivitInfo);
                    mActivity.startActivity(i);
                }
                if (vZjyCouresActivitInfo.getDataType().contains("签到")) {
                  new Thread(()->{

                      doSign(mZjyUser,mZjyTeachInfo,vZjyCouresActivitInfo);
                  }).start();
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

    public void doSign(zjyUser zjyUsers, zjyTeachInfo zjyTeachInfos, zjyCouresActivitInfo varActivitInfo) {

        if (varActivitInfo.getDataType().equals("签到") && !varActivitInfo.getState().equals("3")) {
            if (signOk.contains(varActivitInfo.getId())) {
                mActivity.runOnUiThread(() -> {
                    Tool.toast(mActivity, zjyTeachInfos.getCourseName() + "\n" + varActivitInfo.getTitle() + "\n" + "已签到");
                });
            }
            sign(varActivitInfo, zjyUsers, zjyTeachInfos);
        } else if (varActivitInfo.getDataType().equals("签到")) {
            mActivity.runOnUiThread(() -> {
                Tool.toast(mActivity, zjyTeachInfos.getCourseName() + "\n" + varActivitInfo.getTitle() + "\n" + "签到已结束");
            });
        }


    }

    public void sign(zjyCouresActivitInfo varActivitInfo, zjyUser zjyUsers, zjyTeachInfo zjyTeachInfos) {

        String resc1 = zjyApi.getJoinActivities(varActivitInfo.getId(), zjyUsers, zjyTeachInfos);
        String resc2 = zjyApi.getSaveSign(varActivitInfo.getId(), varActivitInfo.getGesture(), zjyUsers, zjyTeachInfos);
        final String ret;
        if (resc1 != null && resc2 != null && resc2.contains("签到成功")) {
            signOk.add(varActivitInfo.getId());
            ret = "签到成功";
        } else {
            String resp = mZjyApiW.getSaveSign(varActivitInfo.getId(), varActivitInfo.getGesture(), zjyUsers, zjyTeachInfos);
            if (resp != null && resp.contains("{\"code\":1}")) {
                signOk.add(varActivitInfo.getId());
                ret = "签到成功";
            } else {
                ret = "签到失败";

            }
        }
        mActivity.runOnUiThread(() -> {
            Tool.toast(mActivity, zjyTeachInfos.getCourseName() + "\n" + varActivitInfo.getTitle() + "\n" + ret);
        });
    }



    public void initListener(final ViewHolder1 holder){

    }


    @Override
    public int getItemCount() {
        return mZjyCouresActivitInfos == null ? 0 : mZjyCouresActivitInfos.size();
    }

    public static class ViewHolder1 extends RecyclerView.ViewHolder {

        TextView mTextView0, mTextView1,mTextView2,mTextView3,mTextView4;


        public ViewHolder1(View itemView) {
            super(itemView);
            mTextView0=itemView.findViewById(R.id.zjy_work_zymc);
            mTextView1=itemView.findViewById(R.id.zjy_work_zyms);
            mTextView2=itemView.findViewById(R.id.zjy_work_zysj);
            mTextView3=itemView.findViewById(R.id.zjy_work_zycs);
            mTextView4=itemView.findViewById(R.id.zjy_work_zylx);

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
