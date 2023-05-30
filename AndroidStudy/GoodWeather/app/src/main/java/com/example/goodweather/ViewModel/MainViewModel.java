package com.example.goodweather.ViewModel;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.NowResponse;
import com.example.goodweather.bean.SearchCityResponse;
import com.example.goodweather.repository.SearchCityRepository;
import com.example.goodweather.repository.WeatherRepository;
import com.example.mylibrary.base.BaseViewModel;

/**
 * 创建ViewModel对应MainActivity主页面ViewModel
 * {@link com.example.goodweather.MainActivity}
 * @auther zj970
 * @create 2023-05-20 下午4:53
 */
public class MainViewModel extends BaseViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    public MutableLiveData<SearchCityResponse> searchCityResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<NowResponse> nowResponseMutableLiveData = new MutableLiveData<>();

    /**
     * 搜索城市
     *
     * @param cityName 城市名称
     */
    public void searchCity(String cityName) {
        new SearchCityRepository().searchCity(searchCityResponseMutableLiveData, failed, cityName);
    }

    /**
     * 实况天气
     *
     * @param cityId 城市ID
     */
    public void nowWeather(String cityId) {
        new WeatherRepository().nowWeather(nowResponseMutableLiveData, failed, cityId);
    }
}
