package com.example.goodweather.bean.adapter;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.goodweather.bean.DailyResponse;
import com.example.goodweather.databinding.ItemDailyRvBinding;
import com.example.goodweather.util.EasyDate;
import com.example.goodweather.util.WeatherUtil;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-30 下午5:36
 */
public class DailyAdapter extends RecyclerView.Adapter<DailyAdapter.ViewHolder> {

    private final List<DailyResponse.DailyBean> dailyBeans;

    public DailyAdapter(List<DailyResponse.DailyBean> dailyBeans) {
        this.dailyBeans = dailyBeans;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemDailyRvBinding binding = ItemDailyRvBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(binding);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        DailyResponse.DailyBean dailyBean = dailyBeans.get(position);
        holder.binding.tvDate.setText(EasyDate.dateSplit(dailyBean.getFxDate()) + EasyDate.getDayInfo(dailyBean.getFxDate()));
        WeatherUtil.changeIcon(holder.binding.ivStatus, Integer.parseInt(dailyBean.getIconDay()));
        holder.binding.tvHeight.setText(dailyBean.getTempMax() + "℃");
        holder.binding.tvLow.setText(" / " + dailyBean.getTempMin() + "℃");
    }

    @Override
    public int getItemCount() {
        return dailyBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public ItemDailyRvBinding binding;

        public ViewHolder(@NonNull ItemDailyRvBinding itemTextRvBinding) {
            super(itemTextRvBinding.getRoot());
            binding = itemTextRvBinding;
        }
    }
}
