package com.example.goodweather.repository;

import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.base.WeatherApp;
import com.example.goodweather.bean.Province;
import io.reactivex.Completable;
import io.reactivex.Flowable;

import java.util.List;

/**
 * @auther zj970
 * @create 2023-06-03 上午12:35
 */
public class CityRepository {

    private static final String TAG = CityRepository.class.getSimpleName();

    private static final class CityRepositoryHolder {
        private static final CityRepository mInstance = new CityRepository();
    }

    public static CityRepository getInstance() {
        return CityRepository.CityRepositoryHolder.mInstance;
    }

    /**
     * 添加城市数据
     */
    public void addCityData(List<Province> cityList) {
        Province[] provinceArray = cityList.toArray(new Province[0]);
        Completable insertAll = WeatherApp.getDb().provinceDao().insertAll(provinceArray);
        CustomDisposable.addDisposable(insertAll, () -> Log.d(TAG, "addCityData: 插入数据成功。"));
    }

    /**
     * 获取城市数据
     */
    public void getCityData(MutableLiveData<List<Province>> listMutableLiveData) {
        Flowable<List<Province>> listFlowable = WeatherApp.getDb().provinceDao().getAll();
        CustomDisposable.addDisposable(listFlowable, listMutableLiveData::postValue);
    }
}
