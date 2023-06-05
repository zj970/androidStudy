package com.example.goodweather.repository;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.DailyResponse;
import com.example.goodweather.bean.HourlyResponse;
import com.example.goodweather.bean.LifestyleResponse;
import com.example.goodweather.bean.NowResponse;
import com.example.goodweather.service.ApiService;
import com.example.goodweather.util.Constant;
import com.example.goodweather.util.LogUtil;
import com.example.mylibrary.network.ApiType;
import com.example.mylibrary.network.NetworkApi;
import com.example.mylibrary.network.observer.BaseObserver;

/**
 * @auther zj970
 * @create 2023-05-22 下午9:57
 */
@SuppressLint("CheckResult")
public class WeatherRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();


    /**
     * 单例模式
     */
    private static final class WeatherRepositoryHolder {
        private static final WeatherRepository mInstance = new WeatherRepository();
    }

    public static WeatherRepository getInstance() {
        return WeatherRepositoryHolder.mInstance;
    }

    /**
     * 实况天气
     *
     * @param responseLiveData 成功数据
     * @param failed           错误信息
     * @param cityId           城市ID
     */
    public void nowWeather(MutableLiveData<NowResponse> responseLiveData,
                           MutableLiveData<String> failed, String cityId) {
        String type = "实时天气-->";
        NetworkApi.createService(ApiService.class, ApiType.WEATHER).nowWeather(cityId)
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(NowResponse nowResponse) {
                        if (nowResponse == null) {
                            failed.postValue("实况天气数据为null，请检查城市ID是否正确。");
                            return;
                        }
                        //请求接口成功返回数据，失败返回状态码
                        if (Constant.SUCCESS.equals(nowResponse.getCode())) {
                            responseLiveData.postValue(nowResponse);
                        } else {
                            failed.postValue(type + nowResponse.getCode());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.e(TAG, "onFailure: " + e.getMessage());
                        failed.postValue(type + e.getMessage());
                    }
                }));
    }


    /**
     * 天气预报
     * @param responseLiveData
     * @param failed
     * @param cityId
     */
    public void dailyWeather(MutableLiveData<DailyResponse> responseLiveData,
                             MutableLiveData<String> failed, String cityId) {
        String type = "天气预报-->";
        NetworkApi.createService(ApiService.class, ApiType.WEATHER).dailyWeather(cityId)
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(DailyResponse dailyResponse) {
                        if (dailyResponse == null) {
                            failed.postValue("天气预报数据为null，请检查城市ID是否正确。");
                            return;
                        }
                        //请求接口成功返回数据，失败返回状态码
                        if (Constant.SUCCESS.equals(dailyResponse.getCode())) {
                            responseLiveData.postValue(dailyResponse);
                        } else {
                            failed.postValue(type + dailyResponse.getCode());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.e(TAG, "onFailure: " + e.getMessage());
                        failed.postValue(type + e.getMessage());
                    }
                }));
    }


    /**
     * 生活指数
     * @param responseLiveData
     * @param failed
     * @param cityId
     */
    public void lifestyle(MutableLiveData<LifestyleResponse> responseLiveData,
                          MutableLiveData<String> failed, String cityId) {
        String type = "生活指数-->";
        NetworkApi.createService(ApiService.class, ApiType.WEATHER).lifestyle("1,2,3,4,5,6,7,8,9", cityId)
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(LifestyleResponse lifestyleResponse) {
                        if (lifestyleResponse == null) {
                            failed.postValue("生活指数数据为null，请检查城市ID是否正确。");
                            return;
                        }
                        //请求接口成功返回数据，失败返回状态码
                        if (Constant.SUCCESS.equals(lifestyleResponse.getCode())) {
                            responseLiveData.postValue(lifestyleResponse);
                        } else {
                            failed.postValue(type + lifestyleResponse.getCode());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.e(TAG, "onFailure: " + e.getMessage());
                        failed.postValue(type + e.getMessage());
                    }
                }));
    }

    public void hourlyWeather(MutableLiveData<HourlyResponse> responseLiveData,
                              MutableLiveData<String> failed, String cityId) {
        String type = "逐小时天气预报-->";
        NetworkApi.createService(ApiService.class, ApiType.WEATHER).hourlyWeather(cityId)
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(HourlyResponse hourlyResponse) {
                        if (hourlyResponse == null) {
                            failed.postValue("逐小时天气预报数据为null，请检查城市ID是否正确。");
                            return;
                        }
                        //请求接口成功返回数据，失败返回状态码
                        if (Constant.SUCCESS.equals(hourlyResponse.getCode())) {
                            responseLiveData.postValue(hourlyResponse);
                        } else {
                            failed.postValue(type + hourlyResponse.getCode());
                        }
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.e(TAG, "onFailure: " + e.getMessage());
                        failed.postValue(type + e.getMessage());
                    }
                }));
    }


}
