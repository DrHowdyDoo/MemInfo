package com.drhowdydoo.meminfo.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drhowdydoo.meminfo.databinding.RecyclerViewItemLayoutBinding;

public class ItemViewHolder extends RecyclerView.ViewHolder {

    private TextView txtAttribute,txtValue;
    private ImageView img_bullet;

    public ItemViewHolder(@NonNull RecyclerViewItemLayoutBinding binding) {
        super(binding.getRoot());
        txtAttribute = binding.txtLabelKey;
        txtValue = binding.txtLabelValue;
        img_bullet = binding.imgBullet;
    }

    public TextView getTxtAttribute() {
        return txtAttribute;
    }

    public TextView getTxtValue() {
        return txtValue;
    }

    public ImageView getImg_bullet() {
        return img_bullet;
    }
}
