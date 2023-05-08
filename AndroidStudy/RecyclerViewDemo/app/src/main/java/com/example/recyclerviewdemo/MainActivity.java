package com.example.recyclerviewdemo;

import android.os.Bundle;
import com.example.recyclerviewdemo.activity.*;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity {

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.btnRvBasicUse.setOnClickListener(v -> jumpActivity(RvBasicUseActivity.class));

        binding.btnRvViewBinding.setOnClickListener(v -> jumpActivity(RvViewBindingActivity.class));

        binding.btnRvDataBinding.setOnClickListener(v -> jumpActivity(RvDataBindingActivity.class));

        binding.btnRvRefreshLoad.setOnClickListener(v -> jumpActivity(RvRefreshLoadActivity.class));

        binding.btnRvMultipleLayouts.setOnClickListener(v -> jumpActivity(RvMultipleLayoutsActivity.class));

        binding.btnRvMultilevelList.setOnClickListener(v -> jumpActivity(RvMultilevelListActivity.class));

        binding.btnRvDynamicallyChangeData.setOnClickListener(v -> jumpActivity(RvDynamicallyChangeActivity.class));

        binding.btnRvSwipeDrag.setOnClickListener(v -> jumpActivity(RvSwipeDragActivity.class));

    }
}