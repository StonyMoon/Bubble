package com.stonymoon.bubble.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;
import com.stonymoon.bubble.R;
import com.stonymoon.bubble.bean.BubbleBean;

import java.util.List;

import cn.jpush.im.android.api.model.UserInfo;


public class ShareAdapter extends RecyclerView.Adapter<ShareAdapter.ViewHolder> {
    private List<BubbleBean.ContentBean> mList;
    private Context mContext;

    public ShareAdapter(List<BubbleBean.ContentBean> list) {
        this.mList = list;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null) {
            mContext = parent.getContext();

        }
        final View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_share, parent, false);
        final ViewHolder holder = new ViewHolder(view);
        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                BubbleBean.ContentBean bean = mList.get(position);
                //todo 设置点击打开

            }

        });


        return holder;

    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        BubbleBean.ContentBean bean = mList.get(position);
        Picasso.with(mContext)
                .load(bean.getImage())
                .into(holder.imageView);
        //.placeholder(R.drawable.icon_placeholder)
        holder.titleView.setText(bean.getTitle());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView titleView;

        public ViewHolder(View view) {
            super(view);
            imageView = (ImageView) view.findViewById(R.id.iv_share_item_image);
            titleView = (TextView) view.findViewById(R.id.tv_share_item_title);

        }

    }

}
