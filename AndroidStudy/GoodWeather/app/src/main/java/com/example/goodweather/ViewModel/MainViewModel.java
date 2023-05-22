package com.example.goodweather.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.NowResponse;
import com.example.goodweather.bean.SearchCityResponse;
import com.example.goodweather.repository.SearchCityRepository;
import com.example.goodweather.repository.WeatherRepository;
import com.example.mylibrary.base.BaseViewModel;

/**
 * 创建ViewModel对应MainActivity
 * @auther zj970
 * @create 2023-05-20 下午4:53
 */
public class MainViewModel extends BaseViewModel {

    public MutableLiveData<SearchCityResponse> searchCityResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<NowResponse> nowResponseMutableLiveData = new MutableLiveData<>();

    public void nowWeather(String cityId) {
        new WeatherRepository().nowWeather(nowResponseMutableLiveData,failed, cityId);
    }


    /**
     * 搜索成功
     * @param cityName 城市名称
     */
    public void searchCity(String cityName) {
        new SearchCityRepository().searchCity(searchCityResponseMutableLiveData, failed, cityName);
    }
}

