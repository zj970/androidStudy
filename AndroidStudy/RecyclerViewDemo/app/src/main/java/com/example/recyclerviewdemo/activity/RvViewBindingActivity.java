package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.adapter.StringViewBindingAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.databinding.ActivityRvViewBindingBinding;

import java.util.List;

public class RvViewBindingActivity extends BaseActivity {

    private ActivityRvViewBindingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRvViewBindingBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        initView();
    }


    private void initView() {
        back(binding.materialToolbar);

        List<String> strings = getStrings();
        //获取适配器实例
        StringViewBindingAdapter stringAdapter = new StringViewBindingAdapter(strings);
        //设置适配器Item点击事件
        stringAdapter.setListener((view, position) -> showMsg(strings.get(position)));
        //设置适配器Item子控件点击事件
        stringAdapter.setChildClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.btn_test:
                    showMsg(strings.get(position) + "的按钮 1");
                    break;
                case R.id.btn_test_2:
                    showMsg(strings.get(position) + "的按钮 2");
                    break;
            }
        });
        //设置适配器Item长按事件
        stringAdapter.setLongClickListener((view, position) -> {
            showMsg("长按了");
            return true;
        });
        //设置适配器Item子控件长按事件
        stringAdapter.setChildLongClickListener((view, position) -> {
            showMsg("长按了按钮");
            return true;
        });
        //配置适配器
        binding.rvText.setAdapter(stringAdapter);
        //配置布局管理器
        binding.rvText.setLayoutManager(new LinearLayoutManager(this));
    }

}