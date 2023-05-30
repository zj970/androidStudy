package com.example.goodweather.repository;

import android.annotation.SuppressLint;
import android.util.Log;
import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.SearchCityResponse;
import com.example.goodweather.service.ApiService;
import com.example.goodweather.util.Constant;
import com.example.goodweather.util.LogUtil;
import com.example.mylibrary.network.ApiType;
import com.example.mylibrary.network.NetworkApi;
import com.example.mylibrary.network.observer.BaseObserver;

/**
 * 在MVVM框架中，是Model + View + ViewModel的模式
 * Model：SearchCityResponse，搜索城市数据实体类
 * View：MainActivity
 * 而ViewModel就是负责连接View，所以在ViewModel中需要获取Model，拿到数据给到View，而如果直接在ViewModel中请求网络又比较臃肿，
 * 因此再拆分一下，在ViewModel使用Repository，作为数据处理的方式。
 * @auther zj970
 * @create 2023-05-20 下午4:48
 */
@SuppressLint("CheckResult")
public class SearchCityRepository {

    private static final String TAG = SearchCityRepository.class.getSimpleName();
    /**
     * 单例模式
     */
    private static final class SearchCityRepositoryHolder {
        private static final SearchCityRepository mInstance = new SearchCityRepository();
    }

    public static SearchCityRepository getInstance() {
        return SearchCityRepository.SearchCityRepositoryHolder.mInstance;
    }

    /**
     * 搜索城市
     *
     * @param responseLiveData 成功数据
     * @param failed           错误信息
     * @param cityName         城市名称
     */
    public void searchCity(MutableLiveData<SearchCityResponse> responseLiveData,
                           MutableLiveData<String> failed, String cityName) {
        String type = "搜索城市-->";
        NetworkApi.createService(ApiService.class, ApiType.SEARCH).searchCity(cityName)
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(SearchCityResponse searchCityResponse) {
                        if (searchCityResponse == null) {
                            failed.postValue("搜索城市数据为null，请检查城市名称是否正确。");
                            return;
                        }
                        //请求接口成功返回数据，失败返回状态码
                        if (Constant.SUCCESS.equals(searchCityResponse.getCode())) {
                            responseLiveData.postValue(searchCityResponse);
                        } else {
                            failed.postValue(type + searchCityResponse.getCode());
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
