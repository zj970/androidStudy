package com.example.goodweather.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import com.example.goodweather.ViewModel.ManageCityViewModel;
import com.example.goodweather.bean.MyCity;
import com.example.goodweather.bean.adapter.MyCityAdapter;
import com.example.goodweather.databinding.ActivityManageCityBinding;
import com.example.goodweather.util.AddCityDialog;
import com.example.goodweather.util.Constant;
import com.example.mylibrary.base.NetworkActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ManageCityActivity extends NetworkActivity<ActivityManageCityBinding> {

    private ManageCityViewModel viewModel;
    private final List<MyCity> myCityList = new ArrayList<>();
    private final MyCityAdapter myCityAdapter = new MyCityAdapter(myCityList);

    @Override
    protected void onCreate() {
        initView();
        viewModel = new ViewModelProvider(this).get(ManageCityViewModel.class);
        viewModel.getAllCityData();
    }

    private void initView() {
        backAndFinish(binding.toolbar);
        setStatusBar(true);
        myCityAdapter.setOnClickItemCallback(position -> setPageResult(myCityList.get(position).getCityName()));

        binding.rvCity.setLayoutManager(new LinearLayoutManager(ManageCityActivity.this));
        binding.rvCity.setAdapter(myCityAdapter);
        binding.btnAddCity.setOnClickListener(v ->
                AddCityDialog.show(ManageCityActivity.this, Arrays.asList(Constant.CITY_ARRAY), cityName -> {
                    //保存到数据库中
                    viewModel.addMyCityData(cityName);
                    //设置页面返回数据
                    setPageResult(cityName);
                }));

    }

    @SuppressLint("NotifyDataSetChanged")
    @Override
    protected void onObserveData() {
        viewModel.listMutableLiveData.observe(this, myCities -> {
            if (myCities != null && myCities.size() > 0) {
                myCityList.clear();
                myCityList.addAll(myCities);
                myCityAdapter.notifyDataSetChanged();
            } else {
                showMsg("空空如也");
            }
        });
    }

    /**
     * 设置页面返回数据
     * @param cityName 城市名
     */
    private void setPageResult(String cityName) {
        Intent intent = new Intent();
        intent.putExtra(Constant.CITY_RESULT, cityName);
        setResult(Activity.RESULT_OK, intent);
        finish();
    }

}
