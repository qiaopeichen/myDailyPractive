package com.example.qiaopc.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.TextView;

import com.example.qiaopc.myapplication.R;

import java.util.List;

/**
 * Created by qiaopc on 2019/2/13.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

    private List<String> mData;

    public RecyclerAdapter(List<String> data) {
        mData = data;
    }

    public OnItemClickListener itemClickListener;

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.itemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder
            implements View.OnClickListener {

        public TextView textView;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView;
            textView.setOnClickListener(this);
        }

        // 通过接口回调来实现RecyclerView的点击事件
        @Override
        public void onClick(View v) {
            if (itemClickListener != null) {
                itemClickListener.onItemClick(v, getPosition());
            }
        }
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // 将布局转化为view并传递给RecyclerView封装好的ViewHolder
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.rc_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // 建立起ViewHolder中视图与数据的关联
        holder.textView.setText(mData.get(position) + position);
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

}
