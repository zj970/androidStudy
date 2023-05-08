package com.example.recyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.databinding.ItemTextRvBinding;
import com.example.recyclerviewdemo.listener.OnItemChildClickListener;
import com.example.recyclerviewdemo.listener.OnItemChildLongClickListener;
import com.example.recyclerviewdemo.listener.OnItemClickListener;
import com.example.recyclerviewdemo.listener.OnItemLongClickListener;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-08 上午9:14
 */
public class StringViewBindingAdapter extends RecyclerView.Adapter<StringViewBindingAdapter.ViewHolder>{
    private final List<String> list;

    private OnItemClickListener listener;//视图点击

    private OnItemChildClickListener childClickListener;//视图子控件点击

    private OnItemLongClickListener longClickListener;//视图长按

    private OnItemChildLongClickListener childLongClickListener;//视图子控件长按

    public void setLongClickListener(OnItemLongClickListener longClickListener) {
        this.longClickListener = longClickListener;
    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public void setChildLongClickListener(OnItemChildLongClickListener childLongClickListener) {
        this.childLongClickListener = childLongClickListener;
    }

    public void setChildClickListener(OnItemChildClickListener childClickListener) {
        this.childClickListener = childClickListener;
    }

    public StringViewBindingAdapter(List<String> list) {
        this.list = list;
    }


    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        ItemTextRvBinding  binding = ItemTextRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        ViewHolder viewHolder = new ViewHolder(binding);
        handlerEvents(binding.getRoot(),viewHolder);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.binding.tvText.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        public ItemTextRvBinding binding;
        public ViewHolder(@NonNull @NotNull ItemTextRvBinding itemTextRvBinding) {
            super(itemTextRvBinding.getRoot());
            binding = itemTextRvBinding;
        }
    }


    /**
     * 处理事件
     * @param view
     * @param viewHolder
     */
    private void handlerEvents(View view,ViewHolder viewHolder){
        //添加视图点击事件
        view.setOnClickListener(v -> {
            if (listener != null){
                listener.onItemClick(v,viewHolder.getAdapterPosition());
            }
        });

        //添加多个子控件点击事件
        addChildClicks(new int[]{R.id.btn_test,R.id.btn_test_2},view,viewHolder);

        //添加视图长按事件
        view.setOnLongClickListener(v -> {
            if (longClickListener != null){
                return longClickListener.onItemLongClick(v,viewHolder.getAdapterPosition());
            }
            return false;
        });

        //添加视图子控件长按事件
        view.findViewById(R.id.btn_test).setOnLongClickListener(v -> {
            if (longClickListener != null){
                return longClickListener.onItemLongClick(v,viewHolder.getAdapterPosition());
            }
            return false;
        });
    }

    /**
     * 添加子控件点击事件
     * @param ids 子控件id数组
     * @param view
     * @param viewHolder
     */
    private void addChildClicks(int[] ids, View view, ViewHolder viewHolder){
        for(int id : ids){
            view.findViewById(id).setOnClickListener(v -> {
                if (childClickListener != null){
                    childClickListener.onItemChildClick(v,viewHolder.getAdapterPosition());
                }
            });
        }
    }
}
