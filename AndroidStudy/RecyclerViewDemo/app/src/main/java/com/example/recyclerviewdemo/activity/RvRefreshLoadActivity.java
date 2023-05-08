package com.example.recyclerviewdemo.activity;

import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.recyclerviewdemo.adapter.BasicAdapter;
import com.example.recyclerviewdemo.base.BaseActivity;
import com.example.recyclerviewdemo.databinding.ActivityRvRefreshLoadBinding;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class RvRefreshLoadActivity extends BaseActivity {


    private ActivityRvRefreshLoadBinding binding;
    private final List<String> strings = new ArrayList<>();
    private int lastVisibleItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRvRefreshLoadBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initView();
    }

    private void initView(){
        back(binding.materialToolbar);
        strings.addAll(getStrings());

        //获取适配器
        BasicAdapter adapter = new BasicAdapter(strings);

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        //配置布局管理器
        binding.rvText.setLayoutManager(linearLayoutManager);
        //配置适配器
        binding.rvText.setAdapter(adapter);

        binding.refresh.setOnRefreshListener(()->{
            strings.clear();
            strings.addAll(getStrings());
            new Handler().postDelayed(() -> {
                adapter.notifyDataSetChanged();
                binding.refresh.setRefreshing(false);
            }, 200);
        });

        binding.rvText.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull @NotNull RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == adapter.getItemCount()){
                    if(adapter.getItemCount() > 50){
                        showMsg("已加载全部");
                    }else {
                        showMsg("加载更多");
                        new Handler().postDelayed(()->{
                            strings.addAll(getStrings());
                            adapter.notifyDataSetChanged();
                        }, 1000);
                    }
                }
            }

            @Override
            public void onScrolled(@NonNull @NotNull RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //获取最后一个可见Item的下标
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition();
            }
        });
    }
}