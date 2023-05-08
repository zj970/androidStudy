package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.recyclerviewdemo.adapter.GroupAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.databinding.ActivityRvMultilevelListBinding;

public class RvMultilevelListActivity extends BaseActivity {
    private ActivityRvMultilevelListBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRvMultilevelListBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        back(binding.materialToolbar);
        binding.rvGroup.setAdapter(new GroupAdapter(getGroups()));
        binding.rvGroup.setLayoutManager(new LinearLayoutManager(this));
    }
}