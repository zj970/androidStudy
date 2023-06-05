package com.example.goodweather.ViewModel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.BingResponse;
import com.example.goodweather.bean.Province;
import com.example.goodweather.repository.BingRepository;
import com.example.goodweather.repository.CityRepository;
import com.example.mylibrary.base.BaseViewModel;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:35
 */
public class SplashViewModel extends BaseViewModel {
    private static final String TAG = "SplashViewModel";
    public MutableLiveData<List<Province>> listMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<BingResponse> bingResponseMutableLiveData = new MutableLiveData<>();

    public void bing() {
        BingRepository.getInstance().bing(bingResponseMutableLiveData, failed);
    }

    /**
     * 添加城市数据
     */
    public void addCityData(List<Province> provinceList) {
        CityRepository.getInstance().addCityData(provinceList);
    }

    /**
     * 获取所有城市数据
     */
    public void getAllCityData() {
        CityRepository.getInstance().getCityData(listMutableLiveData);
    }
}
