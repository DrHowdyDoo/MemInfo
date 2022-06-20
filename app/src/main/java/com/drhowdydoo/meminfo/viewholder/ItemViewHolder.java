package com.drhowdydoo.meminfo.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drhowdydoo.meminfo.databinding.RecyclerViewItemLayoutBinding;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView txtAttribute,txtValue;

    public ItemViewHolder(@NonNull RecyclerViewItemLayoutBinding binding) {
        super(binding.getRoot());
        txtAttribute = binding.txtLabelKey;
        txtValue = binding.txtLabelValue;
    }

    public TextView getTxtAttribute() {
        return txtAttribute;
    }

    public TextView getTxtValue() {
        return txtValue;
    }
}
