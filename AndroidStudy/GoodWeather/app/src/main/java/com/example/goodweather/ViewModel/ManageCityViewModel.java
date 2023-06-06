package com.example.goodweather.ViewModel;

import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.MyCity;
import com.example.goodweather.repository.CityRepository;
import com.example.mylibrary.base.BaseViewModel;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-06 上午10:09
 */
public class ManageCityViewModel extends BaseViewModel {

    public MutableLiveData<List<MyCity>> listMutableLiveData = new MutableLiveData<>();

    /**
     * 获取所有城市数据
     */
    public void getAllCityData() {
        CityRepository.getInstance().getMyCityData(listMutableLiveData);
    }

    /**
     * 添加我的城市数据，在定位之后添加数据
     */
    public void addMyCityData(String cityName) {
        CityRepository.getInstance().addMyCityData(new MyCity(cityName));
    }

}
