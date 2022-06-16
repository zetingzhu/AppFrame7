package com.xtrend.zt_srecycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 *
 */
public class RecentlyViewedAdapter extends RecyclerView.Adapter<RecentlyViewedAdapter.MyViewHolder> {
    Context context;
    List<String> data;

    public RecentlyViewedAdapter(Context context, List<String> data) {
        this.context = context;
        this.data = data;

    }

    public void addList(List<String> dd) {
        data.addAll(dd);
        notifyDataSetChanged();
    }


    @NonNull
    @Override

    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_find_recently_viewed, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String ds = data.get(position);
        holder.textView.setText(ds);
    }

    @Override
    public int getItemCount() {
        return data != null ? data.size() : 0;
    }


    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
        }
    }
}
