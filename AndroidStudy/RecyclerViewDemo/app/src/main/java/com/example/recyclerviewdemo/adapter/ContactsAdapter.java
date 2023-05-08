package com.example.recyclerviewdemo.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.bean.Group;
import com.example.recyclerviewdemo.databinding.ItemContactsRvBinding;
import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-08 下午2:39
 */
public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ViewHolder> {
    private final List<Group.Contacts> contacts;

    public ContactsAdapter(List<Group.Contacts> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return new ViewHolder(ItemContactsRvBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, int position) {
        holder.binding.tvContactsName.setText(contacts.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        ItemContactsRvBinding binding;
        public ViewHolder(@NonNull @NotNull ItemContactsRvBinding itemContactsRvBinding) {
            super(itemContactsRvBinding.getRoot());
            binding = itemContactsRvBinding;
        }
    }
}
