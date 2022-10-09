package com.vms.ykt.UI.Adapter;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

public abstract class  baseRecyclerAdapter <T extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<T>{

    /**
     * 事件回调监听
     */
    private OnItemClickListener onItemClickListener;

    private initRcView mInitRcView;

    /**
     * 设置回调监听
     *
     * @param listener
     */
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.onItemClickListener = listener;
    }

    public void setInitView(initRcView listener) {

        this.mInitRcView = listener;
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
