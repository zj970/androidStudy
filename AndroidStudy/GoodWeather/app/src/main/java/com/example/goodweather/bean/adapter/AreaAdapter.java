package com.example.goodweather.bean.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.goodweather.bean.Province;
import com.example.goodweather.databinding.ItemTextRvBinding;
import com.example.goodweather.util.AdministrativeType;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:39
 */
public class AreaAdapter extends RecyclerView.Adapter<AreaAdapter.ViewHolder> {

    private final List<Province.City.Area> areas;

    private AdministrativeClickCallback administrativeClickCallback;//视图点击

    public AreaAdapter(List<Province.City.Area> areas) {
        this.areas = areas;
    }

    public void setAdministrativeClickCallback(AdministrativeClickCallback administrativeClickCallback) {
        this.administrativeClickCallback = administrativeClickCallback;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTextRvBinding binding = ItemTextRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        ViewHolder viewHolder = new ViewHolder(binding);
        //添加视图点击事件
        binding.getRoot().setOnClickListener(v -> {
            if (administrativeClickCallback != null) {
                administrativeClickCallback.onAdministrativeItemClick(v, viewHolder.getAdapterPosition(), AdministrativeType.AREA);
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.binding.tvText.setText(areas.get(position).getAreaName());
    }

    @Override
    public int getItemCount() {
        return areas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemTextRvBinding binding;

        public ViewHolder(@NonNull ItemTextRvBinding itemTextRvBinding) {
            super(itemTextRvBinding.getRoot());
            binding = itemTextRvBinding;
        }
    }
}

