package com.example.goodweather.service;

import com.example.goodweather.bean.NowResponse;
import com.example.goodweather.bean.SearchCityResponse;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

import static com.example.goodweather.util.Constant.API_KEY;

/**
 * @auther zj970
 * @create 2023-05-20 下午4:46
 */
public interface ApiService {

    /**
     * 搜索城市  模糊搜索，国内范围 返回10条数据
     *
     * @param location 城市名
     * @return NewSearchCityResponse 搜索城市数据返回
     */
    @GET("/v2/city/lookup?key=" + API_KEY + "&range=cn")
    Observable<SearchCityResponse> searchCity(@Query("location") String location);

    /**
     * 实况天气
     *
     * @param location 城市ID
     * @return 返回实况天气数据 NowResponse
     */
    @GET("/v7/weather/now?key=" + API_KEY)
    Observable<NowResponse> nowWeather(@Query("location") String location);


}
