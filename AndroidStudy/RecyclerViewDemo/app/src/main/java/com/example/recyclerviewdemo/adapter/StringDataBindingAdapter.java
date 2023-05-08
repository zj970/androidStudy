package com.example.recyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.bean.BasicBean;
import com.example.recyclerviewdemo.databinding.ItemTextDataRvBinding;
import com.example.recyclerviewdemo.listener.OnItemClickListener;
import com.example.recyclerviewdemo.listener.OnItemLongClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-08 上午10:16
 */
public class StringDataBindingAdapter extends RecyclerView.Adapter<StringDataBindingAdapter.ViewHolder> {

    private List<BasicBean> list;

    private OnItemClickListener listener;//视图点击

    private OnItemLongClickListener longClickListener;//视图长按

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public StringDataBindingAdapter(List<BasicBean> list) {
        this.list = list;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemTextDataRvBinding binding = ItemTextDataRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        ItemTextDataRvBinding binding = DataBindingUtil.getBinding(holder.binding.getRoot());
        if (binding != null){
            binding.setBasicBean(list.get(position));
            binding.executePendingBindings();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ItemTextDataRvBinding binding;

        public ViewHolder(@NonNull @NotNull ItemTextDataRvBinding itemTextDataRvBinding) {
            super(itemTextDataRvBinding.getRoot());
            binding = itemTextDataRvBinding;
        }
    }

    private void handlerEvents(View view, ViewHolder viewHolder){
        view.setOnClickListener(v -> {
            if (listener != null){
                listener.onItemClick(v,viewHolder.getAdapterPosition());
            }
        });

        view.setOnLongClickListener(v -> {
            if (longClickListener != null){
                return longClickListener.onItemLongClick(v, viewHolder.getAdapterPosition());
            }
            return false;
        });
    }
}
