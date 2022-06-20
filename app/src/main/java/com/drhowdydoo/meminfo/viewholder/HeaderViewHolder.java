package com.drhowdydoo.meminfo.viewholder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.drhowdydoo.meminfo.databinding.RecyclerViewHeaderLayoutBinding;
import com.google.android.material.progressindicator.LinearProgressIndicator;

public class HeaderViewHolder extends RecyclerView.ViewHolder {

    private TextView txtMemTotal,txtMemUsed,txtMemFree;
    private LinearProgressIndicator memBar;

    public HeaderViewHolder(@NonNull RecyclerViewHeaderLayoutBinding binding) {
        super(binding.getRoot());
        txtMemTotal = binding.txtMemory;
        txtMemUsed = binding.txtMemoryUsed;
        txtMemFree = binding.txtMemoryFree;
        memBar = binding.memoryBar;
    }

    public TextView getTxtMemTotal() {
        return txtMemTotal;
    }

    public TextView getTxtMemUsed() {
        return txtMemUsed;
    }

    public TextView getTxtMemFree() {
        return txtMemFree;
    }

    public LinearProgressIndicator getMemBar() {
        return memBar;
    }
}
