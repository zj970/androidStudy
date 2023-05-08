package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.recyclerviewdemo.adapter.MessageAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.databinding.ActivityRvMultipleLayoutsBinding;

public class RvMultipleLayoutsActivity extends BaseActivity {

    private ActivityRvMultipleLayoutsBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRvMultipleLayoutsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }

    private void initView(){
        back(binding.materialToolbar);
        binding.rvText.setLayoutManager(new LinearLayoutManager(this));
        binding.rvText.setAdapter(new MessageAdapter(getMessages()));
    }
}