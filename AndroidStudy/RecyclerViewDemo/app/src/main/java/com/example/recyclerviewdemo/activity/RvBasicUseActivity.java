package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.R;
import com.example.recyclerviewdemo.adapter.StringAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.databinding.ActivityMainBinding;
import com.google.android.material.appbar.MaterialToolbar;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-05-05 下午4:40
 */
public class RvBasicUseActivity extends BaseActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rv_basic_use);
        initView();
    }

    private void initView(){
        MaterialToolbar toolbar = findViewById(R.id.materialToolbar);
        RecyclerView recyclerView = findViewById(R.id.rv_text);
        back(toolbar);

        List<String> stringList = getStrings();

        //获取适配器实例
        StringAdapter stringAdapter = new StringAdapter(stringList);
        stringAdapter.setListener(((view, position) -> showMsg(stringList.get(position))));
        //为适配器添加Item子控件的点击事件
        stringAdapter.setChildClickListener((view, position) -> {
            switch (view.getId()) {
                case R.id.btn_test:
                    showMsg(stringList.get(position) + "的按钮 1");
                    break;
                case R.id.btn_test_2:
                    showMsg(stringList.get(position) + "的按钮 2");
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
        recyclerView.setAdapter(stringAdapter);
        //配置布局管理器
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

}
