package com.example.goodweather.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.*;
import com.example.goodweather.repository.CityRepository;
import com.example.goodweather.repository.SearchCityRepository;
import com.example.goodweather.repository.WeatherRepository;
import com.example.goodweather.ui.MainActivity;
import com.example.mylibrary.base.BaseViewModel;

import java.util.List;

/**
 * 创建ViewModel对应MainActivity主页面ViewModel
 * {@link MainActivity}
 *
 * @auther zj970
 * @create 2023-05-20 下午4:53
 */
public class MainViewModel extends BaseViewModel {
    private static final String TAG = MainViewModel.class.getSimpleName();
    public MutableLiveData<SearchCityResponse> searchCityResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<DailyResponse> dailyResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<NowResponse> nowResponseMutableLiveData = new MutableLiveData<>();
    public MutableLiveData<LifestyleResponse> lifestyleResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<Province>> cityMutableLiveData = new MutableLiveData<>();


    /**
     * 搜索城市
     *
     * @param cityName 城市名称
     */
    public void searchCity(String cityName) {
        SearchCityRepository.getInstance().searchCity(searchCityResponseMutableLiveData, failed, cityName);
    }

    /**
     * 实况天气
     *
     * @param cityId 城市ID
     */
    public void nowWeather(String cityId) {
        WeatherRepository.getInstance().nowWeather(nowResponseMutableLiveData, failed, cityId);
    }


    /**
     * 天气预报(7天)
     * @param cityId
     */
    public void dailyWeather(String cityId) {
        WeatherRepository.getInstance().dailyWeather(dailyResponseMutableLiveData, failed, cityId);
    }

    /**
     * 生活指数
     * @param cityId
     */
    public void lifestyle(String cityId) {
        WeatherRepository.getInstance().lifestyle(lifestyleResponseMutableLiveData, failed, cityId);
    }



    public void getAllCity() {
        CityRepository.getInstance().getCityData(cityMutableLiveData);
    }


}
