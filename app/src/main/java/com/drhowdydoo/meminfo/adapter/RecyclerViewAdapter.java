package com.drhowdydoo.meminfo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.drhowdydoo.meminfo.databinding.RecyclerViewHeaderLayoutBinding;
import com.drhowdydoo.meminfo.databinding.RecyclerViewItemLayoutBinding;
import com.drhowdydoo.meminfo.model.DisplayHeader;
import com.drhowdydoo.meminfo.model.MemInfo;
import com.drhowdydoo.meminfo.util.MyDiffUtil;
import com.drhowdydoo.meminfo.viewholder.HeaderViewHolder;
import com.drhowdydoo.meminfo.viewholder.ItemViewHolder;

import java.util.ArrayList;

@SuppressWarnings("FieldCanBeLocal")
public class RecyclerViewAdapter extends RecyclerView.Adapter {

    private ArrayList<Object> dataList;
    private  Context context;

    public RecyclerViewAdapter(ArrayList<Object> dataList, Context context) {
        this.dataList = new ArrayList<>(dataList);
        this.context = context;
    }

    @Override
    public int getItemViewType(int position) {
        if(dataList.get(position) instanceof DisplayHeader) return 0;
        else if(dataList.get(position) instanceof MemInfo) return 1;
        else return -1;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if(viewType == 0){
            RecyclerViewHeaderLayoutBinding binding = RecyclerViewHeaderLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new HeaderViewHolder(binding);
        }
        else {
            RecyclerViewItemLayoutBinding binding = RecyclerViewItemLayoutBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
            return new ItemViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(this.getItemViewType(position) == 0){

            HeaderViewHolder headerViewHolder = (HeaderViewHolder) holder;
            DisplayHeader dh = (DisplayHeader) dataList.get(position);
            headerViewHolder.getTxtMemTotal().setText(dh.getTotalMem());
            headerViewHolder.getTxtMemUsed().setText(dh.getUsedMem());
            headerViewHolder.getTxtMemFree().setText(dh.getFreeMem());
            headerViewHolder.getMemBar().setProgress(dh.getUsedFreeRatio(),true);

        }else {

            ItemViewHolder itemViewHolder = (ItemViewHolder) holder;
            MemInfo mi = (MemInfo) dataList.get(position);
            itemViewHolder.getTxtAttribute().setText(mi.getAttribute());
            itemViewHolder.getTxtValue().setText(mi.getValue());
            if(mi.isShowIcon()) itemViewHolder.getImg_bullet().setVisibility(View.VISIBLE);
            else itemViewHolder.getImg_bullet().setVisibility(View.INVISIBLE);

        }

    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateList(ArrayList<Object> newList){
        MyDiffUtil myDiffUtil = new MyDiffUtil(dataList,newList);
        androidx.recyclerview.widget.DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(myDiffUtil);
        dataList.clear();
        dataList.addAll(newList);
        diffResult.dispatchUpdatesTo(this);
    }
}
