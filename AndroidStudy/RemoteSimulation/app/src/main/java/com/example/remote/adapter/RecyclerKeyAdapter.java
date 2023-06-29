package com.example.remote.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.remote.api.IOnClickItemCallback;
import com.example.remote.databinding.RecyclerListItemBinding;
import com.example.remote.protocol.EProtocol;

import java.util.List;

public class RecyclerKeyAdapter extends RecyclerView.Adapter<RecyclerKeyAdapter.MyViewHolder>{


    private IOnClickItemCallback onClickItemCallback;

    private final List<String> buttonText;

    public RecyclerKeyAdapter(List<String> buttonText) {
        this.buttonText = buttonText;
    }

    public void setOnClickItemCallback(IOnClickItemCallback onClickItemCallback) {
        this.onClickItemCallback = onClickItemCallback;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerListItemBinding binding = RecyclerListItemBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        MyViewHolder viewHolder = new MyViewHolder(binding);
        //添加点击回调
        binding.key.setOnClickListener(v -> {
            if (onClickItemCallback != null)
            {
                onClickItemCallback.onItemClick(viewHolder.getBindingAdapterPosition());
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.binding.key.setText(buttonText.get(position));
    }

    @Override
    public int getItemCount() {
        return buttonText.size();
    }

    public List<String> getButtonText() {
        return buttonText;
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        RecyclerListItemBinding binding;
        public MyViewHolder(@NonNull RecyclerListItemBinding itemBinding) {
            super(itemBinding.getRoot());
            binding = itemBinding;
        }
    }
}
