package com.drhowdydoo.meminfo.util;

import com.drhowdydoo.meminfo.model.DisplayHeader;
import com.drhowdydoo.meminfo.model.MemInfo;

import java.util.ArrayList;
import java.util.Objects;

public class MyDiffUtil extends androidx.recyclerview.widget.DiffUtil.Callback {


    private ArrayList<Object> oldList,newList;

    public MyDiffUtil(ArrayList<Object> oldList, ArrayList<Object> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldItemPosition == newItemPosition;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        if(oldList.get(oldItemPosition) instanceof DisplayHeader){
            DisplayHeader oldObj = (DisplayHeader) oldList.get(oldItemPosition);
            DisplayHeader newObj = (DisplayHeader) newList.get(newItemPosition);
            return (Objects.equals(oldObj.getUsedMem(), newObj.getUsedMem()));
        }else {
            MemInfo oldObj = (MemInfo) oldList.get(oldItemPosition);
            MemInfo newObj = (MemInfo) newList.get(newItemPosition);
            return (Objects.equals(oldObj.getValue(),newObj.getValue()));
        }
    }
}
