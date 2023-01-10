package com.zj970.tourism.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.zj970.tourism.R;
import com.zj970.tourism.entity.ExoticItem;

import java.util.List;

/**
 * <p>
 * 异域风情RecyclerView适配器
 * </p>
 *
 * @author: zj970
 * @date: 2023/1/10
 */
public class ExoticAdapter extends RecyclerView.Adapter<ExoticAdapter.ViewHolder>{
    Context context;
    List<ExoticItem> exoticItems;

    public ExoticAdapter(List<ExoticItem> exoticItems) {
        this.exoticItems = exoticItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (context == null){
            context = parent.getContext();
        }
        View view = LayoutInflater.from(context).inflate(R.layout.exotic_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        ExoticItem exoticItem = exoticItems.get(position);
        Glide.with(holder.view).load(exoticItem.getIconURL()).into(holder.icon);
        holder.country.setText(exoticItem.getCountry());
        holder.count.setText(exoticItem.getCount() + " " + context.getResources().getString(R.string.travelogues));
    }

    @Override
    public int getItemCount() {
        return exoticItems.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder{
        View view;
        ImageView icon;
        TextView country;
        TextView count;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView;
            icon = itemView.findViewById(R.id.exotic_item_icon);
            country = itemView.findViewById(R.id.exotic_item_country);
            count = itemView.findViewById(R.id.exotic_item_count);
            icon.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(view.getContext(), "iiiiiiiii", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}
