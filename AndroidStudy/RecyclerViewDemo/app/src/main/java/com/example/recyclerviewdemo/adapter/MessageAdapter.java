package com.example.recyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.bean.Message;
import com.example.recyclerviewdemo.databinding.ItemMyselfRvBinding;
import com.example.recyclerviewdemo.databinding.ItemOtherRvBinding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-08 下午2:07
 */
public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final List<Message> messages;

    private static final int OTHER = 0;
    private static final int MYSELF = 1;

    public MessageAdapter(List<Message> messages) {
        this.messages = messages;
    }

    @NonNull
    @NotNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder viewHolder;
        if (viewType == OTHER){
            viewHolder = new OtherViewHolder(ItemOtherRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        } else {
            viewHolder = new MyselfViewHolder(ItemMyselfRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof OtherViewHolder){
            ((OtherViewHolder) holder).otherRvBinding.tvContent.setText(messages.get(position).getContent());
        } else if (holder instanceof MyselfViewHolder) {
            ((MyselfViewHolder) holder).myselfRvBinding.tvContent.setText(messages.get(position).getContent());
        }
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    @Override
    public int getItemViewType(int position) {
        if (messages.get(position).getType() == OTHER){
            return OTHER;
        }else {
            return MYSELF;
        }
    }

    public static class OtherViewHolder extends RecyclerView.ViewHolder{

        public ItemOtherRvBinding otherRvBinding;
        public OtherViewHolder(@NonNull @NotNull ItemOtherRvBinding itemOtherRvBinding) {
            super(itemOtherRvBinding.getRoot());
            otherRvBinding = itemOtherRvBinding;
        }
    }

    public static class MyselfViewHolder extends RecyclerView.ViewHolder{

        public ItemMyselfRvBinding myselfRvBinding;
        public MyselfViewHolder(@NonNull @NotNull ItemMyselfRvBinding itemMyselfRvBinding) {
            super(itemMyselfRvBinding.getRoot());
            myselfRvBinding = itemMyselfRvBinding;
        }
    }
}
