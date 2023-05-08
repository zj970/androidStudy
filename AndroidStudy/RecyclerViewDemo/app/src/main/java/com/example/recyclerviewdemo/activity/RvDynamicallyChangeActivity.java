package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.recyclerviewdemo.adapter.SelectAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.bean.SelectBean;
import com.example.recyclerviewdemo.databinding.ActivityRvDataBindingBinding;
import com.example.recyclerviewdemo.databinding.ActivityRvDynamicallyChangeBinding;

import java.util.List;
import java.util.Locale;

public class RvDynamicallyChangeActivity extends BaseActivity {

    ActivityRvDynamicallyChangeBinding binding;

    private int selectNum = 0;

    private boolean show = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRvDynamicallyChangeBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView() {
        back(binding.materialToolbar);
        List<SelectBean> list = getSelects();

        SelectAdapter selectAdapter = new SelectAdapter(list);
        selectAdapter.setOnItemClickListener((view, position) -> {
            if (!show) {
                boolean select = list.get(position).isSelect();
                //更改数据
                list.get(position).setSelect(!select);
                //刷新
                selectAdapter.notifyItemChanged(position);
                if (!select) {
                    ++selectNum;
                } else {
                    --selectNum;
                }

                binding.tvSelectNum.setText(String.format(Locale.getDefault(), "选中%d个", selectNum));
            }

        });
        binding.rvText.setAdapter(selectAdapter);
        binding.rvText.setLayoutManager(new LinearLayoutManager(this));

        binding.tvEdit.setOnClickListener(v -> {
            show = selectAdapter.isShow();
            selectAdapter.setShow(!show);
            binding.tvSelectNum.setVisibility(show ? View.GONE : View.VISIBLE);
            binding.tvEdit.setText(show ? "编辑" : "取消");

            boolean cancel = !show;
            if (!cancel) {
                for (SelectBean selectBean : list) {
                    selectBean.setSelect(false);
                }
                selectAdapter.notifyDataSetChanged();
                selectNum = 0;
                binding.tvSelectNum.setText(String.format(Locale.getDefault(), "选中%d个", selectNum));
            }
        });
    }
}