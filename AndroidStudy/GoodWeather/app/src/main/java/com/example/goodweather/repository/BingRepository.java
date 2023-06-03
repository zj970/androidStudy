package com.example.goodweather.repository;

import android.annotation.SuppressLint;
import androidx.lifecycle.MutableLiveData;
import com.example.goodweather.bean.BingResponse;
import com.example.goodweather.service.ApiService;
import com.example.goodweather.util.LogUtil;
import com.example.mylibrary.network.ApiType;
import com.example.mylibrary.network.NetworkApi;
import com.example.mylibrary.network.observer.BaseObserver;

/**
 * @auther zj970
 * @create 2023-06-03 下午9:04
 */
@SuppressLint("CheckResult")
public class BingRepository {

    private static final String TAG = WeatherRepository.class.getSimpleName();

    private static final class BingRepositoryHolder {
        private static final BingRepository mInstance = new BingRepository();
    }

    public static BingRepository getInstance() {
        return BingRepositoryHolder.mInstance;
    }

    /**
     * 必应壁纸
     *
     * @param responseLiveData 成功数据
     * @param failed           错误信息
     */
    public void bing(MutableLiveData<BingResponse> responseLiveData, MutableLiveData<String> failed) {
        String type = "必应壁纸-->";
        NetworkApi.createService(ApiService.class, ApiType.BING).bing()
                .compose(NetworkApi.applySchedulers(new BaseObserver<>() {
                    @Override
                    public void onSuccess(BingResponse bingResponse) {
                        if (bingResponse == null) {
                            failed.postValue("必应壁纸数据为null。");
                            return;
                        }
                        responseLiveData.postValue(bingResponse);
                    }

                    @Override
                    public void onFailure(Throwable e) {
                        LogUtil.e(TAG, "onFailure: " + e.getMessage());
                        failed.postValue(type + e.getMessage());
                    }
                }));
    }
}
