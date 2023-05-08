package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.adapter.StringDataBindingAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.bean.BasicBean;
import com.example.recyclerviewdemo.databinding.ActivityRvDataBindingBinding;

import java.util.List;

public class RvDataBindingActivity extends BaseActivity {

    private ActivityRvDataBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rv_data_binding);
        initView();
    }

    private void initView(){
        back(binding.materialToolbar);

        List<BasicBean> list = getBasicBean();

        //获取适配器实例
        StringDataBindingAdapter adapter = new StringDataBindingAdapter(list);

        adapter.setListener((view, position) -> {
            showMsg("点击了 "+ list.get(position).getTitle());
        });


        adapter.setLongClickListener((view, position) -> {
            showMsg("长按了 "+ list.get(position).getTitle());
            return true;
        });
        //配置适配器
        binding.rvText.setAdapter(adapter);

        //配置布局管理器
        binding.rvText.setLayoutManager(new LinearLayoutManager(this));
    }
}