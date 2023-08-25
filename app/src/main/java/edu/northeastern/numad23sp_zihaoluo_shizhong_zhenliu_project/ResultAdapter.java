package edu.northeastern.numad23sp_zihaoluo_shizhong_zhenliu_project;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ResultAdapter extends RecyclerView.Adapter<ResultViewHolder> {
    private final List<String> tips;
    private final Context context;
    private final ResultAdapter adapter = this;

    public ResultAdapter(List<String> tips, Context context) {
        this.tips = tips;
        this.context = context;
    }

    @NonNull
    @Override
    public ResultViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ResultViewHolder(LayoutInflater.from(context).inflate(R.layout.item_result, null));
    }

    @Override
    public void onBindViewHolder(@NonNull ResultViewHolder holder, int position) {
        holder.tip.setText(tips.get(position));
    }


    @Override
    public int getItemCount() {
        return tips.size();
    }
}
