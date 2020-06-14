package com.example.android_test;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import io.github.luizgrp.sectionedrecyclerviewadapter.Section;
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters;

public class SectionAdapter extends Section {

    private Context context;
    private Integer header;
    private DataVO dataVO;

    SectionAdapter(Context context, Integer header, DataVO dataVO) {
        super(SectionParameters.builder()
                .itemResourceId(R.layout.item)
                .headerResourceId(R.layout.header)
                .build());
        this.context = context;
        this.header = header;
        this.dataVO = dataVO;
    }

    @Override
    public int getContentItemsTotal() {
        return 1;
    }

    @Override
    public RecyclerView.ViewHolder getHeaderViewHolder(View view) {
        return new HeaderViewHolder(view);
    }

    @Override
    public RecyclerView.ViewHolder getItemViewHolder(View view) {
        return new ItemViewHolder(view);
    }

    @Override
    public void onBindHeaderViewHolder(final RecyclerView.ViewHolder holder) {
        final HeaderViewHolder headerHolder = (HeaderViewHolder)holder;
        headerHolder.iv_Image.setImageResource(header);
    }

    @Override
    public void onBindItemViewHolder(RecyclerView.ViewHolder holder, final int position) {

        final ItemViewHolder itemHolder = (ItemViewHolder)holder;
        itemHolder.tv_StartTime.setText(dataVO.getStartTime());
        itemHolder.tv_EndTime.setText(dataVO.getEndTime());
        itemHolder.tv_Temp.setText(dataVO.getTemP());

        View.OnClickListener clickclick=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,SecondActivity.class);
                intent.putExtra("startTime",dataVO.getStartTime());
                intent.putExtra("endTime",dataVO.getEndTime());
                intent.putExtra("temP",dataVO.getTemP());
                context.startActivity(intent);


            }
        };
        itemHolder.tv_StartTime.setOnClickListener(clickclick);
        itemHolder.tv_EndTime.setOnClickListener(clickclick);
        itemHolder.tv_Temp.setOnClickListener(clickclick);
    }

    class HeaderViewHolder extends RecyclerView.ViewHolder {
        private final ImageView iv_Image;
        HeaderViewHolder(View itemView) {
            super(itemView);
            iv_Image=itemView.findViewById(R.id.iv_Image);

        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        private final TextView tv_StartTime,tv_EndTime,tv_Temp;
        ItemViewHolder(View itemView) {
            super(itemView);
            tv_StartTime = itemView.findViewById(R.id.tv_StartTime);
            tv_EndTime = itemView.findViewById(R.id.tv_EndTime);
            tv_Temp = itemView.findViewById(R.id.tv_Temp);
        }
    }

}