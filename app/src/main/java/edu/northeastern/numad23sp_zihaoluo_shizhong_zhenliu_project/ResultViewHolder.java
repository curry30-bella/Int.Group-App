package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ResultViewHolder extends RecyclerView.ViewHolder {
    public TextView tip;

    public ResultViewHolder(@NonNull View itemView) {
        super(itemView);
        this.tip = itemView.findViewById(R.id.tip);
    }
}
